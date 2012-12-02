package serverbased.app;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class LoadGeoDataTask extends AsyncTask<Void,Void,Void>{

	double latitude,longitude;
	ArrayList<Entry> result = new ArrayList<Entry>();
	Context c ;
	Entry[] e = new Entry[10];
	
	LoadGeoDataTask(double latitude,double longitude,Context c){
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.c = c;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try{
        GeoDistance g = new GeoDistance();
        result  = g.dataWithinradius(latitude,longitude);
		}catch(Exception e){
			Log.d("Radius", e.getMessage()+"");
					}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void resul) {
		// TODO Auto-generated method stub
		super.onPostExecute(resul);
		Toast.makeText(c, "Done Radius - " + result.size(), Toast.LENGTH_LONG).show();
	}

	
}
