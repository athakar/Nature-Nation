package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class photoview extends Activity
{

    private static final int CAMERA_PIC_REQUEST = 1337;
    private Button yes, no;
    private static int attachmentCount = 0;
    private ImageView image ;
	private Bitmap photo;
	/**
	 * @param args
	 */
    
    
    
	public void onCreate(Bundle savedInstanceState) 
    {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoviewer);
		Intent i = new Intent();
		attachmentCount = getIntent().getIntExtra("count", 0);
		String pathName = new File(Environment.getExternalStorageDirectory(),"/ServerApp/test"+ attachmentCount + ".jpg").toString();
	    photo = BitmapFactory.decodeFile(pathName);
		setWidgets();
     
    }
	

	private void setWidgets(){
		
		image = (ImageView) findViewById(R.id.imageView1);
		image.setImageBitmap(photo);
			
		yes = (Button) findViewById(R.id.identify);
		yes.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				SavePhotoTask s = new SavePhotoTask(attachmentCount);
				s.execute();
			    finish();
			}
    	});

		no = (Button) findViewById(R.id.No);
		no.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				finish();
			}
    	});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {  
        if (requestCode == CAMERA_PIC_REQUEST)
        {  

        	
        }  
    }  
}