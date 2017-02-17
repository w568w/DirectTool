package ml.qingsu.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import java.io.IOException;

import ml.qingsu.wifi.CommonTools;

public class ShutdownDialogViewJava extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Builder adb=new AlertDialog.Builder(this);
		adb.setTitle("最小关机重启");
		adb.setItems(new String[]{"关机","重启","重启至recovery"}, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
					try {
						if(which==0)
							Runtime.getRuntime().exec(new String[] { "/system/bin/su", "-c", "reboot -p" });
						if(which==1)
							Runtime.getRuntime().exec(new String[] { "/system/bin/su", "-c", "reboot now" });
						if(which==2)
							Runtime.getRuntime().exec(new String[] { "/system/bin/su", "-c", "reboot recovery" });
					} catch (IOException e) {
						e.printStackTrace();
						CommonTools.Toast_long(ShutdownDialogViewJava.this, "请求失败!没有ROOT权限?");
					}
					finally
					{
						ShutdownDialogViewJava.this.finish();
					}
				
			}
		});
		adb.show();
	}

}
