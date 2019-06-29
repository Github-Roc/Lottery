package com.peng.lottery.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Base64工具类
 * Created by tofirst on 2019/4/28.
 */
public class Base64Util {

    /**
     * Bitmap转Base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                //将bitmap放入字节数组流中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
                // 将bos流缓存在内存中的数据全部输出，清空缓存
                bos.flush();
                bos.close();
                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.NO_WRAP);
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Base64转Bitmap
     */
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 字符串进行Base64编码
     */
    public static String stringToBase64(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
    }

    /**
     * 字符串进行Base64解码
     */
    public static String base64ToString(String string) {
        return new String(Base64.decode(string, Base64.DEFAULT));
    }

    /**
     * 对文件进行Base64编码
     */
    public static String fileToBase64(File file) {
        String encodedString = "";
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            encodedString = Base64.encodeToString(buffer, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    /**
     * 对文件进行Base64解码
     */
    public static void base64ToFile(String string, File file) {
        FileOutputStream fos = null;
        try {
            byte[] decodeBytes = Base64.decode(string.getBytes(), Base64.NO_WRAP);
            fos = new FileOutputStream(file);
            fos.write(decodeBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
