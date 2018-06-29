package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import ml.qingsu.wifi.CommonTools;

public class ListJava extends Activity {
    public int offset;
    public String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.so_list);
        ListView lv = (ListView) this.findViewById(R.id.listView1);
        Bundle b = getIntent().getExtras();
        ArrayList<SparseArray<Object>> al;
        if (b.getSerializable("put") == null) {
            al = new ArrayList<SparseArray<Object>>();
        } else {
            al = (ArrayList<SparseArray<Object>>) b.getSerializable("put");
        }
        this.name = b.getString("name");
        final String type = b.getString("type");
        if (type != null && ("coolapk".equals(type) || "baidu".equals(type) || "zhongzi".equals(type))) {
            this.offset = 1;
        }
        lv.setAdapter(new So_Adapter(this, al));
        this.findViewById(R.id.button_prev).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ("shouji".equals(type)) {
                    if (ListJava.this.offset == 0) {
                        CommonTools.Toast_short(ListJava.this, "已是第一页!");
                        return;
                    }
                    ListJava.this.offset -= 24;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_shouji_so(ListJava.this.name + "&offset=" + ListJava.this.offset, ListJava.this.getnext());

                } else if ("coolapk".equals(type)) {
                    if (ListJava.this.offset == 1) {
                        CommonTools.Toast_short(ListJava.this, "已是第一页!");
                        return;
                    }
                    ListJava.this.offset -= 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_coolapk_so(ListJava.this.name + "&p=" + ListJava.this.offset, ListJava.this.getnext());
                } else if ("baidu".equals(type)) {
                    if (ListJava.this.offset == 1) {
                        CommonTools.Toast_short(ListJava.this, "已是第一页!");
                        return;
                    }
                    ListJava.this.offset -= 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_baidu_so(ListJava.this.name + "&currentPage=" + ListJava.this.offset, ListJava.this.getnext());
                } else if ("zhongzi".equals(type)) {
                    if (ListJava.this.offset == 1) {
                        CommonTools.Toast_short(ListJava.this, "已是第一页!");
                        return;
                    }
                    ListJava.this.offset -= 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_zhongzi_so(ListJava.this.name, ListJava.this.offset, "list", ListJava.this.getnext());
                }
            }

        });
        this.findViewById(id.button_next).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ("shouji".equals(type)) {
                    ListJava.this.offset += 24;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_shouji_so(ListJava.this.name + "&offset=" + ListJava.this.offset, ListJava.this.getnext());
                } else if ("coolapk".equals(type)) {
                    ListJava.this.offset += 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_coolapk_so(ListJava.this.name + "&p=" + ListJava.this.offset, ListJava.this.getnext());

                } else if ("baidu".equals(type)) {
                    ListJava.this.offset += 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_baidu_so(ListJava.this.name + "&currentPage=" + ListJava.this.offset, ListJava.this.getnext());

                } else if ("zhongzi".equals(type)) {
                    ListJava.this.offset += 1;
                    ListJava.this.setProgressBarIndeterminateVisibility(true);
                    Tools.Start_zhongzi_so(ListJava.this.name, ListJava.this.offset, "list", ListJava.this.getnext());

                }
            }

        });

    }

    private Tools.FindAPPCallBack getnext() {
        return new Tools.FindAPPCallBack() {

            @Override
            public void finished(Vector<AppNode> results) {
                final ArrayList<SparseArray<Object>> itemlist = new ArrayList<SparseArray<Object>>();
                if (results == null || results.isEmpty()) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            CommonTools.Toast_long(ListJava.this, "已是最后一页！");
                        }
                    });
                    return;
                }
                Iterator<AppNode> iter = results.iterator();

                while (iter.hasNext()) {

                    AppNode value = iter.next();
                    SparseArray<Object> hash = new SparseArray<Object>();
                    if ("".equals(value.getApp_name().trim())) {
                        continue;
                    }
                    hash.put(0, value.getApp_name());
                    hash.put(1, value.getApp_image());
                    hash.put(2, value.getAppIntroduction());
                    hash.put(3, value.getApp_sites());
                    hash.put(4, value.getApp_size());
                    itemlist.add(hash);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ListView lv = (ListView) ListJava.this.findViewById(id.listView1);
                        lv.setAdapter(new So_Adapter(ListJava.this, itemlist));
                        ListJava.this.setProgressBarIndeterminateVisibility(false);
                    }
                });

            }
        };
    }


}
