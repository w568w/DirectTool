package ml.qingsu.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.text.ClipboardManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.MyApplication;
import com.baidu.apistore.sdk.Parameters;
import com.ericsoft.bmob.restapi.BmobE;
import com.example.directloadtest.R;
import com.example.directloadtest.R.drawable;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;
import com.example.wifipassword.WifiInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import ml.qingsu.wifi.CommonTools;
import ml.qingsu.wifi.MD5;

public class Mainjava extends Activity {
    private Camera camera;
    private HeadsetReceiver hr;


    public void QQCheHuiXiaoXi() {
        // 打开编辑框
        CommonTools.Dialog_input(this, "输好了", "输入姓名", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text_name) {
                if ("".equals(text_name.trim())) {
                    return;
                }
                CommonTools.Dialog_input(Mainjava.this, "输好了", "输入动作", new CommonTools.OnInput() {

                    @Override
                    public void onInput(String text_active) {
                        if ("".equals(text_active.trim())) {
                            return;
                        }
                        StringBuffer sb = new StringBuffer();
                        sb.append(text_name);
                        sb.append((char) 0x202e);
                        sb.append(new StringBuffer(text_active).reverse());
                        sb.append((char) 0x202d);
                        ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        c.setText(sb.toString());
                        CommonTools.Toast_long(Mainjava.this, "完成!请到QQ中粘贴到昵称即可!");
                    }
                });

            }
        });

    }

    public void QQDaShang() {
        // 打开编辑框
        CommonTools.Dialog_input(this, "输好了", "打赏了1000.00元红包", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text_name) {
                if ("".equals(text_name.trim())) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[em]e10033[/em] ");
                sb.append(text_name);
                sb.append(" [em]e10011[/em]");
                ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(sb.toString());
                CommonTools.Toast_long(Mainjava.this, "完成!请到QQ中粘贴到说说回复即可!");
            }
        });
    }

    public void La_quan_quan() {
        CommonTools.Dialog_input(this, "~\\(≧▽≦)/~啦啦啦", "QQ", new CommonTools.OnInput() {

            @Override
            public void onInput(String text) {
                if ("".equals(text)) {
                    return;
                }
                Tools.QQLaquanquan(text, la_quan_quan_OnCallBack());

            }

            private Tools.LaquanquanCallBack la_quan_quan_OnCallBack() {
                return new Tools.LaquanquanCallBack() {

                    @Override
                    public void finished(final boolean laquanquan_finished) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (laquanquan_finished) {

                                    CommonTools.Toast_short(Mainjava.this, "完成啦!");
                                } else {
                                    CommonTools.Toast_long(Mainjava.this, "失败了,请找开发者!");
                                }
                            }
                        });

                    }
                };
            }
        });

    }

    public void WiFi() {
        List<WifiInfo> wiFiKeyMap = Tools.GetWiFiKey(this);
        if (wiFiKeyMap == null) {
            Toast.makeText(this, "读取错误或没有密码!~", Toast.LENGTH_SHORT).show();
            return;
        }
        Iterator<WifiInfo> iterator_From_Map = wiFiKeyMap.iterator();
        String toast_results = new String();
        while (iterator_From_Map.hasNext()) {
            WifiInfo oneKey = iterator_From_Map.next();
            String ssid = oneKey.Ssid;
            String pass = oneKey.Password;
            toast_results += ssid + "---" + pass + "\n";
        }
        if (wiFiKeyMap.size() < 5) {
            Toast.makeText(this, toast_results, Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, ViewJava.class);
            i.putExtra("view", toast_results);
            this.startActivity(i);
        }
    }

    public void ShouJiLeYuanSouSuo() {
        CommonTools.Dialog_input(this, "快搜", "我的搜索不知道高到哪里去!", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                Tools.Start_shouji_so(text, new Tools.FindAPPCallBack() {

                    @Override
                    public void finished(Vector<AppNode> results) {
                        if (results == null || results.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonTools.Toast_short(Mainjava.this, "毛都没搜到!~~~");
                                }
                            });
                            return;
                        }
                        final Iterator<AppNode> iter = results.iterator();
                        // 开始构建列表
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                ArrayList<SparseArray<Object>> itemlist = new ArrayList<>();

                                while (iter.hasNext()) {

                                    AppNode value = iter.next();
                                    SparseArray<Object> hash = new SparseArray<>();
                                    hash.put(0, value.getApp_name());
                                    hash.put(1, value.getApp_image());
                                    hash.put(2, value.getAppIntroduction());
                                    hash.put(3, value.getApp_sites());
                                    hash.put(4, value.getApp_size());
                                    itemlist.add(hash);
                                }
                                Intent i = new Intent(Mainjava.this, ListJava.class);
                                i.putExtra("put", itemlist);
                                i.putExtra("name", text);
                                i.putExtra("type", "shouji");
                                startActivity(i);

                            }
                        });


                    }
                });
            }
        });

    }

    public void CoolapkSouSuo() {
        CommonTools.Dialog_input(this, "快搜", "祝V7早日出!", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                Tools.Start_coolapk_so(text, new Tools.FindAPPCallBack() {

                    @Override
                    public void finished(Vector<AppNode> results) {
                        if (results == null || results.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonTools.Toast_short(Mainjava.this, "毛都没搜到!~~~");
                                }
                            });
                            return;
                        }
                        final Iterator<AppNode> iter = results.iterator();
                        // 开始构建列表
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                ArrayList<SparseArray<Object>> itemlist = new ArrayList<>();

                                while (iter.hasNext()) {

                                    AppNode value = iter.next();
                                    if ("".equals(value.getApp_name().trim())) {
                                        continue;
                                    }
                                    SparseArray<Object> hash = new SparseArray<Object>();
                                    hash.put(0, value.getApp_name());
                                    hash.put(1, value.getApp_image());
                                    hash.put(2, value.getAppIntroduction());
                                    hash.put(3, value.getApp_sites());
                                    hash.put(4, value.getApp_size());
                                    itemlist.add(hash);
                                }
                                Intent i = new Intent(Mainjava.this, ListJava.class);
                                i.putExtra("put", itemlist);
                                i.putExtra("name", text);
                                i.putExtra("type", "coolapk");
                                startActivity(i);

                            }
                        });


                    }
                });
            }
        });

    }

    private void BaiduSousuo() {
        CommonTools.Dialog_input(this, "快搜", "解析网页大法好!#(滑稽)", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                Tools.Start_baidu_so(text.trim() + "&currentPage=1", new Tools.FindAPPCallBack() {

                    @Override
                    public void finished(Vector<AppNode> results) {
                        if (results == null || results.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonTools.Toast_short(Mainjava.this, "毛都没搜到!~~~");
                                }
                            });
                            return;
                        }
                        Iterator<AppNode> iter = results.iterator();
                        // 开始构建列表
                        final ArrayList<SparseArray<Object>> itemlist = new ArrayList<>();
                        while (iter.hasNext()) {

                            AppNode value = iter.next();
                            if ("".equals(value.getApp_name().trim())) {
                                continue;
                            }
                            SparseArray<Object> hash = new SparseArray<>();
                            hash.put(0, value.getApp_name());
                            hash.put(1, value.getApp_image());
                            hash.put(2, value.getAppIntroduction());
                            hash.put(3, value.getApp_sites());
                            hash.put(4, value.getApp_size());
                            itemlist.add(hash);
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Intent i = new Intent(Mainjava.this, ListJava.class);
                                i.putExtra("put", itemlist);
                                i.putExtra("name", text);
                                i.putExtra("type", "baidu");
                                startActivity(i);

                            }
                        });

                    }
                });

            }
        });
    }

    private void ZhongziSousuo() {
        CommonTools.Dialog_input(this, "快搜", "解析网页大法好!", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                Tools.Start_zhongzi_so(text.trim(), 1, "list", new Tools.FindAPPCallBack() {

                    @Override
                    public void finished(Vector<AppNode> results) {
                        if (results == null || results.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonTools.Toast_short(Mainjava.this, "毛都没搜到!~~~");
                                }
                            });
                            return;
                        }
                        Iterator<AppNode> iter = results.iterator();
                        // 开始构建列表
                        final ArrayList<SparseArray<Object>> itemlist = new ArrayList<>();
                        while (iter.hasNext()) {

                            AppNode value = iter.next();
                            if ("".equals(value.getApp_name().trim())) {
                                continue;
                            }
                            SparseArray<Object> hash = new SparseArray<>();
                            hash.put(0, value.getApp_name());
                            hash.put(1, value.getApp_image());
                            hash.put(2, value.getAppIntroduction());
                            hash.put(3, value.getApp_sites());
                            hash.put(4, value.getApp_size());
                            itemlist.add(hash);
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Intent i = new Intent(Mainjava.this, ListJava.class);
                                i.putExtra("put", itemlist);
                                i.putExtra("name", text);
                                i.putExtra("type", "zhongzi");
                                startActivity(i);

                            }
                        });

                    }
                });

            }
        });
    }

    public void MeiTu() {
        CommonTools.Dialog_input(this, "输完了", "请输入密码", new CommonTools.OnInput() {


            @Override
            public void onInput(String text) {
                if ("fuck".equals(text.trim().toLowerCase(Locale.ENGLISH))) {
                    Parameters p = new Parameters();
                    p.put("num", "1");
                    ApiStoreSDK.execute(Mainjava.this, "http://apis.baidu.com/txapi/mvtp/meinv", p,
                            new ApiCallBack() {
                                @Override
                                public void onSuccess(String arg1) {
                                    arg1 = CommonTools.decode_Unicode(arg1);
                                    System.out.println(arg1);
                                    try {
                                        JSONObject jo = new JSONObject(arg1);
                                        final String picurl =
                                                jo.getJSONArray("newslist").getJSONObject(0).getString("picUrl");
                                        new Thread(new Runnable() {

                                            @Override
                                            public void run() {
                                                try {
                                                    final Bitmap meinv = ImageService.getImageBitmap(picurl);
                                                    if (meinv != null) {
                                                        runOnUiThread(new Runnable() {


                                                            @Override
                                                            public void run() {
                                                                Toast toast = new Toast(Mainjava.this);
                                                                View v =
                                                                        LayoutInflater.from(Mainjava.this).inflate(
                                                                                layout.hahaha_toast, null);
                                                                ImageView iv = (ImageView) v.findViewById(id.imageView_hahaha);
                                                                iv.setImageBitmap(meinv);
                                                                toast.setDuration(Toast.LENGTH_LONG);
                                                                toast.setView(v);
                                                                toast.show();
                                                            }
                                                        });
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError() {
                                    CommonTools.Toast_long(Mainjava.this, "网络异常!");

                                }

                            });
                } else {
                    CommonTools.Toast_long(Mainjava.this, "密码错误!");
                }
            }
        });
    }

    private void FC() {
        CommonTools.Dialog_input(this, "开始作死", "想让什么停止运行?", new CommonTools.OnInput() {

            @Override
            public void onInput(String text) {

                CommonTools.Dialog_Android_6_0(Mainjava.this, "确定", "\n 很抱歉, “" + text + "”已停止运行。");

            }
        });
    }

    private void BooruPic() {
        if (!CommonTools.Network_isAvailable(this)) {
            CommonTools.Toast_short(this, "无网络!");
            return;
        }
        Tools.Booru(this);
    }

//  private void wifi() {
//    if (!CommonTools.Network_WIFIAvailable(this)) {
//      Intent i = new Intent(this, DialogJava.class);
//      i.putExtra("hint", "您还没有打开WiFi开关。");
//      i.putExtra("button", "转到网络设置");
//      i.putExtra("TurntoNetworkSettings", true);
//
//      startActivity(i);
//      return;
//    }
//    startActivity(new Intent(Mainjava.this, WiFiJava.class));
//
//
//  }

    // private void emokit()
    // {
    // AlertDialog.Builder adb=new Builder(this).setItems(new String[]{"语音情绪分析","脸部动态分析","脸部表情分析"},
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // switch (which) {
    // case 0:
    //
    // Tools.VoiceRecognize(Mainjava.this);
    // break;
    // case 1:
    // Tools.FacesRecognize(Mainjava.this);
    // break;
    // case 2:
    // Tools.FaceRecognize(Mainjava.this);
    // break;
    // default:
    // break;
    // }
    //
    // }
    // });
    // adb.show();
    // }
    private void Shutdown() {
        Tools.ShowShutdownDialog(this);
    }

//  private void NoGPS() {
//    Intent i = new Intent(this, DialogJava.class);
//    i.putExtra("hint", "由于数据库有问题，该功能暂时不予开放。");
//    i.putExtra("button", "请期待");
//    startActivity(i);
//    return;
//    // startActivity(new Intent(Mainjava.this, NoGPSJava.class));
//  }

    private void Clean_Gan_tan_hao() {
        if (VERSION.SDK_INT < 20) {
            CommonTools.Dialog_Android_6_0(this, "说的也是", "您的系统版本号是" + VERSION.SDK_INT
                    + ",看不出来有用的必要....");
            return;
        }
        Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("确定您有ROOT权限!");
        adb.setMessage("按确定后手机将重启!");
        adb.setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    CommonTools.Root_Run_command(new String[]{"settings", "put", "global",
                            "captive_portal_detection_enabled", "0"}, Mainjava.this);
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot now"});
                } catch (Exception e) {
                    CommonTools.Toast_long(Mainjava.this, "错误，可能是您没有ROOT.\n" + e);

                    e.printStackTrace();
                }

            }
        });
        adb.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
    }

    private void SetSELinux() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("选择状态");
        adb.setItems(new String[]{"On", "Off"}, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    if (which == 0) {
                        CommonTools.Root_Run_command(new String[]{"setenforce", "1"}, Mainjava.this);
                    }
                    if (which == 1) {
                        CommonTools.Root_Run_command(new String[]{"setenforce", "0"}, Mainjava.this);
                    }
                } catch (Exception e) {
                    CommonTools.Toast_long(Mainjava.this, "错误，可能是您没有ROOT.\n" + e);
                    e.printStackTrace();
                }
            }
        });
        adb.show();
    }

    private void TreasureTools() {
        this.startActivity(new Intent(this, FenbeiJava.class));
    }


    private void QQMusic() {
        CommonTools.Dialog_input(this, "输好了", "输入QQ号", new CommonTools.OnInput() {

            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String baseurl = "http://api.qq-admin.cn/?mod=QQMusicJs&qq=";
                        String res = CommonTools.Network_SendGet(baseurl + text);
                        try {
                            JSONObject jo = new JSONObject(res);
                            int code = jo.getInt("code");
                            if (code == 1) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        CommonTools.Toast_long(Mainjava.this, "成功!等一会去看看吧");

                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        CommonTools.Toast_long(Mainjava.this, "错误!可能是接口已挂!或者是QQ号格式错误!");

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    CommonTools.Toast_long(Mainjava.this, "错误!可能是接口已挂!");

                                }
                            });
                        }
                    }
                }).start();

            }
        });


    }

    private void BOOM() {
        Random r = new Random();
        int a = r.nextInt(10);
        int b = a - r.nextInt(10);
        int n = r.nextInt(5);
        int k = r.nextInt(10);
        final String result = (Math.pow(a, n + 1) + k * a - Math.pow(b, n + 1) - k * b) + "";

        LayoutInflater iInflater = LayoutInflater.from(this);
        ViewGroup vv = (ViewGroup) iInflater.inflate(R.layout.input, null);
        final EditText v = (EditText) vv.findViewById(id.editText);
        v.setHint("结果保留整数");
        Builder ab = new Builder(this);
        ab.setTitle("对于f(x)=x^" + n + "*" + (n + 1) + "+" + k + ",在" + b + "到" + a + "间求积分").setView(vv).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ((v.getText().toString().trim() + ".0").equals(result)) {
                    startActivity(new Intent(Mainjava.this, HongzhaJava.class));
                } else {
                    Toast.makeText(Mainjava.this, "错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ab.show();
    }

    private void Calc_SuShu() {
        CommonTools.Dialog_input(this, "开始搞机", "请输入正整数!", new CommonTools.OnInput() {

            @Override
            public void onInput(String text) {
                text = text.trim();
                try {
                    long num = Long.parseLong(text);
                    if (num <= 0) {
                        throw new NumberFormatException();
                    }
                    Mainjava.SuShuCalcTask ssct = new Mainjava.SuShuCalcTask();
                    ssct.execute(num);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    CommonTools.Toast_long(Mainjava.this, "搞什么啊!你输入的是正整数?");
                }

            }
        });
    }

    private void BiliBiliAV() {
        CommonTools.Dialog_input(this, "输好了", "输入AV号", new CommonTools.OnInput() {
            @Override
            public void onInput(String text) {
                text = text.trim().toLowerCase().replace("av", "");
                if ("".equals(text)) {
                    return;
                }
                final String finalText = text;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String json = CommonTools.Network_SendGet("https://www.biliplus.com/api/view?id=" + finalText);
                        try {
                            JSONObject jo = new JSONObject(json);
                            final String pic;
                            final String title = jo.optString("title", "");
                            if ((pic = jo.optString("pic")) != null) {
                                try {
                                    final Bitmap b = ImageService.getImageBitmap(pic);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final Builder adb = new Builder(Mainjava.this);
                                            adb.setTitle("保存吼不吼啊?")
                                                    .setPositiveButton("当然吼啊", new OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            final String file = Environment
                                                                    .getExternalStorageDirectory().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png";
                                                            new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    CommonTools.File_WritetoSDFrom_bytes(CommonTools.Image_Bitmap2Bytes(b), file);
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(Mainjava.this, "已保存到:" + file, Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });
                                                                }
                                                            }).start();
                                                        }
                                                    })
                                                    .setNegativeButton("无可奉告", null);
                                            new Builder(Mainjava.this)
                                                    .setTitle("获取完成:\n" + title)
                                                    .setMessage("是否要一睹为快?")
                                                    .setPositiveButton("是", new OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast t = new Toast(Mainjava.this);
                                                                    View v = LayoutInflater.from(Mainjava.this).inflate(layout.hahaha_toast, null);
                                                                    ImageView iv = (ImageView) v.findViewById(id.imageView_hahaha);
                                                                    iv.setImageBitmap(b);
                                                                    t.setDuration(Toast.LENGTH_LONG);
                                                                    t.setView(v);
                                                                    CommonTools.Toast_setduration(t, 5000);
                                                                }
                                                            });
                                                            adb.show();
                                                        }
                                                    })
                                                    .setNegativeButton("否", new OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            adb.show();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(Mainjava.this, "错误啦!请检查输入的AV号是否正确!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
    }

    private void FakeSMS() {
        CommonTools.Dialog_input(this, "下一步", "输入来信人的号码!", new CommonTools.OnInput() {
            @Override
            public void onInput(final String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                CommonTools.Dialog_input(Mainjava.this, "完成", "输入来信内容!", new CommonTools.OnInput() {
                    @Override
                    public void onInput(final String text1) {
                        if ("".equals(text1.trim())) {
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Uri u = Uri.parse("content://sms");
                                ContentValues cv = new ContentValues();
                                cv.put("address", text);
                                cv.put("body", text1);
                                getContentResolver().insert(u, cv);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Mainjava.this, "搞定啦，去看看吧!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();

                    }
                });
            }
        });
    }

    private void MD5() {
        CommonTools.Dialog_input(this, "加密", "1246a35666226006", new CommonTools.OnInput() {
            @Override
            public void onInput(String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                String pass = MD5.stringMD5(text);
                ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(pass);
                Toast.makeText(Mainjava.this, "加密前:" + text + "\n加密后:" + pass + "\n已复制到剪切板", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void light() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("选择状态");
        adb.setItems(new String[]{"on", "off"}, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                try {
                    if (i == 0) {
                        camera = Camera.open();
                        Camera.Parameters params = camera.getParameters();
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(params);
                        camera.startPreview(); // 开始亮灯
                    }
                    if (i == 1) {
                        camera.stopPreview(); // 关掉亮灯
                        camera.release(); // 关掉照相机
                    }
                } catch (Exception e) {

                }
            }
        }).show();
    }

    private void Shorturl() {
        CommonTools.Dialog_input(this, "缩短", "http://coolapk.com/", new CommonTools.OnInput() {
            @Override
            public void onInput(String text) {
                if ("".equals(text.trim())) {
                    return;
                }
                try {
                    CommonTools.Network_SendGet_ASYNC(Mainjava.this, "http://980.so/api.php?url=" + URLEncoder.encode(text, "utf-8"), new CommonTools.OnGet() {
                        @Override
                        public void onGet(String text) {
                            if (text != null) {
                                ClipboardManager c = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                c.setText(text);
                                Toast.makeText(Mainjava.this, "缩短后:" + text + "\n已复制到剪切版", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Mainjava.this, "出错!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private OnItemClickListener getlistener() {

        return new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String tv = ((TextView) view.findViewById(id.textView_title)).getText().toString();
                if ("WiFi密码查看".equals(tv)) {
                    WiFi();
                }
                if ("Todo List".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, TodoListJava.class));
                }
                if ("手电筒".equals(tv)) {
                    light();
                }
                if ("快速翻译".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, TranslateJava.class));
                }
                if ("网易云音乐启动背景替换".equals(tv)) {
                    Tools.Modi_WangYi_Mp3_UI_beginning(Mainjava.this);
                }
                if ("关机重启".equals(tv)) {
                    Shutdown();
                }
                // --------------QQ----------------
                if ("QQ空间自定义打赏".equals(tv)) {
                    QQDaShang();
                }
                if ("QQ拉圈圈".equals(tv)) {
                    La_quan_quan();
                }
                if ("QQ音乐加速".equals(tv)) {
                    QQMusic();
                }
                if ("QQ撤回消息自定义".equals(tv)) {
                    QQCheHuiXiaoXi();
                }
                if ("QQ聊天字数突破".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, QQZiShuJava.class));
                }
                // --------------QQ--END---------
                if ("手机乐园搜索".equals(tv)) {
                    ShouJiLeYuanSouSuo();
                }
                if ("酷安应用搜索".equals(tv)) {
                    CoolapkSouSuo();
                }
                if ("百度网盘搜索".equals(tv)) {
                    BaiduSousuo();
                }
                if ("种子搜索".equals(tv)) {
                    ZhongziSousuo();
                }
                if ("天气预报".equals(tv)) {
                    Tools.TianQi(Mainjava.this);
                }
                if ("simsimi".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, TalkJava.class));
                }
                if ("压力测试".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, PaoFenJava.class));
                }
                if ("虚拟短信".equals(tv)) {
                    FakeSMS();
                }
                if ("停止运行".equals(tv)) {
                    FC();
                }
                if ("生成二维码".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, QRcodeJava.class));
                }
                if ("生成短网址".equals(tv)) {
                    Shorturl();
                }
                if ("一键锁屏".equals(tv)) {
                    CommonTools.System_Install_New_ShortCut(Mainjava.this, "一键锁屏", drawable.lock,
                            LockJava.class);
                }
                if ("Booru壹图".equals(tv)) {
                    BooruPic();
                }
                if ("轰炸机".equals(tv)) {
                    BOOM();
                }
                if ("测量小工具".equals(tv)) {
                    TreasureTools();
                }
                if ("MD5加密".equals(tv)) {
                    MD5();
                }
                if ("蛤蛤蛤".equals(tv)) {
                    MeiTu();
                }
                if ("素数判断".equals(tv)) {
                    Calc_SuShu();
                }
                if ("获取Bilibili封面".equals(tv)) {
                    BiliBiliAV();
                }
                if ("烧机".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, ShaojiJava.class));
                }

                if ("设置".equals(tv)) {
                    startActivity(new Intent(Mainjava.this, SettingsJava.class));
                }
                // -------------------SYSTEM------------
                if ("去除通知栏感叹号".equals(tv)) {
                    Clean_Gan_tan_hao();
                }
                if ("SELinux开关".equals(tv)) {
                    SetSELinux();
                }
                // -------------------DEVELOPER------------
//                if(tv.equals("Run Code"))
//                {
//                    startActivity(new Intent(Mainjava.this, CodeRunnerJava.class));
//                }

            }


        };
    }

    private List<Map<Integer, Object>> gettabdata(int tab) {
        switch (tab) {
            case id.menu_main:
                return GetListData.getData();
            case id.menu_qq:
                return GetListData.getQQData();
            case id.menu_system:
                return GetListData.getSystemData();
//            case id.menu_develop:
//                return GetListData.getDevelopData();
            case id.menu_happy:
                return GetListData.getHappyData();
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if ("推·乐(Beta)".equals(item.getTitle().toString())) {
            startActivity(new Intent(this, MusicJava.class));
            return true;
        }
        final ListView lv = (ListView) findViewById(id.main_listView);
        //final LinearLayout ll= (LinearLayout) findViewById(id.main_ll);
//        Rotate3dAnimation ra = new Rotate3dAnimation(lv.getWidth() / 2, lv.getHeight() / 2, 100);
//        ra.setAl(new Rotate3dAnimation.AnimationListener() {
//            @Override
//            public void OnChangeView() {
//
//            }
//        });
//        lv.startAnimation(ra);
        lv.setAdapter(new Main_Adapter(Mainjava.this, gettabdata(item.getItemId())));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobE.initBmob("68e6a3ef6a74ccf587042a44670ecd86", "12439151b099471918bc349a9e57d649");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences sp = this.getSharedPreferences("fanyi", Context.MODE_PRIVATE);
        boolean IsErJiOpening = sp.getBoolean("erji", false);

        this.setContentView(layout.main_activity);
        // 注册耳机监听器
        if (IsErJiOpening) {
            this.hr = new HeadsetReceiver();
            MyApplication.getContextObject().registerReceiver(hr,
                    new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        }
        ListView lv = (ListView) findViewById(id.main_listView);
        lv.setAdapter(new Main_Adapter(this, gettabdata(id.menu_main)));
        lv.setOnItemClickListener(getlistener());
        // TODO 实验性功能!!!!!!!!!!!!
        // findViewById(R.id.textView1).setOnLongClickListener(new OnLongClickListener() {
        //
        // @Override
        // public boolean onLongClick(View v) {
        // findViewById(R.id.button3).setVisibility(View.VISIBLE);
        // findViewById(R.id.webView1).setVisibility(View.VISIBLE);
        // findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("*/*");
        // CommonTools.Toast_short(Mainjava.this, "请选择JavaScipt文件!");
        // startActivityForResult(intent, 0x128);
        //
        // }
        // });
        //
        // return false;
        // }
        // });
        // TODO 实验性功能!!!!!!!!!!!!
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO 实验性功能!!!!!!!!!!!!
//    if (requestCode == 0x128 && resultCode == RESULT_OK) {
//
//      InputStream reader = Tools.getisfromintent(data, this);
//      byte[] buffer = new byte[] {};
//      try {
//        buffer = new byte[reader.available()];
//        reader.read(buffer);
//        String js_text = new String(buffer, "UTF-8");
//        StringBuffer html_buffer = new StringBuffer();
//        html_buffer.append("<html><head></head><body><script type=\"text/javascript\">");
//        html_buffer.append(js_text);
//        html_buffer.append("</script></body></html>");
//        OutputStream writer =
//            new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/a.html");
//        writer.write(html_buffer.toString().getBytes("UTF-8"));
//        writer.close();
//        WebView wv = (WebView) findViewById(R.id.webView1);
//        wv.getSettings().setJavaScriptEnabled(true);
//        wv.loadUrl("file://" + Environment.getExternalStorageDirectory().getPath() + "/a.html");
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//
//
//    }
        // TODO 实验性功能!!!!!!!!!!!!
        Tools.modiWangyiMp3UiDoing(this, requestCode, resultCode, data);
    }

    public class SuShuCalcTask extends AsyncTask<Long, Integer, Boolean> {
        ProgressDialog pd;
        long num;


        @Override
        protected void onPostExecute(Boolean result) {


            super.onPostExecute(result);
            this.pd.dismiss();
            if (result) {
                CommonTools.Dialog_Android_6_0(Mainjava.this, "好", this.num + "是质数");
            } else {
                CommonTools.Dialog_Android_6_0(Mainjava.this, "好", this.num + "不是质数");
            }
        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            this.pd = new ProgressDialog(Mainjava.this);
            this.pd.setCancelable(true);
            this.pd.setIndeterminate(false);
            this.pd.setTitle("计算中,按返回停止");
            this.pd.setMessage("算算算...");
            this.pd.setMax(100);


            this.pd.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                    cancel(true);
                }
            });
            this.pd.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            this.pd.setProgress(values[0]);
        }

        @Override
        protected Boolean doInBackground(Long... arg0) {
            long a = arg0[0];
            long half = a / 2;
            this.num = a;
            for (long i = 2; i <= half; i++) {
                if (a % i == 0) {
                    return false;
                }
                this.publishProgress((int) (i * 100 / half));
            }

            return true;
        }

    }


}
