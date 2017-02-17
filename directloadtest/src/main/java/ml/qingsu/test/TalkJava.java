package ml.qingsu.test;

import android.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

import org.json.JSONException;
import org.json.JSONObject;

import ml.qingsu.wifi.CommonTools;

public class TalkJava extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		this.setContentView(R.layout.talk_acticity);
		
		final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, layout.simple_list_item_1);
		final EditText et=(EditText) this.findViewById(id.editText_talk);
		ListView lv=(ListView) this.findViewById(id.listView_talk);
		lv.setAdapter(aa);
		this.findViewById(id.button_talk).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text=et.getText().toString().trim();
				if(!text.equals(""))
				{
					aa.add("["+text+"]");
					aa.notifyDataSetChanged();
					et.setText("");
					Talk(text,aa);
				}
			}
		});
		
	}
	private void Talk(final String text,final ArrayAdapter<String> aab)
	{
		this.setProgressBarIndeterminateVisibility(true);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				final JSONObject body=new JSONObject();
				try {
					body.put("key","6cc14e80db4087f970205b81653da577");
					body.put("info",text);
				} catch (JSONException e) {
					e.printStackTrace();
				}

                final String answer=CommonTools.Network_SendPost("http://www.tuling123.com/openapi/api",body.toString());
				if(!answer.equals(""))
				{
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								String show;
								JSONObject jo=new JSONObject(answer);
								int code=jo.optInt("code",-1);
                                switch (code)
								{
									case -1:
										show="网络错误!";
										break;
									case 40004:
										show="今天的API请求已达上限了....";
										break;
									case 100000:
										show=jo.optString("text","");
										break;
									case 200000:
										show=jo.optString("url","");
										break;
									default:
										show="我听懂啦,但是不知道怎么用文字表达给你..";
								}
								aab.add(show);

							} catch (JSONException e) {
								e.printStackTrace();
							}

							aab.notifyDataSetChanged();
							setProgressBarIndeterminateVisibility(false);
						}
					});
				}
			}
		}).start();
	}
}
