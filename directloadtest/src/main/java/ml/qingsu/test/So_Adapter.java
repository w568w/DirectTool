package ml.qingsu.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.directloadtest.R.drawable;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import java.util.ArrayList;


public class So_Adapter extends BaseAdapter
{
	
	private final LayoutInflater li;
	private final ArrayList<SparseArray<Object>> al;
	private final Context con;
	public So_Adapter(Context con,ArrayList<SparseArray<Object>> al) {
		this.li =LayoutInflater.from(con);
		this.al=al;
		this.con=con;
		
		if(al.size()==0)
		{
			SparseArray<Object> sp=new SparseArray<Object>();
			sp.put(0, "没搜到...");
			sp.put(1, null);
			sp.put(2,"搜不到啊,请更换关键字重试!");
			sp.put(3,"http://qingsu.ml/");
			sp.put(4,"异空间?");
			al.add(sp);
		}
		int lastitemlocation=al.size()-1;
		SparseArray<Object> lastitem=al.get(lastitemlocation);
		if(lastitem.get(0).equals(""))
		{
			al.remove(al.size()-1);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.al.size();
	}

	@Override
	public Object getItem(int position) {
		
		return this.al.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		SparseArray<Object> currect= this.al.get(position);
		if(v==null)
			v= this.li.inflate(layout.so_list_items, null);
		((TextView)v.findViewById(id.name)).setText((String)currect.get(0));
		try {
			String imageurl=(String) currect.get(1);
			if(imageurl==null)
			{
				((ImageView)v.findViewById(id.imageicon)).setImageResource(drawable.ic_build);
			}
			else
			{
				((ImageView)v.findViewById(id.imageicon)).setImageBitmap(ImageService.getImageBitmap(imageurl));
			}
			
		} catch (Exception e) {
			((ImageView)v.findViewById(id.imageicon)).setImageResource(drawable.ic_build);
			e.printStackTrace();
		}
		((TextView)v.findViewById(id.introduction)).setText((String)currect.get(2));
		v.findViewById(id.download).setTag(currect.get(3));
		((Button)v.findViewById(id.download)).setText((String)currect.get(4));
		v.findViewById(id.download).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				So_Adapter.this.con.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse((String)v.getTag())));
			}
		});
		return v;
	}
	
}