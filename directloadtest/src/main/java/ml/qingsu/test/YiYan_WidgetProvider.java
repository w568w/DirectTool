package ml.qingsu.test;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

public class YiYan_WidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		SharedPreferences sp= context.getSharedPreferences("yiyan", Context.MODE_PRIVATE);
		int N = appWidgetIds.length;
		String message=sp.getString("text", "一言(点击以设置内容)!");
		for (int i = 0; i < N; i++)
		{
		            int appWidgetId = appWidgetIds[i];            
		            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.launcher_view);
		            views.setTextViewText(id.yiyan_textView1, message);
		            Intent intent = new Intent(context, YiYan_settingsJava.class);
		            intent.putExtra("appWidgetId", appWidgetId);
		            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		            views.setOnClickPendingIntent(id.yiyan_textView1, pendingIntent);
		            ComponentName myComponentName = new ComponentName(context,YiYan_WidgetProvider.class);
		            appWidgetManager.updateAppWidget(myComponentName,views);
		}
		
	}

}
