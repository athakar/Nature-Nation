package serverbased.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
	public static String GatewaySQL = "";
	private Connection conn;
	private ResultSet rs;
	private Statement stmt;
	private String ip, user, pass;
	private PreparedStatement pstmt;

	public DBConnector(String ip, String username, String password) {
		conn = null;
		stmt = null;
		this.ip = ip;
		this.user = username;
		this.pass = password;
	}

	private void open() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/test?user="+user+"&password="+pass);
	}

	public boolean getTurk(String username) {
		try {
			this.open();
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE user='"+username+"'");
			if(stmt.execute("SELECT * FROM users WHERE user='"+username+"'")) {
				rs = stmt.getResultSet();
			}

			if(rs.isBeforeFirst())
				if(rs.next())
					if(rs.getInt("mturk")==0) {
						return false;
					}
		} catch(Exception e) {}
		return true;
	}
	
	public boolean addUser(String username, String password) {
		try {
			this.open();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE user='"+username+"'");
			if(stmt.execute("SELECT * FROM users WHERE user='"+username+"'")) {
				rs = stmt.getResultSet();
			}

			if(rs.isBeforeFirst())
				if(rs.next())
					if(rs.getString("user").equals(username)) {
						return false;
					}
			pstmt = conn.prepareStatement("INSERT INTO users\nVALUES ('"+username+"', '"+password+"', 0)");
			pstmt.executeUpdate();
			this.close();
		} catch(Exception e) {}
		
		return true;
	}
	
	
	
	
	public boolean login(String username, String password) {
		try {
			this.open();

			//prepare SQL statement
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE user='"+username+"'");
			if(stmt.execute("SELECT * FROM users WHERE user='"+username+"'")) {
				rs = stmt.getResultSet();
			}

			if(rs.isBeforeFirst()) rs.next();
			if(rs.getString("password").equals(password)) {
				return true;
			}

			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void logout() {
		//code for logging out
	}

	public Entry lookupById(int n) {
		Entry e = new Entry();

		try {
			this.open();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data WHERE id='"+n+"'");

			if(stmt.execute("SELECT * FROM data WHERE id='"+n+"'")) {
				rs = stmt.getResultSet();
			} else {
				return null;
			}

			rs.next();
			e.animal = rs.getString("animalname");
			e.id = n;
			e.latitude = rs.getString("latitude");
			e.longitude = rs.getString("longitude");
			e.username =rs.getString("username");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return e;
	}
	
	public void addEntry(Entry e) {
		addEntry(e.latitude, e.longitude, e.username, e.animal);
	}
	
	public void addEntry(String lat, String longi, String user, String animal) {
		String values = longi+"', '"+lat+"', '"+user+"', '"+animal+"'";
		
		try {
			this.open();
			pstmt = conn.prepareStatement("INSERT INTO data\nVALUES ("+values+")");
			pstmt.executeUpdate();
			
		} catch(Exception e) {
		}
	}

	public ArrayList<Entry> getAllEntries() {
		ArrayList<Entry> list = new ArrayList<Entry>();
		Entry e = new Entry();

		try {
			this.open();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data");

			if(stmt.execute("SELECT * FROM data")) {
				rs = stmt.getResultSet();
			} else {
				return null;
			}

			while(rs.next()) {
				e.animal = rs.getString("animalname");
				e.id = Integer.parseInt(rs.getString("id"));
				e.latitude = rs.getString("latitude");
				e.longitude = rs.getString("longitude");
				e.username =rs.getString("username");
				list.add(e);
				e = new Entry();
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return list;
	}

	public void close() throws SQLException {
		if(conn!=null) conn.close();
	}

}
