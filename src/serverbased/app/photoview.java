package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class photoview extends Activity
{

    private static final int CAMERA_PIC_REQUEST = 1337;
    private Button yes, no;
	/**
	 * @param args
	 */
	public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.photoviewer);
        
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		
		yes = (Button) findViewById(R.id.identify);
		no = (Button) findViewById(R.id.No);
		
		yes.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				
				FTPClient f = new FTPClient();
				FileInputStream q = null;
				try 
				{
					//f.connect("192.168.1.1");          
					f.connect("cheese.asuscomm.com");       //works when on an actual phone. not the emulator
					f.login("anonymous", "");
					f.setFileType(FTP.BINARY_FILE_TYPE);
					f.changeWorkingDirectory("/AiDisk_a1/share/server/");
					File file = new File(Environment.getExternalStorageDirectory() + "/ServerApp/test.jpg");
					q = new FileInputStream(file);
					f.storeFile("test.jpg", q);
					f.logout();
				} 
				catch(Exception e) 
				{
					Log.e("Error","error : " + e.getMessage() );
					e.printStackTrace();
				}
				
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
        	
        	Bitmap photo = (Bitmap) data.getExtras().get("data");
        	ImageView image = (ImageView) findViewById(R.id.imageView1);
        	image.setImageBitmap(photo); 
        	try 
        	{
                FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/ServerApp/test.jpg");
                photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } 
        	catch (Exception e) 
            {
                e.printStackTrace();
            }
        	
        	
        }  
    }  
}
