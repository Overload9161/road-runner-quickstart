package org.firstinspires.ftc.teamcode.util.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import androidx.annotation.RequiresApi;

@SuppressWarnings(value = "unused")
public class ReadImage extends Thread{
	
	OpMode opMode;
	
	File file;
	
	int which = -1;
	
//	Intent cropIntent = new Intent("com.android.camera.action.CROP");
	
	public ReadImage(OpMode opMode){
		this.opMode = opMode;
	}
	
	/**
	 * Give this class the location of the file
	 * @param path Where the image is
	 */
	public void setImage(File path){
		file = path;
		this.start();
	}
	
	@Override
	public synchronized void start() {
		super.start();
		// If we have anything we want to happen when we start this thread
	}
	
//	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void run() {
		super.run();
		// When we want to run this thread
		// TODO: This will crop then
		
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		
		Bitmap left = Bitmap.createBitmap(bitmap, 0, 100, 100, bitmap.getHeight()/2);
		Bitmap center = Bitmap.createBitmap(bitmap, 225, 150, 100, bitmap.getHeight()/2);
		Bitmap right = Bitmap.createBitmap(bitmap, 500,150,100,bitmap.getHeight()/2);
		Bitmap[] bitmaps = new Bitmap[]{left, center, right};
		
		int[] averages = new int[3];
		
		for(int a = 0; a<3; a++){
			int redBucket = 0, greenBucket = 0, blueBucket = 0, pixelCount = 0;
			
			for(int y = 0; y<bitmaps[a].getHeight(); y++){
				for(int x = 0; x<bitmaps[a].getWidth(); x++){
					int c = bitmaps[a].getPixel(x, y);
					
					pixelCount++;
					redBucket += Color.red(c);
					greenBucket += Color.green(c);
					blueBucket += Color.blue(c);
				}
			}
			
			int averageColor = Color.rgb(redBucket / pixelCount, greenBucket / pixelCount, blueBucket / pixelCount);
			
			averages[a] = averageColor;
			
		}

		int smallest = 0;
		which = 0;
		for(int i = 0; i< averages.length; i++){
			if(smallest<averages[i] || smallest == 0) {
				smallest = averages[i];
				which=i;
			}
			
		}
		
		saveCrops(left, "Left");
		saveCrops(center, "Center");
		saveCrops(right, "Right");
		
		opMode.telemetry.addData("Location-ReadImage", which);
		opMode.telemetry.update();
		
		Log.d("Location", String.valueOf(which));
		
		Bitmap bmp = bitmap.copy(Bitmap.Config.RGB_565, true);
		
		float stroke_width = 10.0f;
		
		Canvas cnva = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(stroke_width);
		
		switch (which){
			case 0:
				cnva.drawLine(0.0f, 100.0f, bmp.getWidth()/4.0f, 100.0f, paint);
				cnva.drawLine(bmp.getWidth()/4.0f, 100.0f-(stroke_width/2.0f), bmp.getWidth()/4.0f, 400.0f, paint);
				cnva.drawLine(bmp.getWidth()/4.0f+(stroke_width/2.0f), 400.0f, 0, 400.0f, paint);
				break;
			case 1:
				cnva.drawLine(bmp.getWidth()/4.0f, 100.0f, bmp.getWidth()/1.75f, 100.0f, paint);
				cnva.drawLine(bmp.getWidth()/1.75f, 100.0f-(stroke_width/2.0f), bmp.getWidth()/1.75f, 400.0f, paint);
				cnva.drawLine(bmp.getWidth()/1.75f+(stroke_width/2.0f), 400.0f, bmp.getWidth()/4.0f, 400.0f, paint);
				cnva.drawLine(bmp.getWidth()/4.0f-(stroke_width/2.0f), 400.0f+(stroke_width/2.0f), bmp.getWidth()/4.0f, 100.0f-(stroke_width/2.0f), paint);
				break;
			case 2:
				cnva.drawLine(bmp.getWidth()/1.5f, 100.0f, bmp.getWidth(), 100.0f, paint);
				cnva.drawLine(bmp.getWidth()/1.5f, 100.0f-(stroke_width/2.0f), bmp.getWidth()/1.5f, 400.0f, paint);
				cnva.drawLine(bmp.getWidth()/1.5f-(stroke_width/2.0f), 400.0f, bmp.getWidth(), 400.0f, paint);
				break;
		}
		
		saveCrops(bmp, "SelectedImage");
		
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		// When we want to interrupt this thread
	}
	
	public int getLocation(){
		return which;
	}
	
	private void saveCrops(Bitmap bitmap, String where){
		File file = new File(this.file.getParent(), "Thing-"+this.file.getName()+"-"+where+".jpg");
		try {
			try (FileOutputStream outputStream = new FileOutputStream(file)) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//				opMode.telemetry.log().add("Saved %s", file.getName());
				Log.d("Location", "====================================================="+file.getAbsolutePath());
			}
		} catch (IOException e) {
			RobotLog.ee("Read Image", e, "exception in saveBitmap()");
//			error("exception saving %s", file.getName());
		}
	}
	
}