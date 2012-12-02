package serverbased.app;
import java.sql.Connection;
import java.sql.DriverManager;
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

	public static void main(String[] args) {
		//Entry entry;
		//Scanner input = new Scanner(System.in);
		DBConnector connection = new DBConnector("pal-nat184-061-116.itap.purdue.edu", "root", "developer");
		ArrayList<Entry> list = new ArrayList<Entry>();
		//String pass = input.nextLine();
		try {
			list = connection.getAllEntries();
			//entry = connection.lookupById(2);
			for(Entry e: list) {
				System.out.print("Id: "+e.id);
				System.out.print("\tUsername: "+e.username);
				System.out.print("\tAnimal: "+e.animal);
				System.out.println("\t Lat+Long: "+e.latitude+" "+e.longitude);
			}
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
