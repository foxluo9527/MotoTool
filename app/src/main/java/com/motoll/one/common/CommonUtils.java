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
    private ArrayList<Integer> cars;
    private ArrayList<Integer> selectCars;

    public CommonUtils(){
        cars=new ArrayList<>();
        selectCars =new ArrayList<>();
        cars.add(R.drawable.big_no1_img);
        cars.add(R.drawable.big_no2_img);
        cars.add(R.drawable.big_no3_img);
        cars.add(R.drawable.big_no4_img);
        cars.add(R.drawable.big_no5_hm_img);
        cars.add(R.drawable.big_no6_img);
        selectCars.add(R.drawable.no1_img);
        selectCars.add(R.drawable.no2_img);
        selectCars.add(R.drawable.no3_img);
        selectCars.add(R.drawable.no4_img);
        selectCars.add(R.drawable.no5_img);
        selectCars.add(R.drawable.no6_img);
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

    public ArrayList<Integer> getCars() {
        return cars;
    }

    public ArrayList<Integer> getSelectCars() {
        return selectCars;
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
