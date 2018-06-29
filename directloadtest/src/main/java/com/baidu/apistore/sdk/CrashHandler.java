package com.baidu.apistore.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                StringBuilder sb = new StringBuilder();
                sb.append(throwable.toString());
                StackTraceElement[] ste = throwable.getStackTrace();
                for (StackTraceElement s : ste) {
                    sb.append("\n");
                    sb.append(s.toString());
                }
                TextView tv = new TextView(mContext);
                tv.setSingleLine(false);
                tv.setVerticalScrollBarEnabled(true);
                tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                tv.setText(sb.toString());
                AlertDialog a = new AlertDialog.Builder(mContext)
                        .setTitle("错误!截图给开发者!")
                        .setView(tv)
                        .setPositiveButton("吼", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                        .create();
                a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                a.show();
                Looper.loop();
            }
        }.start();
    }
}
