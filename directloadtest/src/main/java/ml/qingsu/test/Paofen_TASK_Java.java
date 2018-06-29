package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class Paofen_TASK_Java extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sp = getSharedPreferences("paofen", MODE_PRIVATE);
        int score = sp.getInt("score", 0);
        sp.edit().putInt("score", ++score).apply();
        startActivity(new Intent(Paofen_TASK_Java.this, Paofen_TASK_Java.class));
    }
}
