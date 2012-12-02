package serverbased.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class NavigationActivity extends Activity{

	private ListView listView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_view);
		
		ArrayList<Navigation> radiusData= null;
		try{
		Bundle extras = getIntent().getExtras();
		radiusData = (ArrayList<Navigation>) extras.getSerializable("radiusData");
		}catch(Exception e){
			Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
		}

		ListClass[] list_data = new ListClass[radiusData.size()];
		int i=radiusData.size()-1;
		for(Navigation e1: radiusData){
			Entry e = e1.e;
			String dist = e1.d + "";
			list_data[i--] = new ListClass(e.animal, dist);
		}

		                                     
		                                     ListAdapter adapter = new ListAdapter(this, 
		                                             R.layout.list_view_item, list_data);
		                                     
		                                     
		                                     listView1 = (ListView)findViewById(R.id.listView1);
		                                      
		                                   //  View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
		                                   //  listView1.addHeaderView(header);
		                                     
		                                     listView1.setAdapter(adapter);
		
		
		
		}
		
		
	

}