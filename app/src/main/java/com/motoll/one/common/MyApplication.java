package com.motoll.one.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.motoll.one.data.Bill;
import com.motoll.one.data.db.BillDataBase;
import com.xuexiang.xormlite.BillDataBaseRepository;
import com.xuexiang.xormlite.annotation.DataBase;
import com.xuexiang.xormlite.db.DBService;
import com.xuexiang.xutil.XUtil;

import java.util.Stack;

@DataBase(name = "bill")
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Stack<Activity> activityTask = new Stack<>();
    public static DBService<Bill> mDBService;
    public static void cleanTaskExpectTop() {
        Activity top = activityTask.lastElement();
        for (Activity activity : activityTask) {
            if (activity != top) {
                activity.finish();
            }
        }
    }

    @Override

    public void onCreate() {
        super.onCreate();
        XUtil.init(this);
        registerActivityLifecycleCallbacks(this);
        BillDataBaseRepository.getInstance()
                .setIDatabase(new BillDataBase())  //设置内部存储的数据库实现接口
                .init(this);
        mDBService = BillDataBaseRepository.getInstance().getDataBase(Bill.class);
    }

    @Override

    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        activityTask.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityTask.remove(activity);
    }
}
