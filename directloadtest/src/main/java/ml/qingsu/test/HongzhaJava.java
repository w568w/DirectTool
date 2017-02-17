package ml.qingsu.test;

import android.R.attr;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import ml.qingsu.wifi.CommonTools;

public class HongzhaJava extends Activity {
  String html = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(layout.hongzha_activity);
    final WebView wv = (WebView) this.findViewById(id.hongzha_webView1);
    final EditText et = (EditText) this.findViewById(id.hongzha_editText1);
    final Button bt = (Button) this.findViewById(id.hongzha_button1);

    ProgressBar progressbar =
        new ProgressBar(this, null, attr.progressBarStyleHorizontal);
    progressbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 3));

    progressbar.setMax(100);
    progressbar.setTag(123);
    wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    wv.addView(progressbar);
    wv.setWebChromeClient(this.getwebChromeClient());
    wv.setWebViewClient(new WebViewClient() {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    bt.setText("加载中...");
    bt.setClickable(false);
    new Thread(new Runnable() {

      @Override
      public void run() {

        HongzhaJava.this.html = CommonTools.File_Read_File_From_Assets(HongzhaJava.this, "hongzha.html");
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            bt.setText("炸一下(约20条)");
            bt.setClickable(true);
          }
        });
      }
    }).start();
    bt.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        String phone = et.getText().toString().trim();

        if (phone.equals("")) return;
        HongzhaJava.this.html = HongzhaJava.this.html.replace("{phone}", phone);
        System.out.println(HongzhaJava.this.html);
        wv.stopLoading();
        wv.loadDataWithBaseURL(null, HongzhaJava.this.html, "text/html", "utf-8", null);
      }
    });
  }

  @Override
  protected void onDestroy() {
    WebView wv = (WebView) this.findViewById(id.hongzha_webView1);
    wv.removeAllViews();
    wv.stopLoading();
    wv.destroy();
    super.onDestroy();

  }

  private WebChromeClient getwebChromeClient() {
    return new WebChromeClient() {


      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        View v = view.findViewWithTag(123);
        if (v != null) {
          ProgressBar p = (ProgressBar) v;
          if (newProgress < 100) {

            p.setVisibility(View.VISIBLE);
            p.setProgress(newProgress);
          } else {
            p.setVisibility(View.INVISIBLE);
          }
        }
        super.onProgressChanged(view, newProgress);
      }

    };
  }

}
