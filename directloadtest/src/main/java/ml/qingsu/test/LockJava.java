package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;

import ml.qingsu.wifi.CommonTools;

public class LockJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.finish();
        CommonTools.System_Lock_screen(this, LockReceiver.class);
    }

}
