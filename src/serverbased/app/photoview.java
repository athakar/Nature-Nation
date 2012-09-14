package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.content.Intent;
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
	/**
	 * @param args
	 */
	public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.photoviewer);
      //  attachmentCount = savedInstanceState.getInt("count",0);
      
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),"/ServerApp/test"+ attachmentCount +".jpg");
        if(photo.exists())
        	photo.delete();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

		yes = (Button) findViewById(R.id.identify);
		no = (Button) findViewById(R.id.No);

		yes.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
			attachmentCount ++;
			SavePhotoTask s = new SavePhotoTask(attachmentCount-1);
			s.execute();
			finish();
			}
    	});

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
        	String pathName = new File(Environment.getExternalStorageDirectory(),"/ServerApp/test" + attachmentCount + ".jpg").toString();
        	Bitmap photo = BitmapFactory.decodeFile(pathName);;
        	ImageView image = (ImageView) findViewById(R.id.imageView1);
        	image.setImageBitmap(photo);
        	
        	
        }  
    }  
}