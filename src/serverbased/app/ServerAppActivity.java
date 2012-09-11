package serverbased.app;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
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
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
				
				
				Intent i = new Intent(ServerAppActivity.this, photoview.class);
    			startActivity(i);
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
    
    public void createDataFolder()
    {
    	File folder = new File(Environment.getExternalStorageDirectory() + "/ServerApp/");
    	folder.mkdirs();
    }
    
    
}