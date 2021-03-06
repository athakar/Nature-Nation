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
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class MyFindingActivityAlt extends Activity {

	private ArrayList<String> newsTitleList;
	private String news_content;
	private LinearLayout findingsList;
	private String[] testImageUrls 	= new String [6];
	private int attachmentCount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findingsvieweralt);
		Bundle extras = getIntent().getExtras();
		attachmentCount = extras.getInt("count");
		initMembers();
		setList();
		

	}
	
	private void initMembers() {
		findingsList = (LinearLayout) findViewById(R.id.list_alt);
		
	}

private void setList(){
	int margin = 4;
	Bitmap b;
    for(int i=0;i<attachmentCount;i++){
    	LinearLayout listItem = new LinearLayout(this);
		listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listItem.setOrientation(LinearLayout.HORIZONTAL);
		listItem.setId(i);
	//	listItem.setBackgroundResource(R.drawable.news_item_selector);
		listItem.setBackgroundResource(android.R.drawable.list_selector_background);
		
		LayoutParams ip = new LayoutParams(85, 85);
		ip.setMargins(margin, margin, margin, margin);
		RelativeLayout icon = new RelativeLayout(this);
		icon.setLayoutParams(ip);
		
		File f = new File(Environment.getExternalStorageDirectory(),"/ServerApp/Attachment " + i + ".jpg");
		

		ImageView image = new ImageView(this);
		image.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		image.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
		text.setText("Item " + i);
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