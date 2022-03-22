package org.firstinspires.ftc.teamcode.util.images;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCharacteristics;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.robotcore.internal.network.CallbackLooper;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.ContinuationSynchronizer;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"DanglingJavadoc", "unused", "SameParameterValue", "FieldMayBeFinal", "ResultOfMethodCallIgnored"})
public class Camera {
	
	OpMode opMode;
	Telemetry telemetry;
	
	private static final String TAG = "Webcam Sample";
	
	/** How long we are to wait to be granted permission to use the camera before giving up. Here,
	 * we wait indefinitely */
	private static final int secondsPermissionTimeout = Integer.MAX_VALUE;
	
	/** State regarding our interaction with the camera */
	private CameraManager cameraManager;
	private WebcamName cameraName;
	private org.firstinspires.ftc.robotcore.external.hardware.camera.Camera camera;
	private CameraCaptureSession cameraCaptureSession;
	
	/** The queue into which all frames from the camera are placed as they become available.
	 * Frames which are not processed by the OpMode are automatically discarded. */
	private EvictingBlockingQueue<Bitmap> frameQueue;
	
	/** State regarding where and how to save frames when the 'A' button is pressed. */
	private int captureCounter = 0;
	private File captureDirectory;
	private File file = null;
	
	/** A utility object that indicates where the asynchronous callbacks from the camera
	 * infrastructure are to run. In this OpMode, that's all hidden from you (but see {@link #startCamera}
	 * if you're curious): no knowledge of multi-threading is needed here. */
	private Handler callbackHandler;
	
	private String name;
	
	public Camera(OpMode opMode){
		this.opMode = opMode;
		this.telemetry = opMode.telemetry;
		
		captureDirectory = new File(opMode.hardwareMap.appContext.getCacheDir() +"/Logs/Camera");
		
		callbackHandler = CallbackLooper.getDefault().getHandler();
		
		cameraManager = ClassFactory.getInstance().getCameraManager();
		cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
		
		initializeFrameQueue(2);
		AppUtil.getInstance().ensureDirectoryExists(captureDirectory);
	}
	
	public void takePhoto(String name){
		this.name = name;
		Bitmap bmp = frameQueue.poll();
		if (bmp != null) {
			onNewFrame(bmp);
		}
	}
	
	public ReadImage analyzePhoto(){
		if(file != null){
			ReadImage rm = new ReadImage(this.opMode);
			rm.setImage(file);
			return rm;
			
//			rm.interrupt();
		}
		return null;
	}
	
	/** Do something with the frame */
	private void onNewFrame(Bitmap frame) {
		saveBitmap(frame);
		frame.recycle(); // not strictly necessary, but helpful
	}
	
	//===========================================================================
	
	private void initializeFrameQueue(int capacity) {
		/** The frame queue will automatically throw away bitmap frames if they are not processed
		 * quickly by the OpMode. This avoids a buildup of frames in memory */
		frameQueue = new EvictingBlockingQueue<>(new ArrayBlockingQueue<>(capacity));
		// RobotLog.ii(TAG, "frame recycled w/o processing");
// not strictly necessary, but helpful
		frameQueue.setEvictAction(Bitmap::recycle);
	}
	
	public void openCamera() {
		if (camera != null) return; // be idempotent
		
		Deadline deadline = new Deadline(secondsPermissionTimeout, TimeUnit.SECONDS);
		camera = cameraManager.requestPermissionAndOpenCamera(deadline, cameraName, null);
		if (camera == null) {
			error("camera not found or permission to use not granted: %s", cameraName);
		}
	}
	
	public void startCamera() {
		if (cameraCaptureSession != null) return; // be idempotent
		
		/** YUY2 is supported by all Webcams, per the USB Webcam standard: See "USB Device Class Definition
		 * for Video Devices: Uncompressed Payload, Table 2-1". Further, often this is the *only*
		 * image format supported by a camera */
		final int imageFormat = ImageFormat.YUY2;
		
		/** Verify that the image is supported, and fetch size and desired frame rate if so */
		CameraCharacteristics cameraCharacteristics = cameraName.getCameraCharacteristics();
		if (!contains(cameraCharacteristics.getAndroidFormats(), imageFormat)) {
			error("image format not supported");
			return;
		}
		final Size size = cameraCharacteristics.getDefaultSize(imageFormat);
		final int fps = cameraCharacteristics.getMaxFramesPerSecond(imageFormat, size);
		
		/** Some of the logic below runs asynchronously on other threads. Use of the synchronizer
		 * here allows us to wait in this method until all that asynchrony completes before returning. */
		final ContinuationSynchronizer<CameraCaptureSession> synchronizer = new ContinuationSynchronizer<>();
		try {
			/** Create a session in which requests to capture frames can be made */
			camera.createCaptureSession(Continuation.create(callbackHandler, new CameraCaptureSession.StateCallbackDefault() {
				@Override public void onConfigured(@NonNull CameraCaptureSession session) {
					try {
						/** The session is ready to go. Start requesting frames */
						final CameraCaptureRequest captureRequest = camera.createCaptureRequest(imageFormat, size, fps);
						session.startCapture(captureRequest,
								(session1, request, cameraFrame) -> {
									/** A new frame is available. The frame data has <em>not</em> been copied for us, and we can only access it
									 * for the duration of the callback. So we copy here manually. */
									Bitmap bmp = captureRequest.createEmptyBitmap();
									cameraFrame.copyToBitmap(bmp);
									frameQueue.offer(bmp);
								},
								Continuation.create(callbackHandler, (session12, cameraCaptureSequenceId, lastFrameNumber) -> RobotLog.ii(TAG, "capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber))
						);
						synchronizer.finish(session);
					} catch (CameraException |RuntimeException e) {
						RobotLog.ee(TAG, e, "exception starting capture");
						error("exception starting capture");
						session.close();
						synchronizer.finish(null);
					}
				}
			}));
		} catch (CameraException|RuntimeException e) {
			RobotLog.ee(TAG, e, "exception starting camera");
			error("exception starting camera");
			synchronizer.finish(null);
		}
		
		/** Wait for all the asynchrony to complete */
		try {
			synchronizer.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		/** Retrieve the created session. This will be null on error. */
		cameraCaptureSession = synchronizer.getValue();
	}
	
	public void stopCamera() {
		if (cameraCaptureSession != null) {
			cameraCaptureSession.stopCapture();
			cameraCaptureSession.close();
			cameraCaptureSession = null;
		}
	}
	
	public void closeCamera() {
		stopCamera();
		if (camera != null) {
			camera.close();
			camera = null;
		}
	}
	
	public File getFile(){
		return this.file;
	}
	
	//----------------------------------------------------------------------------------------------
	// Utilities
	//----------------------------------------------------------------------------------------------
	
	private void error(String msg) {
		telemetry.log().add(msg);
		telemetry.update();
	}
	private void error(String format, Object...args) {
		telemetry.log().add(format, args);
		telemetry.update();
	}
	
	private boolean contains(int[] array, int value) {
		for (int i : array) {
			if (i == value) return true;
		}
		return false;
	}
	
	private void saveBitmap(Bitmap bitmap) {
		if(!captureDirectory.exists())
			captureDirectory.mkdirs();
		file = new File(captureDirectory, "Camera-"+name+".png");
		try {
			try (FileOutputStream outputStream = new FileOutputStream(file)) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
				telemetry.log().add("captured %s", file.getName());
				Log.d("Location", "===================================================== "+file.getAbsolutePath()+" =============================================");
			}
		} catch (IOException e) {
			RobotLog.ee(TAG, e, "exception in saveBitmap()");
			error("exception saving %s", file.getName());
		}
	}
	
}