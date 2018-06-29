package ml.qingsu.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import org.json.JSONArray;
import org.json.JSONException;

public class Music_look_for_Adapter extends BaseAdapter {
    JSONArray ja;
    private final LayoutInflater li;

    public Music_look_for_Adapter(JSONArray ja, Context con) {
        this.ja = ja;
        this.li = LayoutInflater.from(con);
    }

    @Override
    public int getCount() {

        return this.ja.length();
    }

    @Override
    public Object getItem(int arg0) {

        try {
            return this.ja.getJSONObject(arg0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = this.li.inflate(layout.simple_item, null);
        }
        TextView t = (TextView) v.findViewById(id.item_textView);
        try {
            t.setText(this.ja.getJSONObject(position).getString("filename"));
            t.setTag(this.ja.getJSONObject(position).getString("hash"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return v;
    }

}
