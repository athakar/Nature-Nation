package serverbased.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
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

	private ArrayList<String> newsTitleList;
	private String news_content;
	private LinearLayout newsTitleListView;
	private String[] testImageUrls 	= new String [6];
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findingsviewer);
		
		initMembers();
		for(int i=0;i<2;i++){
			testImageUrls[i] = new File(Environment.getExternalStorageDirectory(),"/ServerApp/test" + i + ".jpg").toString();
			newsTitleList.add("WildLife Animal " + i);
		}
		setWidgetRef();
		

	}
	
	private void initMembers() {
		newsTitleListView = (LinearLayout) findViewById(R.id.findingsview);
		newsTitleList = new ArrayList<String>();
	}

	private void setWidgetRef() {
	//	int margin = 1;//getResources().getInteger(R.integer.margin);

		// Populate the list with news article titles
		for (int i = 0; i < 2; i++) {
			// Create the layout for the news item
			LinearLayout listItem = new LinearLayout(this);
			listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			listItem.setOrientation(LinearLayout.HORIZONTAL);
			listItem.setId(i);

			// Create the article icon (image plus progress bar)
			LayoutParams ip = new LayoutParams(85, 85);
		//	ip.setMargins(margin, margin, margin, margin);
			RelativeLayout icon = new RelativeLayout(this);
			icon.setLayoutParams(ip);

			// Create the image for the icon
			ImageView image = new ImageView(this);
			image.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			image.setScaleType(ImageView.ScaleType.FIT_CENTER);
	//		image.setImageResource(R.drawable.icon);
			icon.addView(image);
		//	images.add(image);
			
			// Create the progress bar for the image
			RelativeLayout.LayoutParams pbp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			pbp.addRule(RelativeLayout.CENTER_IN_PARENT);
			int pbmargin = 20;
			pbp.setMargins(pbmargin, pbmargin, pbmargin, pbmargin);
			ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
			pb.setLayoutParams(pbp);
			icon.addView(pb);
		//	pbs.add(pb);
			LoadCachedImageTask l = new LoadCachedImageTask(testImageUrls[i],image,pb,i);
			l.execute();

			listItem.addView(icon);

			// Create the text
			TextView text = new TextView(this);
			text.setText(newsTitleList.get(i));
			text.setTextColor(Color.BLACK);
			text.setTypeface(null, Typeface.BOLD);
			LayoutParams tp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//	tp.setMargins(margin, margin, margin, margin);
			text.setLayoutParams(tp);
			listItem.addView(text);

			// Set the action for clicking on the item
			listItem.setOnClickListener(new View.OnClickListener() {
				public void onClick(View item) {

				}
			});

			newsTitleListView.addView(listItem);

			// Add a separator between items
			View separator = new View(this);
			LayoutParams sp = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
			separator.setLayoutParams(sp);
			separator.setBackgroundColor(Color.BLACK);
			newsTitleListView.addView(separator);
		}


	}

}
