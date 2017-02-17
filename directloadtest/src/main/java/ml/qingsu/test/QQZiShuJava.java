package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import ml.qingsu.wifi.CommonTools;

public class QQZiShuJava extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      this.setContentView(layout.qq_zishu_actvity);
    final EditText et = (EditText) this.findViewById(id.qq_zishu_editText);
    Button b = (Button) this.findViewById(id.qq_zishu_button1);
    b.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
          String text=et.getText().toString();
          Intent shareIntent = new Intent();
          shareIntent.setAction(Intent.ACTION_SEND);
          shareIntent.putExtra(Intent.EXTRA_TEXT,text);
          shareIntent.setType("text/plain");
          CommonTools.Toast_short(QQZiShuJava.this,"请选择\"分享到QQ\"");
          QQZiShuJava.this.startActivity(Intent.createChooser(shareIntent, "分享到QQ即可"));
      }
    });
  }

}
