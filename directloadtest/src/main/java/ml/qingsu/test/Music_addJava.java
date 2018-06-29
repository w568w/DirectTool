package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

import ml.qingsu.wifi.CommonTools;

public class Music_addJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.music_add_activity);
        Bundle b = this.getIntent().getExtras();
        final String name = b.getString("name");
        final String hash = b.getString("hash");
        if (name == null || hash == null) {
            this.finish();
            return;
        }

        TextView tv = (TextView) this.findViewById(id.music_add_name);
        tv.setText(name);
        tv.setTag(hash);
        this.findViewById(id.music_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) Music_addJava.this.findViewById(id.music_add_des);
                String des = et.getText().toString().trim();
                if ("".equals(des)) {
                    return;
                }

                MusicNode mn = new MusicNode(CommonTools.System_Get_IMEI(Music_addJava.this), 0, des, name, System.currentTimeMillis() + "", "");
                mn.setHash(hash);
                Tools.savemusic(mn);
                finish();
            }
        });
    }

}
