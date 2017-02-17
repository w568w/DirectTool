package ml.qingsu.test;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

public class FenbeiJava extends Activity implements SensorEventListener {
  static final int SAMPLE_RATE_IN_HZ = 8000;
  static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(FenbeiJava.SAMPLE_RATE_IN_HZ,
      AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
  AudioRecord mAudioRecord;
  boolean isGetVoiceRun;
  Object mLock;
  TextView fenbei;
  TextView or;
  private SensorManager mSensorManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    this.setContentView(layout.feibei_activity);
    this.mLock = new Object();
    this.fenbei = (TextView) this.findViewById(id.feibei_feibei);
    this.or = (TextView) this.findViewById(id.feibei_orientation);
    this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    this.getNoiseLevel();
  }
  private View getContentView(){
    ViewGroup view = (ViewGroup) this.getWindow().getDecorView();
    FrameLayout content = (FrameLayout) view.getChildAt(0);
    return content.getChildAt(0);
  }
  @Override
  protected void onResume() {
    super.onResume();
    // 为系统的方向传感器注册监听器
    this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        SensorManager.SENSOR_DELAY_GAME);
  }

  @Override
  protected void onPause() {
    super.onPause();
    // 取消注册
    this.mSensorManager.unregisterListener(this);
  }

  @Override
  protected void onDestroy() {
    this.isGetVoiceRun = false;
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    super.onDestroy();
  }

  public void getNoiseLevel() {
    if (this.isGetVoiceRun) {
      return;
    }
    try {
      this.mAudioRecord =
              new AudioRecord(AudioSource.MIC, FenbeiJava.SAMPLE_RATE_IN_HZ,
                      AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT, FenbeiJava.BUFFER_SIZE);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();

    }
    if (this.mAudioRecord == null) {
      return;
    }
    this.isGetVoiceRun = true;

    new Thread(new Runnable() {
      @Override
      public void run() {
        while (FenbeiJava.this.mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {

          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        FenbeiJava.this.mAudioRecord.startRecording();
        short[] buffer = new short[FenbeiJava.BUFFER_SIZE];
        while (FenbeiJava.this.isGetVoiceRun) {
          // r是实际读取的数据长度，一般而言r会小于buffersize
          int r = FenbeiJava.this.mAudioRecord.read(buffer, 0, FenbeiJava.BUFFER_SIZE);
          long v = 0;
          // 将 buffer 内容取出，进行平方和运算
          for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
          }
          // 平方和除以数据总长度，得到音量大小。
          double mean = v / (double) r;
          final double volume = 10 * Math.log10(mean);
          runOnUiThread(new Runnable() {

            @Override
            public void run() {
              fenbei.setText("音量:" + volume + "分贝");

            }
          });
          // 大概一秒十次
          synchronized (mLock) {
            try {
              mLock.wait(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
        FenbeiJava.this.mAudioRecord.stop();
        FenbeiJava.this.mAudioRecord.release();
        FenbeiJava.this.mAudioRecord = null;
      }
    }).start();
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
      // 获取绕Z轴转过的角度
      this.or.setText("角度:" + event.values[0] + "°");
    }

  }


}
