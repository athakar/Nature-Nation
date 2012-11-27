package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
	public SavePhotoTask(int attachmentCount, Context c){
		this.attachmentCount = attachmentCount;
		this.c = c;
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
			ExifInterface exif = new ExifInterface(file.getAbsolutePath());     //Since API Level 5
			String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
			Log.d("Orientation", exifOrientation);
			f.logout();
			success = true;
		} 
		catch(Exception e) 
		{
			Log.e("Error","error : " + e.getMessage() );
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(success)
			Toast.makeText(c, "Photo Successfully Uploaded", Toast.LENGTH_LONG).show();
		
	}
	
	
	}