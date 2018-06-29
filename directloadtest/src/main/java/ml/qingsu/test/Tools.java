package ml.qingsu.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericsoft.bmob.bson.BSONObject;
import com.ericsoft.bmob.restapi.BmobE;
import com.example.directloadtest.R;
import com.example.directloadtest.R.drawable;
import com.example.directloadtest.R.id;
import com.example.wifipassword.WifiInfo;
import com.example.wifipassword.WifiManage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import ml.qingsu.wifi.CommonTools;


public class Tools {
    public static final int REQUESTCODE = 0x123;

    public static void DelLog(Activity a) throws IOException, InterruptedException {
        String res =
                CommonTools.Root_Run_command("rm -rf /data/data/de.robv.android.xposed.installer/log/", a);
        System.out.println(res);
        res =
                CommonTools.Root_Run_command("echo 1> /data/data/de.robv.android.xposed.installer/log", a);
        System.out.println(res);
    }

    public static List<WifiInfo> GetWiFiKey(Context con) {
        try {
            return WifiManage.Read();
        } catch (Exception e) {
            CommonTools.Toast_long(con, "读取失败!您root了吗?");
            e.printStackTrace();
        }
        return null;
    }

    // private static String ExpersiontoString(String result)
    // {
    // String[] org=
    // {"K",
    // "D",
    // "C",
    // "Y",
    // "M",
    // "W",
    // "T"
    // };
    // String[] covrect=
    // {
    // "平静；放松；专注；出神；",
    // "忧愁；疑惑；迷茫；无助；",
    // "伤感；郁闷；痛心；压抑；",
    // "生气；蔑视；兴奋；失控；",
    // "开心；甜蜜；欢快；舒畅；",
    // "害怕；焦虑；紧张；激情；",
    // "厌恶；反感；意外；惊讶；"
    // };
    // for(int i=0;i<org.length;i++)
    // {
    // if(result.equals(org[i]))
    // return covrect[i];
    // }
    // return "";
    // }
    // private static String ResulttoString(String Result)
    // {
    // String[] org=
    // {"KA"
    // ,"KP"
    // ,"KR"
    // ,"CT"
    // ,"CG"
    // ,"YC"
    // ,"YL"
    // ,"YV"
    // ,"ML"
    // ,"MS"
    // ,"WS"
    // ,"WC"};
    // String[] covrect=
    // {"接纳；包容；吸收；柔顺；",
    // "专注；平静；出神；孤单；",
    // "痛快；爽快；释放；放松；",
    // "豪放；从容；开朗；豁达；",
    // "决断；果敢；坚定；爽快；",
    // "平和；美好；理智；祥和；",
    // "舒适；轻松；自然；顺心；",
    // "欢快；欢畅；舒畅；舒心；",
    // "怜爱；同情；关心；甜蜜；",
    // "喜欢；开心；高兴；心动；",
    // "积极；阳光；高涨；激情；",
    // "无畏；泰然；面对；激动；",
    // "急躁；着急；憋躁；憋闷；",
    // "心乱；分心；空想；思念；",
    // "暴躁；烦躁；憋火；悸动；",
    // "伤感；哭泣；痛心；低落；",
    // "怯懦；犹豫；纠结；郁闷;",
    // "生气；指责；攻击；冲动；",
    // "紧张；失调；失控；宣泄； ",
    // "压抑；窝心；别扭；想念； ",
    // "哀伤；失落；幽怨；寂寞；",
    // "记恨；怨恨；仇恨；哀怨； ",
    // "消极；灰暗；低迷；颓废；",
    // "恐惧；害怕；惊恐；焦虑；",
    // };
    // String poss=Result.substring(2,3);
    // String type=Result.substring(0,2);
    // for (int i=0;i<org.length;i++)
    // {
    // if(type.equals(org[i]))
    // {
    // if(poss.equals("+"))
    // {
    // return covrect[i];
    // }
    // else if(poss.equals("-"))
    // {
    // return covrect[i+12];
    // }
    // }
    // }
    // return "";
    // }
    // private static void DoWithResults(final Activity con,String reult,boolean isexpersion)
    // {
    // if(reult==null)
    // {
    // CommonTools.Toast_long(con, "识别失败!无返回结果");
    // return;
    // }
    // String[] results=reult.split("&&");
    // String toasttext=new String();
    // for (String result : results)
    // {
    // try {
    // JSONObject js=new JSONObject(result);
    // String resultcode=js.getString("resultcode");
    // String rc_main=js.getString("rc_main");
    // String rc_main_value=js.getString("rc_main_value");
    // if(!resultcode.equals("200"))
    // {
    // CommonTools.Toast_long(con, "识别错误！错误代码:"+resultcode);
    // return;
    // }
    // if(isexpersion)
    // toasttext+=(ExpersiontoString(rc_main)+":"+rc_main_value+"\n");
    // else
    // toasttext+=(ResulttoString(rc_main)+":"+rc_main_value+"\n");
    //
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // }
    // final String finaltext=toasttext;
    // con.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // CommonTools.Toast_long(con, finaltext);
    // }
    // });
    // }
    // public static void VoiceRecognize(final Activity con)
    // {
    // EmotionVoiceListener evl=new EmotionVoiceListener() {
    //
    // @Override
    // public void onVoiceResult(String arg0) {
    //
    // DoWithResults(con, arg0,false);
    // }
    //
    // @Override
    // public void onEndOfSpeech() {
    // }
    //
    // @Override
    // public void onBeginOfSpeech() {
    // }
    //
    // @Override
    // public void onVolumeChanged(int arg0) {
    //
    // }
    // };
    // EmotionDetect ed=EmotionDetect.createRecognizer(con,new InitListener() {
    //
    // @Override
    // public void onInit(int arg0) {
    // TelephonyManager tm = (TelephonyManager)con.getSystemService(Context.TELEPHONY_SERVICE);
    // String DEVICE_ID = tm.getDeviceId();
    // SDKAppInit.registerforuid("DIRECT-TOOL", DEVICE_ID, "");
    //
    // }
    // });
    // ed.startListening(evl);
    // }
    // public static void FaceRecognize(final Activity con)
    // {
    // ExpressionListener el=new ExpressionListener() {
    //
    // @Override
    // public void endDetect(String arg0, String arg1) {
    // DoWithResults(con, arg0, true);
    // }
    // @Override
    // public void beginDetect() {
    //
    //
    // }
    // };
    //
    // ExpressionDetect ed=ExpressionDetect.createRecognizer(con, new InitListener() {
    //
    // @Override
    // public void onInit(int arg0) {
    // TelephonyManager tm = (TelephonyManager)con.getSystemService(Context.TELEPHONY_SERVICE);
    // String DEVICE_ID = tm.getDeviceId();
    // SDKAppInit.registerforuid("DIRECT-TOOL", DEVICE_ID, "");
    // }
    // });
    // ed.setParameter(SDKConstant.FACING, SDKConstant.CAMERA_FACING_FRONT);
    // ed.startRateListening(el);
    // }
    // public static void FacesRecognize(final Activity con)
    // {
    // EmoRateListener erl=new EmoRateListener() {
    //
    // @Override
    // public void onHeartRateValue(int arg0) {
    //
    // }
    //
    // @Override
    // public void monitor(double arg0) {
    //
    // }
    //
    // @Override
    // public void endDetect(String arg0) {
    // DoWithResults(con, arg0,false);
    // }
    //
    // @Override
    // public void beginDetect() {
    //
    // }
    // };
    // RateDetect rd=RateDetect.createRecognizer(con, new InitListener() {
    //
    // @Override
    // public void onInit(int arg0) {
    // TelephonyManager tm = (TelephonyManager)con.getSystemService(Context.TELEPHONY_SERVICE);
    // String DEVICE_ID = tm.getDeviceId();
    // SDKAppInit.registerforuid("DIRECT-TOOL", DEVICE_ID, "");
    //
    // }
    // });
    // rd.setParameter(SDKConstant.FACING, SDKConstant.CAMERA_FACING_FRONT);
    // rd.startRateListening(erl);
    // }
    public static void Booru(final Activity con) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String url = "https://konachan.net/post?page=1&tags=order%3Arandom";
                String res = CommonTools.Network_SendGet(url);
                if (res == null || "Fail to convert net stream!".equals(res) || res.isEmpty()) {
                    con.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            CommonTools.Toast_long(con, "读取失败!");

                        }
                    });
                    return;
                }
                String doing =
                        Tools.Between(res, "<ul id=\"post-list-posts\">", "</span></a></li>    </ul>").trim();
                doing = Tools.Between(doing, "<li", "</span></a></li>").trim();
                String smallpic = Tools.Between(doing, "<img src=\"", "\"").trim();
                if (smallpic == null || "".equals(smallpic)) {
                    con.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            CommonTools.Toast_long(con, "解析错误，可能是网站改版了!快去敲开发者!");

                        }
                    });
                    return;

                }
                try {
                    final Bitmap bit = ImageService.getImageBitmap(smallpic);
                    CommonTools.File_WritetoSDFrom_bytes(CommonTools.Image_Bitmap2Bytes(bit), Environment
                            .getExternalStorageDirectory().getAbsolutePath() + "/fuck.png");

                    con.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast t = new Toast(con);
                            View v = LayoutInflater.from(con).inflate(R.layout.hahaha_toast, null);
                            ImageView iv = (ImageView) v.findViewById(id.imageView_hahaha);
                            iv.setImageBitmap(bit);
                            t.setDuration(Toast.LENGTH_LONG);
                            t.setView(v);
                            CommonTools.Toast_setduration(t, 5000);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    // public static void Pixiv(final Activity con) {
    // new Thread(new Runnable() {
    //
    // @Override
    // public void run() {
    // final String url="http://lelouchcrgallery.tk/rand";
    // String res=CommonTools.Network_SendGet(url);
    // if(res==null||res.equals("Fail to convert net stream!")||res.isEmpty())
    // {
    // con.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // CommonTools.Toast_long(con, "读取失败!");
    //
    // }
    // });
    // return;
    // }
    // String imageurl=CommonTools.String_Between(res, "\"p_thumbnail\":\"", "\",\"p_mid\"");
    // imageurl=imageurl.replace("\\","");
    // if(!CommonTools.Network_isAvailable(con))
    // {
    //
    //
    // }
    // if(CommonTools.Network_WIFIConnected(con))
    // {
    // imageurl=imageurl.replaceAll( "thumbnail", "large");
    // }
    // else
    // {
    // imageurl=imageurl.replaceAll( "thumbnail", "small");
    // }
    // try {
    // final Bitmap bit=ImageService.getImageBitmap(imageurl);
    // con.runOnUiThread(new Runnable() {
    //
    // @SuppressLint("InflateParams")
    // @Override
    // public void run() {
    // Toast t=new Toast(con);
    // View v=LayoutInflater.from(con).inflate(R.layout.hahaha_toast,null);
    // ImageView iv = (ImageView)v.findViewById(R.id.imageView_hahaha);
    // iv.setImageBitmap(bit);
    //
    // iv.setLongClickable(true);
    // iv.setOnLongClickListener(new OnLongClickListener() {
    //
    // @Override
    // public boolean onLongClick(View v) {
    // ImageView i_v=(ImageView) v;
    // Drawable image=i_v.getDrawable();
    // Bitmap bit_image=CommonTools.Image_drawableToBitmap(image);
    // byte[] datas=CommonTools.Image_Bitmap2Bytes(bit_image);
    // Random random=new Random();
    // CommonTools.File_WritetoSDFrom_bytes(datas, random.nextInt()+".png");
    // CommonTools.Toast_long(con,"保存成功!");
    // return false;
    // }
    // });
    // t.setDuration(Toast.LENGTH_LONG);
    // t.setView(v);
    // CommonTools.Toast_setduration(t, 5000);
    //
    // }
    // });
    // } catch (Exception e) {
    // con.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // CommonTools.Toast_long(con, "读取失败!");
    //
    // }
    // });
    // e.printStackTrace();
    // return;
    //
    // }
    //
    // }
    // }).start();
    //
    // }
    public static void TianQi(final Activity con) {
        final SharedPreferences sp = con.getSharedPreferences("weather", Context.MODE_PRIVATE);
        String city_name = sp.getString("cityname", "0");
        if ("0".equals(city_name)) {
            new Builder(con)
                    .setTitle("询问")
                    .setMessage("要定位到您当前所在位置吗？")
                    .setPositiveButton("是", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sp.edit().putString("cityname", "ip").apply();
                            con.startActivity(new Intent(con, WeatherJava.class));
                        }
                    })
                    .setNegativeButton("否", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CommonTools.Dialog_input(con, "输完了", "输入您要查看的地区,目前支持中文、拼音、经纬度、IP地址!", new CommonTools.OnInput() {
                                @Override
                                public void onInput(String text) {
                                    if ("".equals(text)) {
                                        return;
                                    }
                                    sp.edit().putString("cityname", text).apply();
                                    con.startActivity(new Intent(con, WeatherJava.class));
                                }
                            });
                        }
                    })
                    .show();

        } else {
            con.startActivity(new Intent(con, WeatherJava.class));
        }
    }

    public static String Between(String str, String leftstr, String rightstr) {
        if (str.contains(leftstr) || str.contains(rightstr)) {
            return "";
        }
        int i = str.indexOf(leftstr) + leftstr.length();
        return str.substring(i, str.indexOf(rightstr, i));
    }

    public static void Modi_WangYi_Mp3_UI_beginning(Activity con) {
        if (Tools.Get_Can_Doing_WangYi_Path() == null) {


            Toast.makeText(con, "很不吼啊,你没安装网易啊!或者是还没有广告啊!", Toast.LENGTH_LONG).show();

            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        try {
            con.startActivityForResult(intent, Tools.REQUESTCODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(con, "很不吼啊,你手机内没有图库软件啊!", Toast.LENGTH_LONG).show();
        }


    }

    public void FUCK(final ImageView iv, final TextView tv, final String date, final String ServiceUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpURLConnection c = (HttpURLConnection) new URL(ServiceUrl + date + ".png").openConnection();
                    if (c.getResponseCode() == 200) {
                        iv.setImageBitmap(BitmapFactory.decodeStream(c.getInputStream()));
                    }
                    c = (HttpURLConnection) new URL(ServiceUrl + date + ".txt").openConnection();
                    if (c.getResponseCode() == 200) {
                        tv.setText(new Scanner(c.getInputStream(), "UTF-8").useDelimiter("\\A").next().toString());
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    private static String Get_Can_Doing_WangYi_Path() {
        String[] partpaths =
                {"/emulated/0", "/extSdCard", "/sdcard0", "/sdcard1", "/sdcard2", "/sdcard3", "/sdcard4",
                        "/emulated/0", "/external_sd", "/extsdcard", "/sdcard", "/sdcard/sdcard",

                };
        File file =
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/netease/adcache/");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        for (String partpath : partpaths) {
            file = new File("/storage" + partpath + "/netease/adcache/");
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            file = new File("/mnt" + partpath + "/netease/adcache/");
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public static void modiWeixinUiBeginning(Activity con) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        try {
            con.startActivityForResult(intent, Tools.REQUESTCODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(con, "很不吼啊,你手机内没有图库软件啊!", Toast.LENGTH_LONG).show();
        }
    }

    public static void modiWangyiMp3UiDoing(Activity con, int requestCode, int resultCode,
                                            Intent in) {
        if (requestCode == Tools.REQUESTCODE && resultCode == Activity.RESULT_OK) {
            Uri uri = in.getData();
            ContentResolver cr = con.getContentResolver();
            try {
                InputStream is = cr.openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                String path = Tools.Get_Can_Doing_WangYi_Path();
                if (path == null) {
                    Toast.makeText(con, "很不吼啊,你没安装网易啊!或者是还没有广告啊!", Toast.LENGTH_LONG).show();
                    Toast.makeText(con, "请将此消息发送给开发者:0x123", Toast.LENGTH_LONG).show();
                    return;
                }
                File[] files = new File(path).listFiles();
                if (files == null) {
                    Toast.makeText(con, "很不吼啊,你没安装网易啊!或者是还没有广告啊!", Toast.LENGTH_LONG).show();
                    Toast.makeText(con, "请将此消息发送给开发者:0x123", Toast.LENGTH_LONG).show();
                    return;
                }
                Vector<String> c = new Vector<String>();
                for (File f : files) {
                    if (f.isDirectory()) {
                        File[] tempFiles = f.listFiles();
                        if (tempFiles == null) {
                            continue;
                        }
                        for (File innerF : tempFiles) {

                            if (innerF.isFile()) {
                                c.add(innerF.getAbsolutePath());
                            }
                        }
                    }
                    if (f.isFile()) {
                        c.add(f.getAbsolutePath());
                    }
                }
                for (String s : c) {
                    OutputStream ops = new FileOutputStream(s);
                    bmp.compress(CompressFormat.JPEG, 100, ops);
                }
                if (bmp != null && !bmp.isRecycled()) {
                    bmp.recycle();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(con, "很吼,完成了!", Toast.LENGTH_SHORT).show();
        }

    }

    public static InputStream getisfromintent(Intent u, Activity con) {
        ContentResolver cr = con.getContentResolver();
        try {
            return cr.openInputStream(u.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void QQLaquanquan(final String QQ, final Tools.LaquanquanCallBack lcb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                lcb.finished(!"".equals(CommonTools.Network_SendGet("http://api.vvvpan.cn/4/index.php?hm=" + QQ)));
            }
        }).start();
    }

    public static void Start_shouji_so(final String so, final Tools.FindAPPCallBack facb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String html =
                        CommonTools.Network_SendGet("http://wap.shouji.com.cn/mobile/search.jsp?q=" + so);
                html = Tools.Between(html, "<ul id=\"search-res\">", "</ul>");
                html = html.replace("></div>", "/></div>");
                facb.finished(Tools.ShouJiParser(html));
            }
        }).start();
    }

    public static void Start_coolapk_so(final String so, final Tools.FindAPPCallBack facb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String html = CommonTools.Network_SendGet("http://www.coolapk.com/apk/search?q=" + so);
                html =
                        Tools.Between(html, "<ul class=\"media-list ex-card-app-list\">",
                                "<div class=\"panel-footer ex-card-footer text-center\">");
                facb.finished(Tools.Coolapkarser(html));

            }
        }).start();
    }

    public static void Start_baidu_so(final String so, final Tools.FindAPPCallBack facb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String html =
                        CommonTools.Network_SendGet("http://www.quzhuanpan.com/source/search.action?q=" + so);

                String[] htmls = html.split("<div class=\"search-classic\" style=\"margin-bottom:0px;\"");
                System.out.println(Arrays.toString(htmls));
                if (htmls.length > 1) {
                    for (int i = 1; i < htmls.length; i++) {

                        htmls[i] =
                                Tools.Between(htmls[i], "<h4 class=\"result4 next-row\">",
                                        "<span>提示：来自").trim();
                    }
                }
                facb.finished(Tools.Baiduparser(htmls));
            }
        }).start();
    }

    public static void Start_zhongzi_so(final String so, final int page, final String order,
                                        final Tools.FindAPPCallBack facb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String html =
                        CommonTools
                                .Network_SendGet("http://www.zhongzidi.com/" + order + "/" + so + "/" + page)
                                .trim();

                html = Tools.Between(html, "<div class=\"panel-body\">",
                        "<ul class=\"pagination\">");
                System.out.println(html);
                facb.finished(Tools.zhongziParesr(html));
            }
        }).start();
    }

    private static Vector<AppNode> ShouJiParser(String html) {
        Vector<AppNode> vec = new Vector<AppNode>();
        // 本来打算用xmlpullparser的，但居然错误不断，只好文本分割大法
        String[] items = html.split("</li>");
        for (String item : items) {
            String app_sites = Tools.Between(item, "<a class=\"dd\" href=\"", "\">");
            app_sites = "http://wap.shouji.com.cn/mobile/" + app_sites;
            String app_icon = Tools.Between(item, "class=\"lazy\" data-original=\"", "\"");
            try {
                System.out.println("app_icon=" + app_icon);
                String app_name = Tools.Between(item, "<p class=\"p1\">", "</p>");
                String app_size = Tools.Between(item, "<p class=\"p2\">", "</p>");
                String app_introduction = Tools.Between(item, "<p class=\"p3\">", "</p>");
                vec.add(new AppNode(app_name, app_icon, app_size, app_introduction, app_sites));
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
        return vec;
    }

    private static Vector<AppNode> Coolapkarser(String html) {
        Vector<AppNode> vec = new Vector<AppNode>();
        // 本来打算用xmlpullparser的，但居然错误不断，只好文本分割大法
        String[] items = html.split("</li>");
        for (String item : items) {
            String app_icon =
                    Tools.Between(item, "<img class=\"media-object img-rounded\" src=\"", "\" alt=\"");

            String app_sites = Tools.Between(item, "<h4 class=\"media-heading\"><a href=\"", "\">");
            app_sites = "http://www.coolapk.com" + app_sites;
            try {
                System.out.println("app_icon=" + app_icon);
                String app_name = Tools.Between(item, "<h4 class=\"media-heading\">", "</a></h4>");
                app_name += "&&&";
                app_name = Tools.Between(app_name, "\">", "&&&");
                System.out.println(app_name);
                String app_size = Tools.Between(item, "<span class=\"hidden-xs\">", "</span>").split("，", 2)[0];
                String app_introduction = Tools.Between(item, "<div class=\"media-intro\">", "</div>");
                vec.add(new AppNode(app_name, app_icon, app_size, app_introduction, app_sites));
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
        return vec;
    }

    public static Vector<AppNode> Baiduparser(String[] htmls) {
        Vector<AppNode> vec = new Vector<AppNode>();
        for (String node : htmls) {
            String app_sites = Tools.Between(node, "class=\"source-title\" href=\"", "\" target=\"_blank\">");
            app_sites = "http://www.quzhuanpan.com" + app_sites;
            String app_name = Tools.Between(node, "<div class=\"result next-row\">", "<div class=\"result next-row\">").trim();
            app_name = Tools.Between(app_name, "<a title=\"<span style='color:red'>", "class=\"source-title2\"").trim();
            app_name = app_name.replace("</span><span style='color:red'>", "");
            app_name = app_name.replace("</span>\"", "");
            String app_size = "转到";
            String app_introduction = "分享者:" + Tools.Between(node, "&shareMan=", "&uid=");
            vec.add(new AppNode(app_name, null, app_size, app_introduction, app_sites));
        }
        return vec;
    }

    private static Vector<AppNode> zhongziParesr(String html) {
        Vector<AppNode> vec = new Vector<AppNode>();
        String[] htmls = html.split("<ul class=\"list-group\">");
        System.out.println("length=" + htmls.length);
        for (String node : htmls) {
            String app_name =
                    Tools.Between(node,
                            "<span class=\"glyphicon glyphicon-thumbs-up\"></span>&nbsp;&nbsp;&nbsp;&nbsp;",
                            "</h4></a></div></td>");
            System.out.println(app_name);
            String app_sites = Tools.Between(app_name, "<a href=\"", "\">");
            app_sites = "http://www.zhongzidi.com" + app_sites;
            String app_size = Tools.Between(node, "<td>大小：<strong>", "</strong></td>");
            String app_introduction = "下载量:" + Tools.Between(node, "<td>热度：<strong>", "</strong></td>");
            vec.add(new AppNode(app_name, null, app_size, app_introduction, app_sites));
        }
        return vec;

    }

    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static boolean isRoot() {
        boolean bool = false;

        try {
            bool = !(!new File("/system/bin/su").exists() && !new File("/system/xbin/su").exists());
        } catch (Exception e) {

        }
        return bool;
    }

    public static void savemusic(MusicNode mn) {

        BSONObject bo2 = new BSONObject();
        bo2.put("name", mn.getName());
        bo2.put("imei", mn.getImei());
        bo2.put("good", mn.getGood());
        bo2.put("des", mn.getDes());
        bo2.put("date", mn.getDate());
        bo2.put("hash", mn.getHash());
        System.out.println("bo2=" + bo2);
        BSONObject bo = BmobE.insert("MusicNode", bo2);
        while (bo == null) {
            bo = BmobE.insert("MusicNode", bo2);
        }
    }

    public static void getmusic(final Activity con, int page, final Tools.FindMusicCallBack f) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final ArrayList<MusicNode> mns = new ArrayList<MusicNode>();
                BSONObject json = null;
                try {
                    while (json == null) {
                        json = BmobE.findAll("MusicNode");
                    }
                } catch (Exception e) {
                    con.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            f.finished(null);

                        }
                    });
                    return;
                }

                BSONObject[] ja = json.getBSONArray("results");
                if (ja == null || ja.length == 0 || ja.length == 1) {
                    con.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            f.finished(null);

                        }
                    });
                    return;
                }
                for (int i = 0; i < ja.length; i++) {
                    BSONObject jo = ja[i];
                    MusicNode mn =
                            new MusicNode(jo.getString("imei"), jo.getInt("good"), jo.getString("des"),
                                    jo.getString("name"), jo.getString("createdAt"), jo.getString("objectId"));
                    mn.setHash(null);
                    mns.add(mn);
                }
                con.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        f.finished(mns);

                    }
                });
            }
        }).start();

    }

    public static void ShowShutdownDialog(final Context con) {
        Builder adb = new AlertDialog.Builder(con);
        adb.setTitle("最小关机重启");
        adb.setItems(new String[]{"关机", "重启", "重启至recovery", "重启至boot loader", "建立桌面快捷方式"},
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        try {
                            if (which == 0) {
                                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot -p"});
                            }
                            if (which == 1) {
                                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot now"});
                            }
                            if (which == 2) {
                                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot recovery"});
                            }
                            if (which == 3) {
                                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot bootloader"});
                            }
                            if (which == 4) {
                                CommonTools.System_Install_New_ShortCut(con, "最小关机重启", drawable.lock,
                                        ShutdownDialogViewJava.class);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            CommonTools.Toast_long(con, "请求失败!没有ROOT权限?");
                        }

                    }
                });
        adb.show();
    }

    public static void developer_run_code(final Activity a) {

    }

    public interface LaquanquanCallBack {
        void finished(boolean ok);
    }

    public interface FindAPPCallBack {
        void finished(Vector<AppNode> results);
    }

    public interface FindMusicCallBack {
        void finished(List<MusicNode> results);
    }

}
