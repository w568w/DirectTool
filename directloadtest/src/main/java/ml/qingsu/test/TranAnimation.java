package ml.qingsu.test;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 17-1-23.
 */
public class TranAnimation {
    public static TranslateAnimation getInstance(float Xoffset, float Yoffset, int time) {
        TranslateAnimation ta = new TranslateAnimation(0, Xoffset, 0, Yoffset);
        ta.setDuration(time);
        return ta;
    }

    public static TranslateAnimation getInstance(View v, float Xto, float Yto, int time) {
        TranslateAnimation ta = new TranslateAnimation(0, Xto - v.getLeft() - v.getWidth() / 2, 0, Yto - v.getTop() - v.getHeight() / 2);
        ta.setDuration(time);
        return ta;
    }
}
