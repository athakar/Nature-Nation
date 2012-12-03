package serverbased.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<ListClass> {

	Context context; 
    int layoutResourceId;    
    ListClass data[] = null;
	
	public ListAdapter(Context context, int layoutResourceId,
			ListClass[] data) {
		super(context, layoutResourceId, data);
		// TODO Auto-generated constructor stub
		this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}

	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ListHolder();
            holder.txtDistance = (TextView)row.findViewById(R.id.textDist);
            holder.txtTitle = (TextView)row.findViewById(R.id.textName);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ListHolder)row.getTag();
        }
        
        ListClass info = data[position];
        holder.txtTitle.setText(info.title);
        holder.txtDistance.setText(info.distance);
        
        return row;
    }
    
	
	
    static class ListHolder
    {
        TextView txtDistance;
        TextView txtTitle;
    }



}
