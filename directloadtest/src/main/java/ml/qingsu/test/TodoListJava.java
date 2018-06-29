package ml.qingsu.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import ml.qingsu.wifi.CommonTools;

public class TodoListJava extends Activity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("添加新事项...").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                CommonTools.Dialog_input(TodoListJava.this, "添加", "新的TODO...", new CommonTools.OnInput() {
                    @Override
                    public void onInput(String text) {
                        if ("".equals(text.trim())) {
                            return;
                        }
                        getSharedPreferences("todo", Context.MODE_WORLD_READABLE).edit().putBoolean(text, false).apply();
                        Toast.makeText(TodoListJava.this, "添加完成", Toast.LENGTH_SHORT).show();
                        final ListView lv = (ListView) findViewById(id.todo_listview);
                        lv.setAdapter(new TodoListAdapter());
                    }
                });
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_todo_list);
        final ListView lv = (ListView) this.findViewById(id.todo_listview);
        lv.setAdapter(new TodoListAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoListAdapter a = (TodoListAdapter) adapterView.getAdapter();
                a.setTo(i, !a.isDone(i));
                lv.setAdapter(new TodoListAdapter());
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final TodoListAdapter a = (TodoListAdapter) adapterView.getAdapter();
                new AlertDialog.Builder(TodoListJava.this)
                        .setTitle("确认删除")
                        .setMessage("是否删除该项目:" + adapterView.getAdapter().getItem(i))
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i_inner) {
                                a.delete(i);
                                lv.setAdapter(new TodoListAdapter());
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                return true;
            }
        });
    }

    private class TodoListAdapter extends BaseAdapter {
        SharedPreferences sp;

        public TodoListAdapter() {
            this.sp = getSharedPreferences("todo", Context.MODE_WORLD_READABLE);
        }

        @Override
        public int getCount() {
            return this.sp.getAll().size();
        }

        @Override
        public Object getItem(int i) {
            return this.sp.getAll().keySet().toArray(new String[0])[i];
        }

        public boolean isDone(int i) {
            return sp.getBoolean((String) getItem(i), false);
        }

        public void setTo(int i, boolean isDone) {
            sp.edit().putBoolean((String) getItem(i), isDone).apply();
        }

        public void delete(int i) {
            sp.edit().remove((String) getItem(i)).apply();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView tv = (TextView) view.findViewById(android.R.id.text1);
            tv.setText((String) getItem(i));
            tv.setTextColor(isDone(i) ? Color.GRAY : Color.BLACK);
            return view;
        }
    }
}
