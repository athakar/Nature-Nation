package serverbased.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ServerAppActivity extends Activity
{
    /** Called when the activity is first created. */
   
    
    private Button photo, findings, map, settings;
	private int CAMERA_PIC_REQUEST = 1001;
	private String PREFS_NAME = "My Prefs Folder";
	private int attachmentCount =0;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        getSavedData();
        
      	File folder = new File(Environment.getExternalStorageDirectory() + "/ServerApp");
    	if(folder.exists() == false){
    		attachmentCount =0;
    		folder.mkdirs();
    	}
        setWidgets();           
        
    }
  
    
    public void setWidgets()
    {
    	
    	photo = (Button) findViewById(R.id.photobutton);
    	photo.setOnClickListener(new OnClickListener()
    	{

			public void onClick(View arg0) 
			{
				setCamera();
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
				
				if(attachmentCount <= 10){
					Intent i = new Intent(ServerAppActivity.this, MyFindingActivityAlt.class);
					i.putExtra("count", attachmentCount );
					startActivity(i);
				}
				else{
				Intent i = new Intent(ServerAppActivity.this, MyFindingsActivity.class);
				i.putExtra("count", attachmentCount );
				startActivity(i);
				}
			}
    		
    	});
    }
    
    private void saveData(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("attachmentCount", (float)attachmentCount);
        editor.commit();
    }
    
    private void getSavedData(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		try{
	       attachmentCount  =(int) settings.getFloat("attachmentCount", 0);
		}catch(Exception e){
			Log.e("Preference", "error: " + e.getMessage());
		}
    }
    
    private void setCamera(){
    	Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"/ServerApp/Attachment " + attachmentCount + ".jpg")));
		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
 
    	if(resultCode == RESULT_OK){

    		SavePhotoTask s = new SavePhotoTask(attachmentCount++, ServerAppActivity.this);
			s.execute();
			Toast.makeText(this, "Photo will be Uploaded to Server", Toast.LENGTH_SHORT).show();
    	}
    }

    
	protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	saveData();
    }
    
}