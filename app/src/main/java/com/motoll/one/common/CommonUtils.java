package com.motoll.one.common;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.motoll.one.R;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CommonUtils {
    private static CommonUtils instance;

    public CommonUtils(){

    }
    public static CommonUtils getInstance() {
        if (instance == null) {
            synchronized (CommonUtils.class) {
                if (instance == null)
                    instance = new CommonUtils();
            }
        }
        return instance;
    }
    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    // 将File 转化为 content://URI
    public static Uri getFileProvider(Context context, File file) {
        // ‘authority’要与`AndroidManifest.xml`中`provider`配置的`authorities`一致，假设你的应用包名为com.example.app
        String authority = context.getPackageName() + ".fileprovider";
        Uri contentUri = FileProvider.getUriForFile(context, authority, file);

        // 授权给微信访问路径
        context.grantUriPermission("com.tencent.mm",  // 这里填微信包名
                contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return contentUri;
    }
    /**
     * @return content Uri
     */
    @SuppressLint("Range")
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    public static boolean isInPutInt(String content) {
        if (content.length()==0)
            return false;
        char lastChar=content.charAt(content.length()-1);
        boolean inPutInt = false;
        if (lastChar == '.') {
            return false;
        } else if (!(lastChar <= '9' && lastChar >= '0')) {
            return true;
        }
        //找到式中最后一个小数点
        int startIndex = content.lastIndexOf(".");
        //如果不是-1式中有小数点
        if (startIndex != -1) {
            char[] chars = new char[content.length() - startIndex - 1];
            content.getChars(startIndex + 1, content.length(), chars, 0);
            for (int i = 0; i < chars.length; i++) {
                if (!(chars[i] <= '9' && chars[i] >= '0')) {
                    inPutInt = true;
                    break;
                }
            }
            return inPutInt;
        }
        //Toast.makeText(getApplicationContext(),"没有小数点:",Toast.LENGTH_SHORT).show();
        return true;
    }
    public static double equal(String content) {
        // 找到字符串中最后一个左括号
        int startIndex = content.lastIndexOf("(");
        // 如果不是-1,标识这个等式中有括号,继续找与之对应的右括号
        if (startIndex != -1) {
            // 从左括号的位置开始找,找到第一个右括号,这对括号里面一定没有括号,所以就可以交给写好的equal(Stringcontent)方法算出结果！
            int endIndex = content.indexOf(")", startIndex);
            double d = equal(content.substring(startIndex + 1, endIndex));
            return equal(content.substring(0, startIndex) //
                    + d + content.substring(endIndex + 1));
        }

        int index = content.indexOf("+");
        if (index != -1) {
            return add(equal(content.substring(0, index))
                    , equal(content.substring(index + 1)));
        }
        // 这里<---
        index = content.lastIndexOf("-");
        if (index != -1) {
            return sub(equal(content.substring(0, index)), equal(content.substring(index + 1)));
        }
        // 这里<---
        index = content.indexOf("*");
        if (index != -1) {
            return mul(equal(content.substring(0, index)), equal(content.substring(index + 1)));
        }
        // 这里<---
        index = content.lastIndexOf("/");
        if (index != -1) {
            return div(equal(content.substring(0, index)), equal(content.substring(index + 1)));
        }
        return Double.parseDouble(content);
    }
    private static final int DEF_DIV_SCALE = 10;

    //精确的加法算法
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }

    //精确的减法算法
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }

    //精确的乘法算法
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }

    //相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位
    public static double div(double d1, double d2) {

        return div(d1, d2, DEF_DIV_SCALE);

    }

    //相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后指定精度(scale)，再往后的数字四舍五入
    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
