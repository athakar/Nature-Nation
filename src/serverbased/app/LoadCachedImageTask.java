package serverbased.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadCachedImageTask extends AsyncTask<Void,Void,Bitmap> {

	private int i;
	private String filePath;
	private ImageView img;
	private ProgressBar pb;
	
	public LoadCachedImageTask(String filePath, ImageView img, ProgressBar pb, int i){
		this.i =i;
		this.filePath = filePath;
		this.img = img;
		this.pb = pb;
	}
	@Override
	protected Bitmap doInBackground(Void... params) {
		// TODO Auto-generated method stub
				Bitmap  photo = BitmapFactory.decodeFile(filePath);
		return photo;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		img.setImageBitmap(result);
		pb.setVisibility(View.GONE);
	}

}
