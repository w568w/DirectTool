package ml.qingsu.test;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-1-11.
 */
public class Main_Adapter extends BaseAdapter{
    List<Map<Integer, Object>> al;
    Activity con;
    public Main_Adapter(Activity con,List<Map<Integer, Object>> data) {
        this.con=con;
        this.al =data;
    }

    @Override
    public int getCount() {
        return this.al.size();
    }

    @Override
    public Object getItem(int i) {
        return this.al.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view= this.con.getLayoutInflater().inflate(layout.main_activity_items,null);
        TextView tv1= (TextView) view.findViewById(id.textView_title);
        TextView tv2= (TextView) view.findViewById(id.textView_title_introduction);
        tv1.setText((String) this.al.get(i).get(1));
        tv2.setText((String) this.al.get(i).get(2));
        return view;
    }
}
