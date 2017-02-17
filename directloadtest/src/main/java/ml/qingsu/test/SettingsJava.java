package ml.qingsu.test;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import java.io.IOException;

import ml.qingsu.wifi.CommonTools;

public class SettingsJava extends Activity {
	boolean IsErJiOpening;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layout.settings_activity);
		
		final SharedPreferences sp= this.getSharedPreferences("fanyi", Context.MODE_PRIVATE);
		
		String apikey=sp.getString("key", "495418620");
		String appname=sp.getString("name", "DIRECT-TOOL");
		this.IsErJiOpening =sp.getBoolean("erji", false);
		final Button b=(Button) this.findViewById(id.settings_button_setapikey);
		ToggleButton tb=(ToggleButton) this.findViewById(id.settings_toggleButton_erji);
		tb.setChecked(this.IsErJiOpening);
		tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingsJava.this.IsErJiOpening =isChecked;
				Editor spe=sp.edit();
				spe.putBoolean("erji",buttonView.isChecked());
				spe.commit();
			}
		});
		b.setText("有道翻译API名:"+appname+"\nAPIkey:"+apikey);
		this.findViewById(id.settings_button_setapikey).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonTools.Dialog_input(SettingsJava.this, "确认", "输入有道翻译的应用名称和APIKey,中间用空格分开!", new CommonTools.OnInput() {

					@Override
					public void onInput(String text) {
						if(text.trim().equals(""))
						{
							Builder ab=new AlertDialog.Builder(SettingsJava.this);
							ab.setTitle("空输入");
							ab.setMessage("您什么都没输入!\n是否是要换回默认APIKey?");
							ab.setPositiveButton("是",new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									Editor edit=sp.edit();
									edit.clear();
									edit.commit();
									SettingsJava.this.refresh(b, sp);
									dialog.dismiss();
								}
							});
							ab.setNegativeButton("取消",new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();

								}
							});
							ab.show();


						}
						else
						{
							String[] input=text.split(" ");
							if(input.length!=2)
							{
								CommonTools.Toast_long(SettingsJava.this, "无效输入!(您刚刚所输入内容已复制到剪贴板)");
								ClipboardManager c = (ClipboardManager) SettingsJava.this.getSystemService(Context.CLIPBOARD_SERVICE);
								c.setText(text);
								return;
							}
							else
							{
								Editor sped=sp.edit();
								sped.putString("name", input[0]);
								sped.putString("key", input[1]);
								sped.commit();
								SettingsJava.this.refresh(b, sp);
							}
						}

					}
				});
			}
		});
		this.findViewById(id.settings_button_setcity).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final SharedPreferences sp= SettingsJava.this.getSharedPreferences("weather", Context.MODE_PRIVATE);
				String cityname=sp.getString("cityname","无");
				AlertDialog.Builder b=new AlertDialog.Builder(SettingsJava.this);
				b.setTitle("确认").setMessage("您当前选择城市为: "+cityname+"\n确定要重新选择城市?");
				b.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Editor spe=sp.edit();
						spe.remove("cityname");
						if(spe.commit())
						{
							CommonTools.Toast_long(SettingsJava.this,"请重选城市!");
							Tools.TianQi(SettingsJava.this);
						}

					}
				});
				b.setNegativeButton("否", null);
				b.show();
			}
		});
		this.findViewById(id.settings_button_thanks).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonTools.Dialog_Android_6_0(SettingsJava.this, "同谢", "感谢以下项目支持:\n" +
						"RippleEffect\n" +
						"Zxing 3.2.0\n" );

			}
		});
		this.findViewById(id.settings_button_dellog).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Tools.DelLog(SettingsJava.this);
				} catch (IOException e) {

					CommonTools.Toast_short(SettingsJava.this, e.toString());
					e.printStackTrace();
				} catch (InterruptedException e) {
					CommonTools.Toast_short(SettingsJava.this, e.toString());
					e.printStackTrace();
				}

			}
		});
		findViewById(id.settings_button_InstallAPK).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
				startActivity(intent);
				Toast.makeText(SettingsJava.this, "开启\"智能安装\"，你懂的!", Toast.LENGTH_SHORT).show();
			}
		});
		this.findViewById(id.settings_button_QQqun).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SettingsJava.this.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://jq.qq.com/?_wv=1027&k=40lIOS5")));
				
			}
		});
		findViewById(id.settings_triple).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText("V2VsY29tZSUyQ015RnJlaW5kLiUwQUVsaW1pbmF0ZSUyMGh1bWFuJTIwdHlyYW5ueSUyQ1RoZSUyMHdvcmxkJTIwYmVsb25ncyUyMHRvJTIwVHJpc29sYXJpcy4");
				Toast.makeText(SettingsJava.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void refresh(Button b,SharedPreferences sp)
	{
		String apikey=sp.getString("key", "495418620");
		String appname=sp.getString("name", "DIRECT-TOOL");
		b.setText("有道翻译API名:"+appname+"\nAPIkey:"+apikey);
	}
	

}
