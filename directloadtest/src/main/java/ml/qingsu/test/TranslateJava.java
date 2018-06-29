package ml.qingsu.test;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ml.qingsu.wifi.CommonTools;

public class TranslateJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = this.getSharedPreferences("fanyi", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        this.setContentView(layout.translate_activity);
        CommonTools.View_Set_EditText_ReadOnly(this, (EditText) this.findViewById(id.translate_result_edittext));
        final String apikey = sp.getString("key", "495418620");
        final String appname = sp.getString("name", "DIRECT-TOOL");
        this.setTitle("如果获取失败，可能是APIkey挂了!");
        final EditText et = (EditText) this.findViewById(id.translate_editText);
        final EditText res = (EditText) this.findViewById(id.translate_result_edittext);
        Button query = (Button) this.findViewById(id.translate_button);
        query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String input = et.getText().toString();
                if (input == null || "".equals(input.trim())) {
                    return;
                }
                try {
                    input = URLEncoder.encode(input, "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                CommonTools.Network_SendGet_ASYNC(TranslateJava.this, "http://fanyi.youdao.com/openapi.do?keyfrom=" + appname + "&key=" + apikey + "&type=data&doctype=json&version=1.1&q=" + input, new CommonTools.OnGet() {
                    @Override
                    public void onGet(final String text) {
                        final JSONObject jo;
                        try {
                            jo = new JSONObject(text);
                            int errorcode = jo.getInt("errorCode");
                            if (errorcode == 0) {
                                res.setText(jo.getJSONArray("translation").getString(0));
                                ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                c.setText(jo.getJSONArray("translation").getString(0));
                                CommonTools.Toast_long(TranslateJava.this, "成功!结果已复制到剪切板!");
                            } else {
                                CommonTools.Toast_long(TranslateJava.this, "获取失败，错误代码:" + errorcode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });


            }
        });
    }

}
