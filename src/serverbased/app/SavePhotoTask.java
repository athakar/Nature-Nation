package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

class SavePhotoTask extends AsyncTask<Void, Void, Void> {
	
	private int attachmentCount;
	
	public SavePhotoTask(int attachmentCount){
		this.attachmentCount = attachmentCount;
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
			File file = new File(Environment.getExternalStorageDirectory() + "/ServerApp/test" + attachmentCount + ".jpg");
			q = new FileInputStream(file);
			f.storeFile("test"+ attachmentCount + ".jpg", q);
			f.logout();
		} 
		catch(Exception e) 
		{
			Log.e("Error","error : " + e.getMessage() );
			e.printStackTrace();
		}

		
		return null;
	}
	}