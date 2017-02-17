package ml.qingsu.wifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import ml.qingsu.test.Tools;

public class CommonTools {
	public static void Toast_long(Context con,String text)
	{
		Toast.makeText(con, text, Toast.LENGTH_LONG).show();
	}
//	public static void Application_GetDisableAppList(final Context con) throws IOException, InterruptedException
//	{
//		String results=Root_Run_command("pm list packages -d", con);
//		
//	}
	public static String File_Read_File_From_Assets(Context con,String filename) {
		
		String result = "";
		try {
			InputStream is=con.getResources().getAssets().open(filename);
			InputStreamReader isr= new InputStreamReader(is);
			  BufferedReader bufReader = new BufferedReader(isr);
			  String line="";
			  while((line = bufReader.readLine()) != null)
                  result += line;
              bufReader.close();
              isr.close();
              is.close();
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static String File_Get_SD_Path()
	{
		String[] partpaths={
			"/emulated/0",
			"/extSdCard",
 			"/sdcard0",
 			"/sdcard1",
 			"/sdcard2",
 			"/sdcard3",
 			"/sdcard4",
 			"/emulated/0",
 			"/external_sd",
 			"/extsdcard", 
 			"/sdcard",
 			"/sdcard/sdcard",
 			
		};
		File file=new File("/sdcard/");
		if(Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED)
			file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/netease/adcache/"); 
		if(file.exists())
		{
			return file.getAbsolutePath();
		}
		for (String partpath : partpaths) {
			file=new File(partpath+"/");
			if(file.exists()&&file.canWrite())
			{
				return file.getAbsolutePath();
			}
			file=new File("/storage"+partpath+"/"); 
			if(file.exists()&&file.canWrite())
			{
				return file.getAbsolutePath();
			}
			file=new File("/mnt"+partpath+"/"); 
			
			if(file.exists()&&file.canWrite())
			{
				return file.getAbsolutePath();
			}
		}
		return null;
	}
	public static String System_Get_IMEI(Context con) {
		return ((TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
//	public static ApplicationInfo System_Get_Disable_App_Info(final Context con,String packageName) throws NameNotFoundException {
//		PackageManager pm=con.getPackageManager();
//		return pm.getApplicationInfo(packageName, PackageManager.GET_DISABLED_COMPONENTS);
//	}
	public static void System_Lock_screen(Context con, Class<? extends DeviceAdminReceiver> Lockclass)
	{
		DevicePolicyManager policyManager = (DevicePolicyManager) con.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(con,Lockclass); 
		if (policyManager.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器) 
		      System.out.println("已被允许!");
			policyManager.lockNow();// 直接锁屏 
		      android.os.Process.killProcess(android.os.Process.myPid()); 
		    }else{ 
		    	System.out.println("不被允许!");
			CommonTools.activeManager(con,componentName);//激活设备管理器获取权限
		} 
		
	}
	private static void activeManager(Context con, ComponentName cn) {
	    //使用隐式意图调用系统方法来激活指定的设备管理器 
	    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN); 
	    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn); 
	    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏"); 
	    con.startActivity(intent); 
	    System.out.println("已启动硬件管理器!");
	  }
	public static void System_Install_New_ShortCut(Context con,String name,int icon_id,Class<? extends Activity> Goto) {
		Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		shortcutintent.putExtra("duplicate", true);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		Parcelable icon = ShortcutIconResource.fromContext(con.getApplicationContext(), icon_id);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		 shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(con , Goto));
		 con.sendBroadcast(shortcutintent);
	}
	public static void View_Set_EditText_ReadOnly(Context con, final EditText et)
	{
		et.setOnKeyListener(null);
		et.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				et.setInputType(InputType.TYPE_NULL);
				
				return false;
			}
		});
		InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);    
	}
	@SuppressLint("NewApi")
	public static void Notification_show(Context con, String showtext, String info, int iconRID)
	{
		NotificationManager manger = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification.Builder b=new Notification.Builder(con);
		b.setSmallIcon(iconRID);
		b.setWhen(System.currentTimeMillis());
		b.setContentTitle(showtext);
		b.setContentText(info);
		b.setTicker(showtext);
		b.setAutoCancel(true); 
		b.setOngoing(true);
		Notification notification = b.build();  
		
		manger.notify(0x123, notification);  
	}
	public static void System_wakeUpAndUnlock(Context context){  
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);  
        KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁  
        kl.disableKeyguard();  
        //获取电源管理器对象  
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);  
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
        WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕  
        wl.acquire();  
        //释放  
        wl.release();  
    }  
	public static String Root_Run_command(String command,Context con) throws IOException, InterruptedException
	{
		StringBuffer ret = new StringBuffer();
		Tools.upgradeRootPermission(con.getPackageCodePath());
		Process process = Runtime.getRuntime().exec("su");
		DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
		DataInputStream dataInputStream = new DataInputStream(process.getInputStream());
		dataOutputStream.writeBytes(command+"\n");
		dataOutputStream.writeBytes("exit\n");
		dataOutputStream.flush();
		InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			ret.append(line);
		}
		bufferedReader.close();
		inputStreamReader.close();
		process.waitFor();
		if (dataOutputStream != null) {
			dataOutputStream.close();
		}
		if (dataInputStream != null) {
			dataInputStream.close();
		}
		return ret.toString();
	}
	public static String Root_Run_command(String[] command,Context con) throws IOException, InterruptedException
	{
		StringBuffer ret = new StringBuffer();
		Tools.upgradeRootPermission(con.getPackageCodePath());
		Process process = Runtime.getRuntime().exec("su");
		DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
		DataInputStream dataInputStream = new DataInputStream(process.getInputStream());
		dataOutputStream.writeBytes(command+"\n");
		dataOutputStream.writeBytes("exit\n");
		dataOutputStream.flush();
		InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			ret.append(line);
		}
		bufferedReader.close();
		inputStreamReader.close();
		process.waitFor();
		if (dataOutputStream != null) {
			dataOutputStream.close();
		}
		if (dataInputStream != null) {
			dataInputStream.close();
		}
		return ret.toString();
	}
	private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
 
        if (logo == null) {
            return src;
        }
 
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
 
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
 
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
 
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
 
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
 
        return bitmap;
    }
	public static boolean Image_createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath) {
        try {
            if (content == null || "".equals(content)) {
                return false;
            }
            //配置参数
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
//            hints.put(EncodeHintType.MARGIN, 2); //default is 4
 
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix,  hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
 
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
 
            if (logoBm != null) {
                bitmap = CommonTools.addLogo(bitmap, logoBm);
            }
 
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap != null && bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return false;
    }
	 
	public static void Network_OpenNetworkSettings(Context con) {
		Intent intent=new Intent();
		if(VERSION.SDK_INT>10){
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
		con.startActivity(intent);
		
	}
	public static void SQLite_async_open(final File db,final CommonTools.SQLite_OnreadOK so) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SQLiteDatabase sqd=SQLiteDatabase.openDatabase(db.getAbsolutePath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
				so.OnreadOK(sqd);
			}
		}).start();
	}
	public static boolean Network_3GConnected(Context con)
	{
		 ConnectivityManager cm = (ConnectivityManager)con .getSystemService(Context.CONNECTIVITY_SERVICE);   
	     NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		return networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE;
	}
	public static String String_Between(String str, String leftstr, String rightstr)  
	{  
	    if(str.indexOf(leftstr)==-1||str.indexOf(rightstr)==-1)
	    	return "";
		int i = str.indexOf(leftstr) + leftstr.length();  
	    String temp = str.substring(i, str.indexOf(rightstr,i));  
	    return temp;  
	}  
	public static boolean Network_WIFIConnected(Context con)
	{
		 ConnectivityManager cm = (ConnectivityManager)con .getSystemService(Context.CONNECTIVITY_SERVICE);   
	     NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		return networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
	}
	public static boolean Network_WIFIAvailable(Context con)
	{
		 ConnectivityManager cm = (ConnectivityManager)con .getSystemService(Context.CONNECTIVITY_SERVICE);   
	     NetworkInfo networkINfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return networkINfo != null
				&& networkINfo.isAvailable();
	}
	public static boolean Network_isAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == State.CONNECTED) {
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    } 
	public static void Toast_setduration(final Toast toast, int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
	public static void Toast_short(Context con,String text)
	{
		Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
	}	
	public static String decode_Unicode(String theString) {      
		   
	    char aChar;      
	   
	     int len = theString.length();      
	   
	    StringBuffer outBuffer = new StringBuffer(len);      
	   
	    for (int x = 0; x < len;) {      
	   
	     aChar = theString.charAt(x++);      
	   
	     if (aChar == '\\') {      
	   
	      aChar = theString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	   
	       // Read the xxxx      
	   
	       int value = 0;      
	   
	       for (int i = 0; i < 4; i++) {      
	   
	        aChar = theString.charAt(x++);      
	   
	        switch (aChar) {      
	   
	        case '0':      
	   
	        case '1':      
	   
	        case '2':      
	   
	        case '3':      
	   
	       case '4':      
	   
	        case '5':      
	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	         else if (aChar == 'n')      
	          aChar = '\n';      
	         else if (aChar == 'f')        
	          aChar = '\f';      
	         outBuffer.append(aChar);      
	        }      
	       } else     
	       outBuffer.append(aChar);      
	      }      
	      return outBuffer.toString();      
	     }     
	public static Bitmap Image_resizeBitmap(Bitmap bit,int kuan,int gao)
	{
		int width = bit.getWidth();
		int height = bit.getHeight();
		float scaleWidth = (float) kuan / width;
		float scaleHeight = (float) gao / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bit, 0, 0,width, height, matrix, true);
		return resizedBitmap;
	}
	public static void File_WritetoSDFrom_assets(Context con,String assets,int buffersize)
	{
		InputStream ips;
		try {
			ips=con.getResources().getAssets().open(assets);
			File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+assets);
			FileOutputStream ofs = new FileOutputStream(f.getAbsolutePath());
			byte[] buffer = new byte[buffersize];
			int count = 0;
			while((count = ips.read(buffer)) > 0){
				  ofs.write(buffer, 0 ,count);
			}
			ofs.flush();
			ofs.close();
			ips.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void File_WritetoSDFrom_bytes(byte[] data,String filename){
		File f=new File(Environment.getExternalStorageDirectory().getPath()+"/"+filename);
		if(f.exists())
			f.delete();
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			FileOutputStream fops = new FileOutputStream(f);
			fops.write(data);
			fops.flush();
			fops.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void File_InstallAPK(Context con,String apk)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);  
		intent.setDataAndType(Uri.fromFile(new File(apk)),
				 "application/vnd.android.package-archive");  
		con.startActivity(intent);
	}	
//	public static boolean Network_PostGet(String url)
//	{
//		HttpGet hg=new HttpGet(url);
//		try {
//			HttpResponse httpResponse =new DefaultHttpClient().execute(hg);
//			
//			return (httpResponse.getStatusLine().getStatusCode() == 200);
//		} catch (ClientProtocolException e) {
//		
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			return false;
//		}
//	}
	public static Bitmap Network_DownloadImage(String url)
	{
		try
		{
			HttpURLConnection httpUrlConnection = null;
			URL urls = new URL(url); 
			httpUrlConnection = (HttpURLConnection)urls.openConnection();
			httpUrlConnection.setDoInput(true); 
			httpUrlConnection.setConnectTimeout(15000);
			httpUrlConnection.connect(); 
			InputStream is =httpUrlConnection.getInputStream();
	        Bitmap bitmap = BitmapFactory.decodeStream(is);  
	        is.close();  
	        if(httpUrlConnection!=null)
	        	httpUrlConnection.disconnect();
	        return bitmap;
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        	
        }
		return null;
	}	
	public static Bitmap Image_drawableToBitmap(Drawable drawable) {       

		BitmapDrawable bd = (BitmapDrawable) drawable;
		
        return bd.getBitmap();

}

	public static void Network_SendPost_ASYNC(final Activity con, final String url,final String body, final OnGet og)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String res=Network_SendPost(url,body);
				con.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						og.onGet(res);
					}
				});
			}
		}).start();
	}
	public static String Network_SendPost(String url,String body)
	{
		try {
			URL urls = new URL(url);
			HttpURLConnection httpConn=(HttpURLConnection)urls.openConnection();
			httpConn.setDoOutput(true);//使用 URL 连接进行输出
			httpConn.setDoInput(true);//使用 URL 连接进行输入
			httpConn.setUseCaches(false);//忽略缓存
			httpConn.setRequestMethod("POST");//设置URL请求方法
            httpConn.setRequestProperty("Content-length", "" + body.getBytes("utf-8").length);
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("charset", "UTF-8");
            httpConn.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(body.getBytes("utf-8"));
			outputStream.close();
			StringBuffer sb = new StringBuffer();
			String readLine;
			BufferedReader responseReader;
			//处理响应流，必须与服务器响应流输出的编码一致
			responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			httpConn.disconnect();
			return sb.toString();
		}
		catch (Exception e)
		{

		}
		return "";
	}
	public static void Network_SendGet_ASYNC(final Activity con, final String url, final OnGet og)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String res=Network_SendGet(url);
				con.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						og.onGet(res);
					}
				});
			}
		}).start();
	}
	public static String Network_SendGet(String url){
	    InputStream is;
	    String result ="";
	    HttpURLConnection httpUrlConnection;
	    try {
			URL urls = new URL(url);
			
			httpUrlConnection = (HttpURLConnection)urls.openConnection();
			httpUrlConnection.setDoInput(true); 
			httpUrlConnection.setConnectTimeout(10000);
			httpUrlConnection.connect();
			is =httpUrlConnection.getInputStream();
			BufferedReader reader =new BufferedReader(new InputStreamReader(is,"utf-8"));
			StringBuilder sb =new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
	            sb.append(line +"\n");
	        }
			reader.close();
			is.close();
			httpUrlConnection.disconnect();
			result=sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    
		return result;
	}
	public static void Dialog_input(Context con,String button,String hint,final CommonTools.OnInput oi)
	{
		LayoutInflater iInflater = LayoutInflater.from(con);
		ViewGroup vv=(ViewGroup) iInflater.inflate(R.layout.input, null);
		final EditText v= (EditText)vv.findViewById(id.editText);
		v.setHint(hint);
		Builder ab = new Builder(con);
		ab.setTitle("输入").setView(vv).setPositiveButton(button, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				oi.onInput(v.getText().toString());
			}
		});
        ab.show();
	}
	public static void Dialog_Android_6_0(Context con,String button,String hint)
	{
		Builder adb=new Builder(con);
		adb.setMessage(hint);
		adb.setPositiveButton(button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		adb.show();
	}	
	public interface OnInput
	{
		void onInput(String text);
	}
	public interface OnGet
	{
		void onGet(String text);
	}
	public interface SQLite_OnreadOK
	{
		void OnreadOK(SQLiteDatabase sqd);
	}

	public static byte[] Image_Bitmap2Bytes(Bitmap bit) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bit.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();  
		
	}	
}
