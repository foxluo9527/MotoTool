package com.motoll.one.common;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.motoll.one.data.PayWay;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SPUtils {
    private static SharedPreferences sp = com.xuexiang.xutil.data.SPUtils.getSharedPreferences("MotoTool");

    public static boolean isFirstUse() {
        return com.xuexiang.xutil.data.SPUtils.getBoolean(sp, "first_use", true);
    }

    public static void setFirstUse() {
        com.xuexiang.xutil.data.SPUtils.putBoolean(sp, "first_use", false);
    }

    /**
     * 添加银行卡
     *
     * @param payWay
     * @return
     */
    public static boolean addPayCard(PayWay payWay) {
        ArrayList<PayWay> list = getAllPayWay(false);
        long maxId = 0L;
        for (PayWay way : list) {
            if (way.getNumber().equals(payWay.getNumber()))
                return false;
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        payWay.setDel(false);
        payWay.setId(maxId + 1);
        list.add(payWay);
        return com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
    }

    /**
     * 编辑支付方式
     *
     * @param payWay
     * @return
     */
    public static boolean editPayWay(PayWay payWay) {
        ArrayList<PayWay> list = getAllPayWay(false);
        long maxId = 0L;
        for (int i = 0; i < list.size(); i++) {
            PayWay way = list.get(i);
            if (way.getId() == payWay.getId()) {
                list.remove(way);
                i--;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //该支付方式未添加则添加
        if (payWay.getId() == 0L)
            payWay.setId(maxId + 1);
        list.add(payWay);
        return com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
    }

    public static PayWay getCash() {
        long maxId = 0L;
        ArrayList<PayWay> list = getAllPayWay(false);
        for (PayWay way : list) {
            if (way.getType().equals("现金")) {
                return way;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //第一次使用添加现金支付方式
        PayWay way = new PayWay();
        way.setId(maxId + 1);
        //现金为默认支付方式
        way.setDefault(true);
        way.setMoney(0);
        way.setType("现金");
        way.setDel(false);
        list.add(way);
        com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
        return way;
    }

    public static PayWay getWechat() {
        long maxId = 0L;
        ArrayList<PayWay> list = getAllPayWay(false);
        for (PayWay way : list) {
            if (way.getType().equals("微信")) {
                return way;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //第一次使用添加微信支付方式
        PayWay way = new PayWay();
        way.setId(maxId + 1);
        way.setDefault(false);
        way.setMoney(0);
        way.setType("微信");
        way.setDel(false);
        list.add(way);
        com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
        return way;
    }

    public static PayWay getAlipay() {
        long maxId = 0L;
        ArrayList<PayWay> list = getAllPayWay(false);
        for (PayWay way : list) {
            if (way.getType().equals("支付宝")) {
                return way;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //第一次使用添加支付宝支付方式
        PayWay way = new PayWay();
        way.setId(maxId + 1);
        way.setDefault(false);
        way.setMoney(0);
        way.setType("支付宝");
        way.setDel(false);
        list.add(way);
        com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
        return way;
    }

    public static PayWay getHB() {
        long maxId = 0L;
        ArrayList<PayWay> list = getAllPayWay(false);
        for (PayWay way : list) {
            if (way.getType().equals("花呗")) {
                return way;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //第一次使用添加花呗支付方式
        PayWay way = new PayWay();
        way.setId(maxId + 1);
        way.setDefault(false);
        way.setMoney(0);
        way.setType("花呗");
        way.setDel(false);
        list.add(way);
        com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
        return way;
    }

    public static PayWay getJD() {
        long maxId = 0L;
        ArrayList<PayWay> list = getAllPayWay(false);
        for (PayWay way : list) {
            if (way.getType().equals("京东白条")) {
                return way;
            }
            if (way.getId() > maxId)
                maxId = way.getId();
        }
        //第一次使用添加京东白条支付方式
        PayWay way = new PayWay();
        way.setId(maxId + 1);
        way.setDefault(false);
        way.setMoney(0);
        way.setType("京东白条");
        way.setDel(false);
        list.add(way);
        com.xuexiang.xutil.data.SPUtils.putString(sp, "all_pay_way", setJson(list));
        return way;
    }

    /**
     * 获取所有银行卡支付方式
     *
     * @return
     */
    public static ArrayList<PayWay> getBankWay() {
        String strJson = sp.getString("all_pay_way", "");
        if (!TextUtils.isEmpty(strJson)) {
            ArrayList<PayWay> list = jsonToArrayList(strJson, PayWay.class);
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getType().equals("银行卡")) {
                    list.remove(i);
                    i--;
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }
    /**
     * 获取所有信用卡支付方式
     *
     * @return
     */
    public static ArrayList<PayWay> getCreditWay() {
        String strJson = sp.getString("all_pay_way", "");
        if (!TextUtils.isEmpty(strJson)) {
            ArrayList<PayWay> list = jsonToArrayList(strJson, PayWay.class);
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getType().equals("信用卡")) {
                    list.remove(i);
                    i--;
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }
    /**
     * 获取所有支付方式
     *
     * @param show 获取结果是否用来展示
     * @return
     */
    public static ArrayList<PayWay> getAllPayWay(boolean show) {
        String strJson = sp.getString("all_pay_way", "");
        if (!TextUtils.isEmpty(strJson)) {
            ArrayList<PayWay> list = jsonToArrayList(strJson, PayWay.class);
            if (show) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isDel()) {
                        //已经删除的不展示
                        list.remove(i);
                        i--;
                    }
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    private static <T> T getJson(String jsonString, Class<T> clz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String setJson(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {

        ArrayList<T> arrayList = null;
        try {
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

            arrayList = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
