package ml.qingsu.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.directloadtest.R;

public class PaoFenJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pao_fen_java);
        Button b= (Button) findViewById(R.id.paofen_button);
        TextView tv=(TextView)findViewById(R.id.paofen_textView);
        final SharedPreferences sp=getSharedPreferences("paofen",MODE_PRIVATE);
        tv.setText(sp.getInt("score",-1)+"");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PaoFenJava.this)
                        .setTitle("测试")
                        .setMessage("确认要开始?\n可能出现死机现象，别担心就好")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sp.edit().putInt("score",0).apply();
                                startActivity(new Intent(PaoFenJava.this,Paofen_TASK_Java.class));
                            }
                        })
                        .setNegativeButton("无可奉告", null)
                        .show();
            }
        });

    }

}
