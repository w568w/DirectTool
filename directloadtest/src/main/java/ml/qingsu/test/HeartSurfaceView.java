package ml.qingsu.test;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.jwetherell.heart_rate_monitor.ImageProcessing;

import java.io.IOException;
import java.util.ArrayList;

//http://blog.csdn.net/jason0539/article/details/10125017
//http://blog.sina.com.cn/s/blog_6d8df7d001013xzc.html
public class HeartSurfaceView extends SurfaceView implements Callback, PreviewCallback {
    SurfaceHolder holder;
    ArrayList<Integer> data=new ArrayList<>();
    Camera myCamera;
    public HeartSurfaceView(Context context) {
        super(context);
        this.init();

    }
private void init()
{
    this.holder = this.getHolder();
    this.holder.addCallback(this);
    this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
}
    public HeartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public HeartSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(this.myCamera == null)
        {
            this.myCamera = Camera.open();//开启相机,不能放在构造函数中，不然不会显示画面.
            try {
                this.myCamera.setPreviewDisplay(this.holder);
                this.myCamera.setPreviewCallback(this);
                Parameters parameters = this.myCamera.getParameters();
                parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);

                this.myCamera.startPreview();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("Oh Shit!!!!!","change");
        Parameters parameters = this.myCamera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        Size size = HeartSurfaceView.getSmallestPreviewSize(i1, i2, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }
        this.myCamera.setParameters(parameters);
        this.myCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.myCamera.stopPreview();//停止预览
        this.myCamera.release();//释放相机资源
        this.myCamera = null;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

        Log.d("Oh Shit!!!!!","------------");
        if (bytes == null)
            throw new NullPointerException();
        Size size = camera.getParameters().getPreviewSize();
        if (size == null)
            throw new NullPointerException();
        int width = size.width;
        int height = size.height;
        int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(bytes.clone(),height,width);
        this.data.add(imgAvg);
        Log.d("Oh Shit!!!!!",imgAvg+"");
    }
    private static Size getSmallestPreviewSize(int width, int height,
                                                      Parameters parameters) {
        Size result = null;
        for (Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea)
                        result = size;
                }
            }
        }
        return result;
    }
}
