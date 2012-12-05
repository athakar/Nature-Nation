package serverbased.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MyFindingsActivity extends Activity {

	private LinearLayout findingsList;
	private int attachmentCount;
	private ArrayList<SQLiteDBEntry> list ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findingsviewer);
		Bundle extras = getIntent().getExtras();
		attachmentCount = extras.getInt("count");
		
		 DatabaseClass b = new DatabaseClass(this);
		 b.open();
		list = b.getData();
		b.close();
		initMembers();
		setList();
		

	}
	
	private void initMembers() {
		findingsList = (LinearLayout) findViewById(R.id.findingslist);
		
	}

private void setList(){
	int margin = 4;
	Bitmap b;
    for(int i=list.size()-1 ;i >=0 ;i--){
    	LinearLayout listItem = new LinearLayout(this);
		listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listItem.setOrientation(LinearLayout.HORIZONTAL);
		listItem.setId(i);
	listItem.setBackgroundResource(android.R.drawable.list_selector_background);
		
		LayoutParams ip = new LayoutParams(85, 85);
		ip.setMargins(margin, margin, margin, margin);
		RelativeLayout icon = new RelativeLayout(this);
		icon.setLayoutParams(ip);
			
        File f = new File(Environment.getExternalStorageDirectory(),"/ServerApp/Attachment " + list.get(i).count + ".jpg");
		
		

		
		ImageView image = new ImageView(this);
		image.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		image.setScaleType(ImageView.ScaleType.FIT_CENTER);
		//image.setImageBitmap(b);
		LoadCachedImageTask l = new LoadCachedImageTask(f.getAbsolutePath(),image);
		l.execute();
		icon.addView(image);
		
		listItem.addView(icon);
		listItem.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				  v.setBackgroundResource(android.R.drawable.list_selector_background);
	               
			}         
     
        });
		
		TextView text = new TextView(this);
		text.setText(list.get(i).name);
		text.setTextColor(Color.LTGRAY);
		text.setTypeface(null, Typeface.BOLD);
		LayoutParams tp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tp.setMargins(margin, margin, margin, margin);
		text.setLayoutParams(tp);
		listItem.addView(text);
		
		findingsList.addView(listItem);
		
		View separator = new View(this);
		LayoutParams sp = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
		separator.setLayoutParams(sp);
		separator.setBackgroundColor(Color.BLACK);
		findingsList.addView(separator);

    }
}

}
