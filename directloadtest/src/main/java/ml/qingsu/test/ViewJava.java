package ml.qingsu.test;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ViewJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        this.setContentView(tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        String view;
        if ((view = this.getIntent().getStringExtra("view")) != null) {
            tv.setText(view);
        }
    }

}
