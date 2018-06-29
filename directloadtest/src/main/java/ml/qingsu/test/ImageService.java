package ml.qingsu.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService {

    /**
     * 获取图片
     *
     * @param path 图片路径
     * @return
     */
    public static byte[] getImageByte(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 设置请求方法为GET
        conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
        byte[] data = null;
        try {
            data = StreamTool.read(inputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 获得图片的二进制数据
        return data;
    }

    /**
     * 获取图片
     *
     * @param path 图片路径
     * @return
     */
    public static Bitmap getImageBitmap(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            return bitmap;
        }
        return null;
    }

}