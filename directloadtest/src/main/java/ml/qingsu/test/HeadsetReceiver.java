package ml.qingsu.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import ml.qingsu.wifi.CommonTools;

public class HeadsetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        boolean Isstring = false;
        SharedPreferences sp = arg0.getSharedPreferences("fanyi", Context.MODE_PRIVATE);
        boolean IsErJiOpening = sp.getBoolean("erji", false);
        if (!IsErJiOpening) {
            return;
        }
        String state_s = arg1.getStringExtra("state");
        int state_i = arg1.getIntExtra("state", -1);
        String name = arg1.getStringExtra("name");
        if (state_s != null) {
            Isstring = true;
        }
        if (Isstring) {
            if (state_s.startsWith("1")) {
                this.popit(arg0, name);
            }
        } else {
            if (state_i == 1) {
                this.popit(arg0, name);
            }
        }
    }

    private void popit(Context con, String erji) {
        CommonTools.System_wakeUpAndUnlock(con);
        //CommonTools.Notification_show(con, "耳机已插入", erji, R.drawable.headset);
    }


}
