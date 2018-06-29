package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

public class ShaojiJava extends Activity {
    boolean Running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_shaoji);
        final EditText et = (EditText) this.findViewById(id.shaoji_xiancheng);
        Button start = (Button) this.findViewById(id.shaoji_start);
        Button stop = (Button) this.findViewById(id.shaoji_stop);
        Button stopp = (Button) this.findViewById(id.shaoji_stoppppp);
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int threadnum = Integer.valueOf(et.getText().toString().trim());
                    if (threadnum <= 0) {
                        return;
                    }
                    ShaojiJava.this.Running = true;
                    while (threadnum-- > 0) {
                        new Thread(new FUCKThread()).start();
                    }
                } catch (Exception e) {
                }
            }
        });
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ShaojiJava.this.Running = false;
            }
        });
        stopp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }

    class FUCKThread implements Runnable {
        @Override
        public void run() {
            while (ShaojiJava.this.Running) {
                //SOME FUCKING THINGS！！！！
                Math.sin(Math.random());
                Math.cos(Math.random());
                Math.tan(Math.random());
                Math.sqrt(Math.pow(Math.random(), Math.random()));
            }
        }
    }
}
