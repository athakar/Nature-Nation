package serverbased.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ServerAppActivity extends Activity
{
    /** Called when the activity is first created. */
   
   
    private ImageButton photo, findings, map, settings;
	private int CAMERA_PIC_REQUEST = 1001;
	private String PREFS_NAME = "My Prefs Folder";
	private int attachmentCount =0;
	ArrayList<Entry> radiusData = new ArrayList<Entry>();
	ArrayList<Navigation> naviData = new ArrayList<Navigation>();
	LoadGeoDataTask l;
	LoadNaviDataTask n;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSavedData();
        launchGeoTasks();

        setWidgets();           
        
    }
  
    
    public void setWidgets()
    {
    	
    	photo = (ImageButton) findViewById(R.id.camera_button);
    	photo.setOnClickListener(new OnClickListener()
    	{

			public void onClick(View arg0) 
			{
				setCamera();
			}
    		
    	});
    	   	
    	photo.setOnTouchListener(new OnTouchListener() {


			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
	   			if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				photo.setColorFilter(Color.argb(150, 155, 155, 155));	
    			} else if (me.getAction() == MotionEvent.ACTION_UP) {
    				photo.setColorFilter(Color.argb(0, 155, 155, 155)); // or null
    			}	
				return false;
			}
    	});
    	
    	
    	
    	
    	map = (ImageButton) findViewById(R.id.map_button);
    	map.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				radiusData = l.result;

			}
    		
    	});
    	map.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
	   			if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				map.setColorFilter(Color.argb(150, 155, 155, 155));
    			} else if (me.getAction() == MotionEvent.ACTION_UP) {
    				map.setColorFilter(Color.argb(0, 155, 155, 155)); // or null	
    			}	
				return false;
			}
    	});
    	
    	
    	
    	settings = (ImageButton) findViewById(R.id.navigate_button);
    	settings.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				naviData = n.nav;
				Intent i = new Intent(ServerAppActivity.this,NavigationActivity.class);
				i.putExtra("radiusData", naviData);
				startActivity(i);
/*	
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + loc)); 
				startActivity(i);*/
				}
			
    		
    	});
    	settings.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
	   			if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				settings.setColorFilter(Color.argb(150, 155, 155, 155));
    			} else if (me.getAction() == MotionEvent.ACTION_UP) {
    				settings.setColorFilter(Color.argb(0, 155, 155, 155)); // or null	
    			}	
				return false;
			}
    	});
    	
    	findings = (ImageButton) findViewById(R.id.findings_button);
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
    	findings.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
	   			if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				findings.setColorFilter(Color.argb(150, 155, 155, 155));
    			} else if (me.getAction() == MotionEvent.ACTION_UP) {
    				findings.setColorFilter(Color.argb(0, 155, 155, 155)); // or null	
    			}	
				return false;
			}
    	});
    }
       
    private void launchGeoTasks(){
    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 

		Location location = (Location) lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location == null)
		 location = (Location) lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
    		
    	l = new LoadGeoDataTask(latitude,longitude,this);
    	l.execute();
		n = new LoadNaviDataTask(latitude,longitude,this);
		n.execute();
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
		
      	File folder = new File(Environment.getExternalStorageDirectory() + "/ServerApp");
    	if(folder.exists() == false){
    		attachmentCount =0;
    		folder.mkdirs();
    	}
		
    }
    
    private void setCamera(){
    	Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"/ServerApp/Attachment " + attachmentCount + ".jpg")));
		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
 
    	if(resultCode == RESULT_OK){

    		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 

    		Location location = (Location) lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		if(location == null)
    		 location = (Location) lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

    		double longitude = location.getLongitude();
    		double latitude = location.getLatitude();
    		
    		SavePhotoTask s = new SavePhotoTask(attachmentCount++, ServerAppActivity.this,latitude,longitude);
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