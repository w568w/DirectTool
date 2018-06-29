package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

public class MusiclookforJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.music_look_for_activity);
        this.findViewById(id.music_look_for_search).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et = (EditText) MusiclookforJava.this.findViewById(id.music_look_for_edittext);
                String text = et.getText().toString().trim();
                if ("".equals(text)) {
                    return;
                }
                Intent i = new Intent(MusiclookforJava.this, Music_addJava.class);
                i.putExtra("name", et.getText().toString());
                i.putExtra("hash", "123");
                MusiclookforJava.this.startActivity(i);
                MusiclookforJava.this.finish();

            }
        });
    }

}
