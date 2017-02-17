package ml.qingsu.test;

import ml.qingsu.wifi.CommonTools;
import android.app.Activity;
import android.os.Bundle;

public class LockJava extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonTools.System_Lock_screen(this,LockReceiver.class);
		this.finish();
	}

}
