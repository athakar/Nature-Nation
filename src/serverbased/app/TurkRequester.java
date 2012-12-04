package serverbased.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TurkRequester {
	public TurkRequester() {
	}
	
	public String executeRequest(String path) {
		String animal = null;
		Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
		try {
			s = new Socket("128.211.216.171", 3436);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		out.println(path);
    		animal = in.readLine();
    		s.close();
		} catch(Exception e) {
			
		}
		
		
		return animal;
	}
	
	public static void main(String[] args) {
		TurkRequester t = new TurkRequester();
		System.out.println(t.executeRequest("http://www.wallpaper-valley.com/animal/animal_101.jpg"));
	}
}
