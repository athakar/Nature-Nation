package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.content.Context;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

class SavePhotoTask extends AsyncTask<Void, Void, Void> {
	
	private int attachmentCount;
	boolean success = false;
	private Context c;
	private double latitude,longitude;
	private String l1,l2;
	
	public SavePhotoTask(int attachmentCount, Context c, double latitude, double longitude){
		this.attachmentCount = attachmentCount;
		this.c = c;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		FTPClient f = new FTPClient();
		FileInputStream q = null;
		try 
		{
			f.connect("cheese.asuscomm.com");       //works when on an actual phone. not the emulator
			f.login("anonymous", "");
			f.setFileType(FTP.BINARY_FILE_TYPE);
			f.changeWorkingDirectory("/AiDisk_a1/share/server/");
			File file = new File(Environment.getExternalStorageDirectory() + "/ServerApp/Attachment " + attachmentCount + ".jpg");
			
			q = new FileInputStream(file);
			f.storeFile("Attachment "+ attachmentCount + ".jpg", q);
			f.logout();
			success = true;
			geoTag(file.getAbsolutePath());
		} 
		catch(Exception e) 
		{
			Log.e("Error","error : " + e.getMessage() );
			e.printStackTrace();
		}		
		return null;
	}

	public void geoTag(String filename){
	ExifInterface exif;

	try {
	    exif = new ExifInterface(filename);
	    int num1Lat = (int)Math.floor(latitude);
	    int num2Lat = (int)Math.floor((latitude - num1Lat) * 60);
	    double num3Lat = (latitude - ((double)num1Lat+((double)num2Lat/60))) * 3600000;

	    int num1Lon = (int)Math.floor(longitude);
	    int num2Lon = (int)Math.floor((longitude - num1Lon) * 60);
	    double num3Lon = (longitude - ((double)num1Lon+((double)num2Lon/60))) * 3600000;

	    
	    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat+"/1,"+num2Lat+"/1,"+num3Lat+"/1000");
	    l1 = num1Lat+"/1,"+num2Lat+"/1,"+num3Lat+"/1000";
	    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon+"/1,"+num2Lon+"/1,"+num3Lon+"/1000");
	    l2 = num1Lon+"/1,"+num2Lon+"/1,"+num3Lon+"/1000";

	    if (latitude > 0) {
	        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N"); 
	    } else {
	        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
	    }

	    if (longitude > 0) {
	        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");    
	    } else {
	    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
	    }

	    exif.saveAttributes();
	} catch (IOException e) {
	    Log.e("PictureActivity", e.getLocalizedMessage());
	} 
	
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try{
		DatabaseClass entry = new DatabaseClass(c);
		entry.open();
		entry.createEntry("Attachment " + attachmentCount,l1,l2);
		entry.close();
		}catch(Exception e){
			Log.d("Database Tag", e.getMessage());
			l1 = e.getMessage();
		}
		Toast.makeText(c, "Photo Successfully uploaded", Toast.LENGTH_LONG).show();
	}
	
	
	}