/**
 * Bmob移动后端云服务RestAPI工具类
 * <p>
 * 提供简单的RestAPI增删改查工具，可直接对表、云函数、支付订单、消息推送进行操作。
 * 使用方法：先初始化initBmob，后调用其他方法即可。
 * 具体使用方法及传参格式详见Bmob官网RestAPI开发文档。
 * http://docs.bmob.cn/restful/developdoc/index.html?menukey=develop_doc&key=develop_restful
 *
 * @author 金鹰
 * @version V1.3.2
 * @since 2015-11-10
 */
package com.ericsoft.bmob.restapi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class Bmob {

    private static boolean IS_INIT;
    private static int TIME_OUT = 10000;

    private static final String STRING_EMPTY = "";
    private static String APP_ID = Bmob.STRING_EMPTY;
    private static String REST_API_KEY = Bmob.STRING_EMPTY;
    private static String MASTER_KEY = Bmob.STRING_EMPTY;

    private static final String BMOB_APP_ID_TAG = "X-Bmob-Application-Id";
    private static final String BMOB_REST_KEY_TAG = "X-Bmob-REST-API-Key";
    private static final String BMOB_MASTER_KEY_TAG = "X-Bmob-Master-Key";
    private static final String BMOB_EMAIL_TAG = "X-Bmob-Email";
    private static final String BMOB_PASSWORD_TAG = "X-Bmob-Password";
    private static final String CONTENT_TYPE_TAG = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    private static final String UTF8 = "UTF-8";
    private static final String CHAR_RISK = ":";

    public static final String MSG_NOT_FOUND = "Not Found";
    public static final String MSG_FILE_NOT_FOUND = "file Not Found";
    public static final String MSG_ERROR = "Error";
    public static final String MSG_UNREGISTERED = "Unregistered";

    /**
     * 是否初始化Bmob
     *
     * @return 初始化结果
     */
    public static boolean isInit() {
        return Bmob.IS_INIT;
    }

    /**
     * 初始化Bmob
     *
     * @param appId
     *            填写 Application ID
     * @param restKey
     *            填写 REST API Key
     * @return 注册结果
     */
    public static boolean initBmob(String appId, String restKey) {
        return Bmob.initBmob(appId, restKey, 10000);
    }

    /**
     * 初始化Bmob
     *
     * @param appId
     *            填写 Application ID
     * @param restKey
     *            填写 REST API Key
     * @param timeout
     *            设置超时（1000~20000ms）
     * @return 注册结果
     */
    public static boolean initBmob(String appId, String restKey, int timeout) {
        Bmob.APP_ID = appId;
        Bmob.REST_API_KEY = restKey;
        if (!Bmob.APP_ID.equals(Bmob.STRING_EMPTY) && !Bmob.REST_API_KEY.equals(Bmob.STRING_EMPTY)) {
            Bmob.IS_INIT = true;
        }
        if (timeout > 1000 && timeout < 20000) {
            Bmob.TIME_OUT = timeout;
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, null, null);
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            Bmob.IS_INIT = false;
        }
        return Bmob.isInit();
    }

    /**
     * 初始化Bmob Master权限
     *
     * @param masterKey
     *            填写 Master Key
     */
    public static void initMaster(String masterKey) {
        Bmob.MASTER_KEY = masterKey;
    }

    /**
     * 查询表全部记录(最多仅查询1000条记录)
     *
     * @param tableName
     *            表名
     * @return JSON格式结果
     */
    public static String findAll(String tableName) {
        return Bmob.find(tableName, Bmob.STRING_EMPTY);
    }

    /**
     * 条件查询表全部记录(最多仅查询1000条记录)
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @return JSON格式结果
     */
    public static String findAll(String tableName, String where) {
        return Bmob.find(tableName, where, Bmob.STRING_EMPTY);
    }

    /**
     * 查询表单条记录
     *
     * @param tableName
     *            表名
     * @param objectId
     *            objectId
     * @return JSON格式结果
     */
    public static String findOne(String tableName, String objectId) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(findOne)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(findOne)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 查询表限定数量记录
     *
     * @param tableName
     *            表名
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String find(String tableName, int limit) {
        return Bmob.find(tableName, "{}", 0, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 条件查询表限定数量记录
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String find(String tableName, String where, int limit) {
        return Bmob.find(tableName, where, 0, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 条件查询表限定数量记录，返回指定列
     *
     * @param tableName
     *            表名
     * @param keys
     *            返回列 （例：score,name）
     * @param where
     *            条件JOSN格式
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String findColumns(String tableName, String keys,
                                     String where, int limit) {
        return Bmob.findColumns(tableName, keys, where, 0, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 查询表区间记录
     *
     * @param tableName
     *            表名
     * @param skip
     *            跳过记录数
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String find(String tableName, int skip, int limit) {
        return Bmob.find(tableName, "{}", skip, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 条件查询表区间记录
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @param skip
     *            跳过记录数
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String find(String tableName, String where, int skip,
                              int limit) {
        return Bmob.find(tableName, where, skip, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 条件查询表区间记录,返回指定列
     *
     * @param tableName
     *            表名
     * @param keys
     *            返回列 （例：score,name）
     * @param where
     *            条件JOSN格式
     * @param skip
     *            跳过记录数
     * @param limit
     *            查询记录数（1~1000）
     * @return JSON格式结果
     */
    public static String findColumns(String tableName, String keys,
                                     String where, int skip, int limit) {
        return Bmob.findColumns(tableName, keys, where, skip, limit, Bmob.STRING_EMPTY);
    }

    /**
     * 排序查询表记录
     *
     * @param tableName
     *            表名
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String find(String tableName, String order) {
        return Bmob.find(tableName, "{}", 0, 1000, order);
    }

    /**
     * 条件排序查询表记录
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String find(String tableName, String where, String order) {
        return Bmob.find(tableName, where, 0, 1000, order);
    }

    /**
     * 条件排序查询表记录,返回指定列
     *
     * @param tableName
     *            表名
     * @param keys
     *            返回列 （例：score,name）
     * @param where
     *            条件JOSN格式
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String findColumns(String tableName, String keys,
                                     String where, String order) {
        return Bmob.findColumns(tableName, keys, where, 0, 1000, order);
    }

    /**
     * 排序查询表限定数量记录
     *
     * @param tableName
     *            表名
     * @param limit
     *            查询记录数（1~1000）
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String find(String tableName, int limit, String order) {
        return Bmob.find(tableName, "{}", 0, limit, order);
    }

    /**
     * 条件排序查询表限定数量记录
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @param limit
     *            查询记录数（1~1000）
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String find(String tableName, String where, int limit,
                              String order) {
        return Bmob.find(tableName, where, 0, limit, order);
    }

    /**
     * 条件排序查询表限定数量记录,返回指定列
     *
     * @param tableName
     *            表名
     * @param keys
     *            返回列 （例：score,name）
     * @param where
     *            条件JOSN格式
     * @param limit
     *            查询记录数（1~1000）
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String findColumns(String tableName, String keys,
                                     String where, int limit, String order) {
        return Bmob.findColumns(tableName, keys, where, 0, limit, order);
    }

    /**
     * 条件排序查询表区间记录
     *
     * @param tableName
     *            表名
     * @param where
     *            条件JOSN格式
     * @param skip
     *            跳过记录数
     * @param limit
     *            查询记录数（1~1000）
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String find(String tableName, String where, int skip,
                              int limit, String order) {
        return Bmob.findColumns(tableName, Bmob.STRING_EMPTY, where, skip, limit, order);
    }

    /**
     * 条件排序查询表区间记录,返回指定列
     *
     * @param tableName
     *            表名
     * @param keys
     *            返回列 （例：score,name）
     * @param where
     *            条件JOSN格式
     * @param skip
     *            跳过记录数
     * @param limit
     *            查询记录数（1~1000）
     * @param order
     *            排序字段（例：score,-name）
     * @return JSON格式结果
     */
    public static String findColumns(String tableName, String keys,
                                     String where, int skip, int limit, String order) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            skip = skip < 0 ? 0 : skip;
            limit = limit < 0 ? 0 : limit;
            limit = limit > 1000 ? 1000 : limit;
            where = where.equals(Bmob.STRING_EMPTY) ? "{}" : where;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName
                    + "?where=" + Bmob.urlEncoder(where) + "&limit=" + limit
                    + "&skip=" + skip + "&order=" + order + "&keys=" + keys;

            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            } catch (Exception e) {
                Log.e("---Bmob---", e.toString());
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        Log.d("---Bmob---", result);
        return result;
    }

    /**
     * BQL查询表记录
     *
     * @param BQL
     *            SQL语句。例如：select * from Student where name=\"张三\" limit 0,10
     *            order by name
     * @return JSON格式结果
     */
    public static String findBQL(String BQL) {
        return Bmob.findBQL(BQL, Bmob.STRING_EMPTY);
    }

    /**
     * BQL查询表记录
     *
     * @param BQL
     *            SQL语句。例如：select * from Student where name=? limit ?,? order by
     *            name
     * @param value
     *            参数对应SQL中?以,为分隔符。例如"\"张三\",0,10"
     * @return JSON格式结果
     */
    public static String findBQL(String BQL, String value) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            BQL = Bmob.urlEncoder(BQL) + "&values=[" + Bmob.urlEncoder(value) + "]";
            String mURL = "https://api.bmob.cn/1/cloudQuery?bql=" + BQL;

            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(findBQL)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(findBQL)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 获取服务器时间
     *
     * @return
     */
    public static String getServerTime() {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/timestamp/";
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(getServerTime)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(getServerTime)"
                        + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 查询表记录数
     *
     * @param tableName
     *            表名
     * @return 统计值
     */
    public static int count(String tableName) {
        return Bmob.count(tableName, "{}");
    }

    /**
     * 条件查询记录数
     *
     * @param tableName
     *            表名
     * @param where
     *            查询条件(JSON格式)
     * @return 统计值
     */
    public static int count(String tableName, String where) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName
                    + "?where=" + Bmob.urlEncoder(where) + "&count=1&limit=0";
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(count)" + e.getMessage();
                System.err.println("Warn: " + result);
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(count)" + e.getMessage();
                System.err.println("Warn: " + result);
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
            System.err.println("Warn: " + result);
        }
        int count = 0;
        if (result.contains(Bmob.MSG_NOT_FOUND) || result.contains(Bmob.MSG_ERROR)
                || result.equals(Bmob.MSG_UNREGISTERED)) {
            return count;
        } else {
            if (result.contains("count")) {
                count = Integer.valueOf(result.replaceAll("[^0-9]",
                        Bmob.STRING_EMPTY));
            }
        }
        return count;
    }

    /**
     * 修改记录
     *
     * @param tableName
     *            表名
     * @param objectId
     *            objectId
     * @param paramContent
     *            JSON格式参数
     * @return JSON格式结果
     */
    public static String update(String tableName, String objectId,
                                String paramContent) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_PUT);
                conn.setDoOutput(true);
                conn.connect();
                Bmob.printWriter(conn, paramContent);
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(update)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(update)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 插入记录
     *
     * @param tableName
     *            表名
     * @param paramContent
     *            JSON格式参数
     * @return JSON格式结果
     */
    public static String insert(String tableName, String paramContent) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName;
            System.out.println(mURL);
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                System.out.println("Connect OK!");
                Bmob.printWriter(conn, paramContent);
                System.out.println("printWriter OK!");
                result = Bmob.getResultFromConnection(conn);
                System.out.println("Get Result OK!");
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(insert)"
                        + e.getMessage();
                Log.e("--BMOB---", e.toString());
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(insert)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 删除记录
     *
     * @param tableName
     *            表名
     * @param objectId
     *            objectId
     * @return JSON格式结果
     */
    public static String delete(String tableName, String objectId) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL),
                        Bmob.METHOD_DELETE);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(delete)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(delete)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 查询支付订单
     *
     * @param payId
     *            交易编号
     * @return JSON格式结果
     */
    public static String findPayOrder(String payId) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/pay/" + payId;
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(findPayOrder)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(findPayOrder)"
                        + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 推送消息
     *
     * @param data
     *            data 详细使用方法参照
     *            http://docs.bmob.cn/restful/developdoc/index.html
     *            ?menukey=develop_doc&key=develop_restful#index_消息推送简介
     * @return JSON格式结果
     */
    public static String pushMsg(String data) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/push";
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                Bmob.printWriter(conn, data);
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(pushMsg)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(pushMsg)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 调用云端代码
     *
     * @param funcName
     *            云函数名
     * @param paramContent
     *            JSON格式参数
     * @return JSON格式结果
     */
    public static String callFunction(String funcName, String paramContent) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/functions/" + funcName;
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                Bmob.printWriter(conn, paramContent);
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(callFunction)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(callFunction)"
                        + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 文件上传
     *
     * @param file
     *            file 完整路径文件
     * @return String
     */
    public static String uploadFile(String file) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            // 获取文件名
            String fileName = file.trim();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            String mURL = "https://api.bmob.cn/1/files/" + fileName;
            try {
                FileInputStream fis = new FileInputStream(file);
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                // 一次读多个字节
                byte[] tempbytes = new byte[1];
                OutputStream os = conn.getOutputStream();
                while (fis.read(tempbytes) != -1) {
                    os.write(tempbytes);
                }
                os.flush();
                os.close();
                fis.close();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_FILE_NOT_FOUND + Bmob.CHAR_RISK + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    /**
     * 登录账户获取所有APP信息
     *
     * @param user
     * @param passwd
     * @return JSON格式结果
     */
    public static String login(String user, String passwd) {
        String result = Bmob.STRING_EMPTY;
        HttpURLConnection conn = null;
        String mURL = "https://api.bmob.cn/1/apps";
        try {
            conn = Bmob.connectionLoginSetting(conn, new URL(mURL), Bmob.METHOD_GET,
                    user, passwd);
            conn.connect();
            result = Bmob.getResultFromConnection(conn);
            conn.disconnect();
        } catch (FileNotFoundException e) {
            result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(Login)" + e.getMessage();
        } catch (Exception e) {
            result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(Login)" + e.getMessage();
        }
        return result;
    }

    /**
     * 获取APP所有表信息 必需填写Master-Key
     *
     * @return JSON格式结果
     */
    public static String findAllTables() {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit() && !Bmob.MASTER_KEY.equals(Bmob.STRING_EMPTY)) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/schemas";
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_GET);
                conn.connect();
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(findAllTables)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(findAllTables)"
                        + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }


    /**
     * 调用网页支付
     *
     * @param payInfo JSON格式，包含order_price、product_name、body
     * @return JSON格式结果
     */
    public static String webPay(String payInfo) {
        String result = Bmob.STRING_EMPTY;
        if (Bmob.isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/webpay";
            try {
                conn = Bmob.connectionCommonSetting(conn, new URL(mURL), Bmob.METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                Bmob.printWriter(conn, payInfo);
                result = Bmob.getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = Bmob.MSG_NOT_FOUND + Bmob.CHAR_RISK + "(webPay)"
                        + e.getMessage();
            } catch (Exception e) {
                result = Bmob.MSG_ERROR + Bmob.CHAR_RISK + "(webPay)" + e.getMessage();
            }
        } else {
            result = Bmob.MSG_UNREGISTERED;
        }
        return result;
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
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
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 复合查询-或
     *
     * @param where1
     *            JSON格式条件一
     * @param where2
     *            JSON格式条件二
     * @return 复合或字符串
     */
    public static String whereOr(String where1, String where2) {
        return "{\"$or\":[" + where1 + "," + where2 + "]}";
    }

    /**
     * 复合查询-与
     *
     * @param where1
     *            JSON格式条件一
     * @param where2
     *            JSON格式条件二
     * @return 复合与字符串
     */
    public static String whereAnd(String where1, String where2) {
        return "{\"$and\":[" + where1 + "," + where2 + "]}";
    }

    /**
     * 操作符-小于
     *
     * @param value
     *            目标值
     * @return 复合小于字符串
     */
    public static String whereLess(int value) {
        return "{\"$lt\":" + value + "}";
    }

    /**
     * 操作符-小于
     *
     * @param value
     *            目标值
     * @return 复合小于字符串
     */
    public static String whereLess(String value) {
        return "{\"$lt\":" + value + "}";
    }

    /**
     * 操作符-小于等于
     *
     * @param value
     *            目标值
     * @return 复合小于等于字符串
     */
    public static String whereLessEqual(int value) {
        return "{\"$lte\":" + value + "}";
    }

    /**
     * 操作符-小于等于
     *
     * @param value
     *            目标值
     * @return 复合小于等于字符串
     */
    public static String whereLessEqual(String value) {
        return "{\"$lte\":" + value + "}";
    }

    /**
     * 操作符-大于
     *
     * @param value
     *            目标值
     * @return 复合大于字符串
     */
    public static String whereGreate(int value) {
        return "{\"$gt\":" + value + "}";
    }

    /**
     * 操作符-大于
     *
     * @param value
     *            目标值
     * @return 复合大于字符串
     */
    public static String whereGreate(String value) {
        return "{\"$gt\":" + value + "}";
    }

    /**
     * 操作符-大于等于
     *
     * @param value
     *            目标值
     * @return 复合大于等于字符串
     */
    public static String whereGreateEqual(int value) {
        return "{\"$gte\":" + value + "}";
    }

    /**
     * 操作符-大于等于
     *
     * @param value
     *            目标值
     * @return 复合大于等于字符串
     */
    public static String whereGreateEqual(String value) {
        return "{\"$gte\":" + value + "}";
    }

    /**
     * 操作符-不等于
     *
     * @param value
     *            目标值
     * @return 复合不等于字符串
     */
    public static String whereNotEqual(int value) {
        return "{\"$ne\":" + value + "}";
    }

    /**
     * 操作符-不等于
     *
     * @param value
     *            目标值
     * @return 复合不等于字符串
     */
    public static String whereNotEqual(String value) {
        return "{\"$ne\":" + value + "}";
    }

    /**
     * 操作符-包含
     *
     * @param value
     *            目标数组值(例：new int[]{1,3,5,7})
     * @return 复合包含字符串
     */
    public static String whereIn(int[] value) {
        String result = Bmob.STRING_EMPTY;
        for (int i = 0; i < value.length; i++) {
            result = i == value.length - 1 ? String.valueOf(result + value[i])
                    : result + value[i] + ",";
        }
        return "{\"$in\":[" + result + "]}";
    }

    /**
     * 操作符-包含
     *
     * @param value
     *            目标数组值(例：new String[]{"张三","李四","王五"})
     * @return 复合包含字符串
     */
    public static String whereIn(String[] value) {
        String result = Bmob.STRING_EMPTY;
        for (int i = 0; i < value.length; i++) {
            result = i == value.length - 1 ? result + "\"" + value[i] + "\""
                    : result + "\"" + value[i] + "\",";
        }
        return "{\"$in\":[" + result + "]}";
    }

    /**
     * 操作符-包含
     *
     * @param value
     *            目标数组值(例："1,3,5,7")
     * @return 复合包含字符串
     */
    public static String whereIn(String value) {
        return "{\"$in\":[" + value + "]}";
    }

    /**
     * 操作符-不包含
     *
     * @param value
     *            目标数组值(例：new int[]{1,3,5,7})
     * @return 复合不包含字符串
     */
    public static String whereNotIn(int[] value) {
        String result = Bmob.STRING_EMPTY;
        for (int i = 0; i < value.length; i++) {
            result = i == value.length - 1 ? String.valueOf(result + value[i])
                    : result + value[i] + ",";
        }
        return "{\"$nin\":[" + result + "]}";
    }

    /**
     * 操作符-不包含
     *
     * @param value
     *            目标数组值(例：new String[]{"张三","李四","王五"})
     * @return 复合不包含字符串
     */
    public static String whereNotIn(String[] value) {
        String result = Bmob.STRING_EMPTY;
        for (int i = 0; i < value.length; i++) {
            result = i == value.length - 1 ? result + "\"" + value[i] + "\""
                    : result + "\"" + value[i] + "\",";
        }
        return "{\"$nin\":[" + result + "]}";
    }

    /**
     * 操作符-不包含
     *
     * @param value
     *            目标数组值(例："\"张三\",\"李四\",\"王五\"")
     * @return 复合不包含字符串
     */
    public static String whereNotIn(String value) {
        return "{\"$nin\":[" + value + "]}";
    }

    /**
     * 操作符-存在
     *
     * @param value
     *            布尔值
     * @return 复合存在字符串
     */
    public static String whereExists(boolean value) {
        return "{\"$exists\":" + value + "}";
    }

    /**
     * 操作符-全包含
     *
     * @param value
     *            目标值
     * @return 复合全包含字符串
     */
    public static String whereAll(String value) {
        return "{\"$all\":[" + value + "]}";
    }

    /**
     * 操作符-区间包含
     *
     * @param greatEqual
     *            是否大于包含等于
     * @param greatValue
     *            大于的目标值
     * @param lessEqual
     *            是否小于包含等于
     * @param lessValue
     *            小于的目标值
     * @return 复合区间包含字符串
     *
     *         例：查询[1000,3000), whereIncluded(true,1000,false,3000)
     */
    public static String whereIncluded(boolean greatEqual, int greatValue,
                                       boolean lessEqual, int lessValue) {
        return Bmob.whereIncluded(greatEqual, String.valueOf(greatValue), lessEqual,
                String.valueOf(lessValue));
    }

    /**
     * 操作符-区间包含
     *
     * @param greatEqual
     *            是否大于包含等于
     * @param greatValue
     *            大于的目标值
     * @param lessEqual
     *            是否小于包含等于
     * @param lessValue
     *            小于的目标值
     * @return 复合区间包含字符串
     *
     *         例：查询[1000,3000), whereIncluded(true,"1000",false,"3000")
     */
    public static String whereIncluded(boolean greatEqual, String greatValue,
                                       boolean lessEqual, String lessValue) {
        String op1;
        String op2;
        op1 = greatEqual ? "\"$gte\"" : "\"$gt\"";
        op2 = lessEqual ? "\"$lte\"" : "\"$lt\"";
        return "{" + op1 + ":" + greatValue + "," + op2 + ":" + lessValue + "}";
    }

    /**
     * 操作符-正则表达式
     *
     * @param regexValue
     * @return 复合正则表达式字符串
     */
    public static String whereRegex(String regexValue) {
        String op = "\"$regex\"";
        return "{" + op + ":\"" + regexValue + "\"}";
    }

    /**
     * 操作符-含有字符串
     *
     * @param value
     * @return 复合含有字符串表达式字符串
     */
    public static String whereLike(String value) {
        return Bmob.whereRegex(".*" + value + ".*");
    }

    public static int getTimeout() {
        return Bmob.TIME_OUT;
    }

    public static void setTimeout(int timeout) {
        Bmob.TIME_OUT = timeout;
    }

    private static void printWriter(HttpURLConnection conn, String paramContent)
            throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(paramContent.getBytes());
        out.flush();
        out.close();
    }

    private static String getResultFromConnection(HttpURLConnection conn)
            throws IOException {
        StringBuffer result = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), Bmob.UTF8));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

    private static HttpURLConnection connectionLoginSetting(
            HttpURLConnection conn, URL url, String method, String user,
            String passwd) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setReadTimeout(Bmob.TIME_OUT);

        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);

        conn.setRequestProperty(Bmob.BMOB_EMAIL_TAG, user);
        conn.setRequestProperty(Bmob.BMOB_PASSWORD_TAG, passwd);
        conn.setRequestProperty(Bmob.CONTENT_TYPE_TAG, Bmob.CONTENT_TYPE_JSON);
        return conn;
    }

    private static HttpURLConnection connectionCommonSetting(
            HttpURLConnection conn, URL url, String method) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setReadTimeout(Bmob.TIME_OUT);

        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);

        conn.setRequestProperty(Bmob.BMOB_APP_ID_TAG, Bmob.APP_ID);
        conn.setRequestProperty(Bmob.BMOB_REST_KEY_TAG, Bmob.REST_API_KEY);
        if (!Bmob.MASTER_KEY.equals(Bmob.STRING_EMPTY)) {
            conn.setRequestProperty(Bmob.BMOB_MASTER_KEY_TAG, Bmob.MASTER_KEY);
        }

        conn.setRequestProperty(Bmob.CONTENT_TYPE_TAG, Bmob.CONTENT_TYPE_JSON);
        return conn;
    }

    private static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str, Bmob.UTF8);
        } catch (UnsupportedEncodingException e1) {
            return str;
        }
    }

}