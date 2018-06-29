package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ericsoft.bmob.bson.BSONObject;
import com.ericsoft.bmob.restapi.BmobE;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import java.util.List;

public class Music_Adapter extends BaseAdapter {
    private final List<MusicNode> al;
    private final LayoutInflater li;
    private final Activity con;

    @Override
    public int getCount() {

        return this.al.size();
    }

    public Music_Adapter(Activity con, List<MusicNode> al) {
        this.al = al;
        this.con = con;
        li = LayoutInflater.from(con);
    }

    @Override
    public Object getItem(int position) {

        return this.al.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MusicNode mn = this.al.get(position);


        if (v == null) {
            v = this.li.inflate(layout.music_activity_item, null);
        }
        ((TextView) v.findViewById(id.music_des)).setText(mn.getDes());
        ((TextView) v.findViewById(id.music_date)).setText(mn.getDate());
        ((Button) v.findViewById(id.music_good)).setText("赞(" + mn.getGood() + ")");
        v.findViewById(id.music_good).setTag(mn.objectId);
        ((TextView) v.findViewById(id.music_name)).setText(mn.getName());
        v.findViewById(id.music_name).setClickable(true);
        v.findViewById(id.music_name).setTag(mn.getHash());
        v.findViewById(id.music_name).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                Music_Adapter.this.con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://hwkxk.cn/?kw=" + tv.getText() + "&lx=wy")));

            }
        });
        v.findViewById(id.music_good).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Button bt = (Button) v;
                final String id = (String) bt.getTag();
                final BSONObject bo = new BSONObject();
                final int good = Integer.valueOf(bt.getText().toString().replace("赞(", "").replace(")", "")) + 1;
                bo.put("good", good);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (BmobE.update("MusicNode", id, bo).getString("updatedAt") != null) {
                            Music_Adapter.this.con.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    bt.setText("赞(" + good + ")");
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        return v;
    }

}
