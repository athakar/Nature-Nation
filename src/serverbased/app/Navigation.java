package serverbased.app;

import java.io.Serializable;

public class Navigation implements Serializable {

	public Entry e;
	public double d;
	
	public Navigation(Entry e,double d){
		this.e = e;
		this.d = d;
	}
}
