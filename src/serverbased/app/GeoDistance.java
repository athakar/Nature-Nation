package serverbased.app;

import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;

public class GeoDistance {

	public  double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.00;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist*0.621371;
        }
	
	
	
	public ArrayList<Entry> getAll(){
		
		DBConnector db = new DBConnector("128.211.216.171", "root", "developer");
		ArrayList<Entry> data = null;
		try{
		 data = db.getAllEntries();
		 db.close();
		}catch(Exception e){
			Log.d("DataBase Tag", e.getMessage()+ "");
		}
		
		return data;		
	}
	
	
	public ArrayList<Entry> dataWithinradius(double latitude, double longitude){
		
		DBConnector db = new DBConnector("128.211.216.171", "root", "developer");
		ArrayList<Entry> data = null;
		ArrayList<Entry> result = new ArrayList<Entry>();
		try{
		 data = db.getAllEntries();
		 db.close();
		}catch(Exception e){
			Log.d("DataBase Tag", e.getMessage()+ "");
		}
		for(Entry e: data){
			double lat = Double.parseDouble(e.latitude);
			double lon = Double.parseDouble(e.longitude);
			if(getDistance(lat,lon,latitude,longitude) < 20)
				result.add(e);
		}
		
		
		return result;
		
	}
	
}
