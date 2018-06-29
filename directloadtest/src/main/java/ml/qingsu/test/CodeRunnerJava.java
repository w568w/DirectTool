package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.directloadtest.R;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import ml.qingsu.wifi.CommonTools;

public class CodeRunnerJava extends Activity {
    EditText output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_runner_java);
        output = (EditText) findViewById(R.id.code_runner_output);
        CommonTools.View_Set_EditText_ReadOnly(this, output);
        final EditText et = (EditText) findViewById(R.id.code_runner_code);
        Button b = (Button) findViewById(R.id.code_runner_run);
        final Spinner s = (Spinner) findViewById(R.id.code_runner_spinner);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = et.getText().toString();
                TextView tv = (TextView) s.getSelectedView();
                if ("".equals(code)) {
                    return;
                }
                StringBuilder sb = new StringBuilder();

                try {
                    sb.append("lang=");
                    sb.append(URLEncoder.encode(tv.getText().toString(), "utf-8"));
                    sb.append("&code=");
                    sb.append(URLEncoder.encode(et.getText().toString(), "utf-8"));
                    sb.append("&private=True&run=True&submit=Submit");
                    getResult(sb.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getResult(final String body) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urls = new URL("http://codepad.org/");
                    final HttpURLConnection httpConn = (HttpURLConnection) urls.openConnection();
                    httpConn.setDoOutput(true);//使用 URL 连接进行输出
                    httpConn.setDoInput(true);//使用 URL 连接进行输入
                    httpConn.setUseCaches(false);//忽略缓存
                    httpConn.setRequestMethod("POST");//设置URL请求方法
                    httpConn.setRequestProperty("Content-length", "" + body.getBytes("utf-8").length);
                    httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
                    httpConn.setRequestProperty("charset", "UTF-8");
                    httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    OutputStream outputStream = httpConn.getOutputStream();
                    outputStream.write(body.getBytes("utf-8"));
                    outputStream.close();
                    final String loca = httpConn.getHeaderField("Location");
                    final String a = CommonTools.Network_SendGet(httpConn.getHeaderField("Location"));
                    httpConn.disconnect();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CodeRunnerJava.this, a, Toast.LENGTH_SHORT).show();
                        }
                    });

                    final String code = CommonTools.String_Between(a, "<a name=\"output\"><span class=\"heading\">Output:</span></a>", "</td></tr></table>");
                    output.post(new Runnable() {
                        @Override
                        public void run() {
                            output.setText(Html.fromHtml(code));
                        }
                    });

                } catch (Exception throwable) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(throwable.toString());
                    StackTraceElement[] ste = throwable.getStackTrace();
                    for (StackTraceElement s : ste) {
                        sb.append("\n");
                        sb.append(s.toString());
                    }
                    Toast.makeText(CodeRunnerJava.this, sb.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).start();

    }
}
