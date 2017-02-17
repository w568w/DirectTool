package ml.qingsu.test;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

public class YiYan_settingsJava extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(layout.yiyan_settings_activity);
		Bundle bundle = this.getIntent().getExtras();
		final int appWidgetID = bundle.getInt("appWidgetId");
		
		SharedPreferences sp= this.getSharedPreferences("yiyan", Context.MODE_PRIVATE);
		final Editor edit=sp.edit();
		final String message=sp.getString("text", "苟利国家生死以");
		Button bt=(Button) this.findViewById(id.yiyan_button1);
		final EditText et=(EditText) this.findViewById(id.yiyan_editText1);
		et.setText(message);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String input=et.getText().toString().trim();
				if(input.equals(""))return;
				edit.putString("text", input);
				edit.apply();
				RemoteViews views = new RemoteViews(YiYan_settingsJava.this.getPackageName(),
			             layout.launcher_view);
				
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(YiYan_settingsJava.this);
				 views.setTextViewText(id.yiyan_textView1, message);
		            Intent intent = new Intent(YiYan_settingsJava.this, YiYan_settingsJava.class);
		            intent.putExtra("appWidgetId", appWidgetID);
		            PendingIntent pendingIntent = PendingIntent.getActivity(YiYan_settingsJava.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		            views.setOnClickPendingIntent(id.yiyan_textView1, pendingIntent);
		            appWidgetManager.updateAppWidget(appWidgetID, views);
				YiYan_settingsJava.this.finish();
			}
		});
	}

}
