package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServerAppActivity extends Activity
{
    /** Called when the activity is first created. */
   
    
    private Button photo, findings, map, settings;
   
    private EditText username,pwd,confirm_pwd;
    private String password,uname;

	private int CAMERA_PIC_REQUEST = 1001;

	private String PREFS_NAME = "My Prefs Folder";

	private int attachmentCount =0;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		try{
	       attachmentCount  =(int) settings.getFloat("silentMode", 0);
		}catch(Exception e){
			Log.e("Preference", "error: " + e.getMessage());
		}
		
        
        setWidgets();
        createDataFolder();             //creates a folder in internal memory called "ServerApp"
       // createJoinDialog(); - Method Implementation not complete
        
    }
    
    public void setWidgets()
    {
    	
    	photo = (Button) findViewById(R.id.photobutton);
    	photo.setOnClickListener(new OnClickListener()
    	{

			public void onClick(View arg0) 
			{
				
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"/ServerApp/test" + attachmentCount + ".jpg")));
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
				//moved all ftp stuff to the photoview.java file
				
			}
    		
    	});
    	
    	map = (Button) findViewById(R.id.mapbutton);
    	map.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    	settings = (Button) findViewById(R.id.settingsbutton);
    	settings.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    	findings = (Button) findViewById(R.id.findingsbutton);
    	findings.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int in =1;
				Intent i = new Intent(ServerAppActivity.this, MyFindingsActivity.class);
				startActivity(i);
	        
			}
    		
    	});
    	
    	username = (EditText)findViewById(R.id.username);
    	pwd = (EditText) findViewById(R.id.password);
    	confirm_pwd= (EditText) findViewById(R.id.confirm_password);

    }
    
    public void createJoinDialog() 
    {// Unable to retreive Strings from EditText
		final AlertDialog.Builder newLogin = new AlertDialog.Builder(this);
		newLogin.setMessage(" Create New User Login ");
		newLogin.setCancelable(false);
		
		LayoutInflater inflater=LayoutInflater.from(this);
	    View addView=inflater.inflate(R.layout.dialog, null);

		LinearLayout dialog = (LinearLayout) findViewById(R.layout.dialog);
		newLogin.setView(addView);
		newLogin.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try{
						String t1 = pwd.getText().toString();
						String t2= confirm_pwd.getText().toString();
						
						if(t1.equals(t2)){
							password = "Weeeeeee";
							uname = username.getText().toString();
							photo.setText(password);
						}
						}catch(Exception e){
							Log.e("error", "error: " + e.getMessage());
						}
					}
				});

		AlertDialog joinGameDialog = newLogin.create();

		joinGameDialog.show();
	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {  
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK){
        	
        	int rotate = 0;
        	String imagePath = "";
        	File f = null;
            try {
            	 f = new File(Environment.getExternalStorageDirectory(),"/ServerApp/test" + attachmentCount + ".jpg");
            	 imagePath = f.toString();
                File imageFile = new File(imagePath);
                ExifInterface exif = new ExifInterface(
                        imageFile.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        	
            Bitmap p = BitmapFactory.decodeFile(imagePath);
            Matrix m = new Matrix();
            m.setRotate(rotate);
            Bitmap p1 = Bitmap.createBitmap(p, 0, 0, p.getWidth(),p.getHeight(), m, false);
            f.delete();
            FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            p1.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        	//Intent i = new Intent(ServerAppActivity.this, photoview.class);
        	//i.putExtra("count", attachmentCount++);
		//	startActivity(i);
        	
        }  
    } 
    
    public void createDataFolder()
    {
    	File folder = new File(Environment.getExternalStorageDirectory() + "/ServerApp/");
    	if(folder.exists() == false){
    		attachmentCount =0;
    		folder.mkdirs();
    	}
    }
    
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("silentMode", (float)attachmentCount);
        editor.commit();
    }
    
}