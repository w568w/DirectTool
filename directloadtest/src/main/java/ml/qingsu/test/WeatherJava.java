package ml.qingsu.test;

import android.R.layout;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ml.qingsu.wifi.CommonTools;

public class WeatherJava extends ListActivity {
	ArrayAdapter<String> itemsadapter;
	boolean select;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.itemsadapter =new ArrayAdapter<>(this, layout.simple_list_item_1 );
		final String c= this.read();
		this.itemsadapter.add("你在"+c+"过得怎么样?");
		this.itemsadapter.add("读取数据中...");
		this.setListAdapter(this.itemsadapter);
		CommonTools.Network_SendGet_ASYNC(this, "https://api.thinkpage.cn/v3/weather/now.json?key=gfcrmdrtrgriad5f&location=" + c + "&language=zh-Hans&unit=c", new CommonTools.OnGet() {
			@Override
			public void onGet(String text) {
				if(text.equals(""))
				{
					CommonTools.Toast_long(WeatherJava.this, "错误了,请确定您的网络连接状态！");
					WeatherJava.this.finish();
					return;
				}
				try {
					JSONObject jo=new JSONObject(text);
					String status_code=jo.optString("status_code","none");
					if("AP010010".equals(status_code))
					{
						//无该城市
						//把已有的城市删掉...
						save("0");
						CommonTools.Toast_long(WeatherJava.this, "错误了,没有找到该城市！");
						finish();
						return;
					}
					else if("none".equals(status_code))
					{
						JSONObject now=jo.getJSONArray("results").getJSONObject(0).getJSONObject("now");
						String location=jo.getJSONArray("results").getJSONObject(0).getJSONObject("location").optString("name");
						itemsadapter.clear();
						/*
						Eg.
						实时北京气温: -2℃，阴
						----------------------
						2017-01-26，-6~8℃，白天晴，晚上晴，北风3级(15km/h)
						....
						 */
						itemsadapter.add("实时"+location+"气温: "+now.optString("temperature")
						+"℃，"+now.optString("text"));
						itemsadapter.add("-----------");
						//获取逐日数据
						CommonTools.Network_SendGet_ASYNC(WeatherJava.this, "https://api.thinkpage.cn/v3/weather/daily.json?key=gfcrmdrtrgriad5f&location=" + c + "&language=zh-Hans&unit=c&start=0&days=5", new CommonTools.OnGet() {
							@Override
							public void onGet(String text) {
								if(text.equals(""))
								{
									CommonTools.Toast_long(WeatherJava.this, "错误了,请确定您的网络连接状态！");
									WeatherJava.this.finish();
									return;
								}
								try {
									JSONArray ja=new JSONObject(text).optJSONArray("results").optJSONObject(0).optJSONArray("daily");
									for(int i=0;i<ja.length();i++)
									{
										JSONObject jo2=ja.getJSONObject(i);
										StringBuilder sb=new StringBuilder();
										sb.append(jo2.optString("date"));
										sb.append("，");
										sb.append(jo2.optString("low"));
										sb.append("~");
										sb.append(jo2.optString("high"));
										sb.append("℃，白天");
										sb.append(jo2.optString("text_day"));
										sb.append("，晚上");
										sb.append(jo2.optString("text_night"));
										sb.append("，");
										sb.append(jo2.optString("wind_direction"));
										sb.append("风");
										sb.append(jo2.optString("wind_scale"));
										sb.append("级(");
										sb.append(jo2.optString("wind_speed"));
										sb.append("km/h)");
										itemsadapter.add(sb.toString());
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});


					}
				} catch (JSONException e) {
					e.printStackTrace();
					CommonTools.Toast_long(WeatherJava.this, "错误了,解析时出错！");
					finish();
				}

			}
		});

	}
	private void save(String cityname)
	{
		SharedPreferences sp= this.getSharedPreferences("weather", Context.MODE_PRIVATE);
		Editor edit=sp.edit();
		edit.putString("cityname", cityname);
		edit.apply();
	}
	private String read() {
		SharedPreferences sp = this.getSharedPreferences("weather", Context.MODE_PRIVATE);
		return sp.getString("cityname", "");
	}
	

}
