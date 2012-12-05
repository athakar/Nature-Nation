package serverbased.app;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseClass {

	public static final String KEY_ROWID ="_id";

	public static final String KEY_COUNT = "count";
	public static final String KEY_NAME = "name";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	
	public static final String DATABASE_NAME = "nature_nation_db";
	public static final String DATABASE_TABLE = "animals";
	public static final int DATABASE_VERSION = 1;
	
	private DBHelper ourHelper;
	private final Context c ;
	private SQLiteDatabase ourDatabase;
	
public DatabaseClass(Context c){
	this.c = c;
}
	
	
	private static class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			arg0.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					      + KEY_COUNT + " INTEGER, "+ KEY_NAME + " TEXT NOT NULL, " + KEY_LATITUDE + " TEXT NOT NULL, " + KEY_LONGITUDE + 
					       " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			arg0.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(arg0);
		}
		
	}

	public DatabaseClass open() throws SQLException{
		ourHelper = new DBHelper(c);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}
	public void delete(){
		c.deleteDatabase(DATABASE_NAME);
	}

	public long createEntry(String name, int count, String latitude, String longitude) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_COUNT, count);
		cv.put(KEY_NAME,name);
		cv.put(KEY_LATITUDE, latitude);
		cv.put(KEY_LONGITUDE, longitude);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	public ArrayList<SQLiteDBEntry> getData(){
		String columns[] = new String[]{KEY_ROWID, KEY_COUNT, KEY_NAME, KEY_LATITUDE, KEY_LONGITUDE};
		ArrayList<SQLiteDBEntry> op = new ArrayList<SQLiteDBEntry> ();
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		

		int iCount = c.getColumnIndex(KEY_COUNT);
		int iName = c.getColumnIndex(KEY_NAME);
		int iLat = c.getColumnIndex(KEY_LATITUDE);
		int iLong = c.getColumnIndex(KEY_LONGITUDE);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			
			SQLiteDBEntry d = new SQLiteDBEntry(c.getString(iName), c.getString(iLat), c.getString(iLong), c.getString(iCount));
			op.add(d);
		
		}
		return op;
	}
	
}
