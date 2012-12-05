package serverbased.app;

public class SQLiteDBEntry {
String count;
String latitude,longitude,name;

public SQLiteDBEntry(String name, String latitude,String longitude, String count){
	this.count = count;
	this.latitude = latitude;
	this.longitude = longitude;
	this.name = name;
}
	

}
