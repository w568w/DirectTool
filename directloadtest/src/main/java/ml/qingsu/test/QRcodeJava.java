package ml.qingsu.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.ClipboardManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;

import java.io.File;

import ml.qingsu.wifi.CommonTools;

public class QRcodeJava extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.qrcode_activity);
		final EditText et=(EditText) this.findViewById(id.qrcode_editText);
		final SeekBar sb=(SeekBar) this.findViewById(id.qrcode_seekBar1);
		final ImageView iv=(ImageView) this.findViewById(id.qrcode_imageView);
		sb.setMax(1000);

		this.findViewById(id.qrcode_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content=et.getText().toString();
				if(content!=null &&!content.equals(""))
				{
					int h=sb.getProgress();
					CommonTools.Image_createQRImage(content, h,h, null, Environment.getExternalStorageDirectory().getAbsolutePath()+"/qr.jpg");
					iv.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/qr.jpg"));
				}
			}
		});
		iv.setLongClickable(true);
		iv.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				String imagePath = Environment.getExternalStorageDirectory() + File.separator + "qr.jpg";
		        Uri imageUri = Uri.fromFile(new File(imagePath));
		        Intent shareIntent = new Intent();
		        shareIntent.setAction(Intent.ACTION_SEND);
		        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
		        shareIntent.setType("image/*");
				QRcodeJava.this.startActivity(Intent.createChooser(shareIntent, "分享到"));
				return false;
			}
		});
//		findViewById(R.id.qrcode_scanbutton).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent openCameraIntent = new Intent(QRcodeJava.this,   CaptureActivity.class);  
//				startActivityForResult(openCameraIntent, 0x123); 
//			}
//		});
	}
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        // 取得返回信息  
        if (resultCode == Activity.RESULT_OK && requestCode==0x123) {
            Bundle bundle = data.getExtras();  
            String scanResult = bundle.getString("result");  
            CommonTools.Dialog_Android_6_0(this, "复制到剪切板", "扫描结果:\n"+scanResult);
            ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			c.setText(scanResult);
        }  
    } 

}
