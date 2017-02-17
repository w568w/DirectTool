package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.Parameters;
import com.example.directloadtest.R;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MusiclookforJava extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(layout.music_look_for_activity);
		this.findViewById(id.music_look_for_search).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et=(EditText) MusiclookforJava.this.findViewById(id.music_look_for_edittext);
				String text=et.getText().toString().trim();
				final ListView lv=(ListView) MusiclookforJava.this.findViewById(id.music_look_for_listView);
				if(text.equals(""))return;
				Parameters p=new Parameters();
				p.put("s", text);
				p.put("size", "30");
				ApiStoreSDK.execute(MusiclookforJava.this,"http://apis.baidu.com/geekery/music/query", p, new ApiCallBack() {
					
					@Override
					public void onSuccess(String arg1) {
						try {

							JSONArray ja=new JSONObject(arg1).getJSONObject("data").getJSONArray("data");
							if(ja.length()>0)
							{
								Music_look_for_Adapter ma=new Music_look_for_Adapter(ja, MusiclookforJava.this);
								lv.setAdapter(ma);
							}
							else
							{
								System.out.println("长度为0");
								MusiclookforJava.this.error();
							}
						} catch (JSONException e) {
							
							e.printStackTrace();
							System.out.println("解析错误");
							MusiclookforJava.this.error();
						}
						
						
					}
					
					@Override
					public void onError() {
						MusiclookforJava.this.error();
						System.out.println("连接错误");
					}
				});
			}
		});
		ListView lv=(ListView) this.findViewById(id.music_look_for_listView);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv=(TextView) view.findViewById(R.id.item_textView);
				Intent i=new Intent(MusiclookforJava.this, Music_addJava.class);
				i.putExtra("name", tv.getText().toString());
				i.putExtra("hash", (String) tv.getTag());
				MusiclookforJava.this.startActivity(i);
				MusiclookforJava.this.finish();
			}
		});
	}
	private void error() {
		Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
	}

}
