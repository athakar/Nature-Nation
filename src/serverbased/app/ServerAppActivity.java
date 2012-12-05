package serverbased.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ServerAppActivity extends Activity
{
    /** Called when the activity is first created. */
   

	
   private boolean newUser = true;
   private AlertDialog joinGameDialog;
   private String userName ="";
   
    private ImageButton photo, findings, map, navigate;
	private int CAMERA_PIC_REQUEST = 1001;
	private String PREFS_NAME = "Nature Nation Prefs Folder";
	

	private int attachmentCount =0;
	
	ArrayList<Entry> radiusData = new ArrayList<Entry>();
	ArrayList<Navigation> naviData = new ArrayList<Navigation>();
	LoadGeoDataTask l;
	LoadNaviDataTask n;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DatabaseClass c = new DatabaseClass(this);
        c.open();
        c.delete();
        
        
        getSavedData();
        
        if(newUser){
        	createJoinDialog();
        }
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
    	
    	
    	
    	navigate = (ImageButton) findViewById(R.id.navigate_button);
    	navigate.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				naviData = n.nav;
				Intent i = new Intent(ServerAppActivity.this,NavigationActivity.class);
				i.putExtra("radiusData", naviData);
				startActivity(i);

				}
			
    		
    	});
    	navigate.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
	   			if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				navigate.setColorFilter(Color.argb(150, 155, 155, 155));
    			} else if (me.getAction() == MotionEvent.ACTION_UP) {
    				navigate.setColorFilter(Color.argb(0, 155, 155, 155)); // or null	
    			}	
				return false;
			}
    	});
    	
    	findings = (ImageButton) findViewById(R.id.findings_button);
    	findings.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				Intent i = new Intent(ServerAppActivity.this, MyFindingsActivity.class);
				i.putExtra("count", attachmentCount );
				startActivity(i);
				
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
       
    
    
    public void createJoinDialog() {
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("New User");
		alertDialogBuilder.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.join_game_dialog, (ViewGroup) findViewById(R.id.dialog_root));
		alertDialogBuilder.setView(layout);
		final EditText nameBox = (EditText) layout.findViewById(R.id.unameInput);
		final EditText codeBox = (EditText) layout.findViewById(R.id.codeInput);
		
		alertDialogBuilder.setPositiveButton("Create Account", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						 userName = nameBox.getText().toString();
						String password = codeBox.getText().toString();
						DBConnector b = new DBConnector("128.211.216.171", "root", "developer");
						boolean authenticate = b.addUser(userName, password);
						if(authenticate){
							Toast.makeText(ServerAppActivity.this, "User Added", Toast.LENGTH_LONG).show();
							newUser = false;
							saveData();
					        
						}
						else
							Toast.makeText(ServerAppActivity.this, "Please Try another username", Toast.LENGTH_LONG).show();

						
					}
				});

		 joinGameDialog = alertDialogBuilder.create();

		joinGameDialog.show();
		
		joinGameDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            public void onDismiss(DialogInterface dialog) {
                //If the error flag was set to true then show the dialog again
                if (newUser) {
                	joinGameDialog.show();
                } else {
                    return;
                }

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
        editor.putBoolean("newUser", newUser);
        editor.putString("userName",userName);
        editor.commit();
    }
    
    private void getSavedData(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		try{
	       attachmentCount  =(int) settings.getFloat("attachmentCount", 0);
	       newUser = settings.getBoolean("newUser", true);
	       userName = settings.getString("userName", "");
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
    		
    		SavePhotoTask s = new SavePhotoTask(attachmentCount++, ServerAppActivity.this,latitude,longitude,userName);
			s.execute();
			saveData();
			Toast.makeText(this, "Photo will be Uploaded to Server", Toast.LENGTH_SHORT).show();
			
    	}
    }

	protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	saveData();
    }
    
}