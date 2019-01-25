package com.peng.lottery.app.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateFormatUtil {

    public static String getSysTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getSysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getAllDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }

    public static String getFormatTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getFormatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }

    public static Date parseTime(String d) {
        if (d == null || d.length() == 0) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(d);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date parseDate(String d) {
        if (d == null || d.length() == 0) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(d);
        } catch (Exception ex) {
            return null;
        }
    }

    public static byte[] intTobyte(int _int) {
        byte[] _byte = new byte[4];
        for (int i = 0; i < _byte.length; i++) {
            _byte[i] = (byte) (_int >> (3 - i) * 8);
        }
        return _byte;
    }

    public static int byteToint(byte[] _byte) {
        int _int = 0;
        int mask = 0xff;
        int temp = 0;
        for (int i = 0; i < 4; i++) {
            _int <<= 8;
            temp = _byte[i] & mask;
            _int |= temp;
        }
        return _int;
    }
}
