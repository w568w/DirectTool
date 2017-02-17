package ml.qingsu.test;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 17-1-13.
 */
public class Rotate3dAnimation extends Animation{
    Camera c;
    boolean hasChanged;
    private  float mCenterX;
    private  float mCenterY;
    private  int duration;
    public void setAl(AnimationListener al) {
        this.al = al;
    }

    AnimationListener al;
    public Rotate3dAnimation(float centerX, float centerY,int duration) {
        super();
        mCenterX = centerX;
        mCenterY = centerY;
        this.duration=duration;
    }

    public Rotate3dAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final Matrix matrix = t.getMatrix();
        c.save();
        if(interpolatedTime<0.5)
        {
            c.rotateY(360*interpolatedTime);
        }
        else
        {
            if(!hasChanged)
            {
                al.OnChangeView();
                hasChanged=true;
            }
            c.rotateY(360*interpolatedTime);
        }
        c.getMatrix(matrix);
        c.restore();
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        c=new Camera();
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setDuration(duration);
        hasChanged=false;
    }

    public interface AnimationListener{
        void OnChangeView();
    }
}
