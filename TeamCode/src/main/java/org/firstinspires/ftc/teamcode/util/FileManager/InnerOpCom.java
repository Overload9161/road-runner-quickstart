package org.firstinspires.ftc.teamcode.util.FileManager;//package org.firstinspires.ftc.teamcode.util.Threads.File;
//
//import android.app.Application;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * This is meant to write and read information between OpModes <br>
// * DO NOT USE YET!!!
// */
//public class InnerOpCom extends Application {
//
//	OpMode opMode;
//
//	JSONObject jsonObject, x, y, rot;
//
//	List<String> buffer = new ArrayList<String>();
//
//	public InnerOpCom(OpMode opMode){
//		this.opMode = opMode;
//		try{
//			jsonObject = new JSONObject(Objects.requireNonNull(loadJSONFromAsset("InnerCom.json")));
//			x   = jsonObject.getJSONObject("Com").getJSONObject("x");
//			y   = jsonObject.getJSONObject("Com").getJSONObject("y");
//			rot = jsonObject.getJSONObject("Com").getJSONObject("rot");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void bufferInfo(Object dta){
//		buffer.add(String.valueOf(dta));
//	}
//
//	public void saveInfo(int[] xyRot){
//		x.
//
//	}
//
//	public String getInfo(){
//		try {
//			return String.valueOf(fileInputStream.read());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public interface continuous {
//		void run();
//	}
//
//	public void runContinuous(continuous dta){
//		Timer t = new Timer();
//		t.schedule(loop(dta), 0, 50);
//	}
//
//	private TimerTask loop(continuous dta){
//		dta.run();
//		return null;
//	}
//
//	private String loadJSONFromAsset(String fileName) {
//		String json = null;
//		try {
////            InputStream is = getApplicationContext().getAssets().open("Field.json");
//
//			InputStream is = opMode.hardwareMap.appContext.getAssets().open(fileName);
//
//			hwMap.appContext.getAssets().
//
//			int size = is.available();
//
//			byte[] buffer = new byte[size];
//
//			is.read(buffer);
//
//			is.close();
//
//			json = new String(buffer, "UTF-8");
//
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			return null;
//		}
//		return json;
////		Log.d("Test", json + "\n Test");
////		Log.d("Test", "Running");
//	}
//
//}