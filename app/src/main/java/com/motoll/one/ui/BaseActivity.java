package com.motoll.one.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.motoll.one.common.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {
    private boolean isFullScreen;
    private boolean isDark;
    private boolean isTransparencyBar = false;

    public abstract int initLayoutId();

    public abstract void initListener();

    public abstract void initData();

    private Activity context;

    //是否全屏
    public abstract boolean initFullScreen();

    /**
     * 设置状态栏颜色
     *
     * @param statusColor
     */
    public void setStatusBarColor(int statusColor) {
        StatusBarUtil.setStatusBarColor(this, statusColor);
    }

    /**
     * 设置状态栏透明
     */
    public void setTransparencyBar() {
        StatusBarUtil.transparencyBar(this);
        isTransparencyBar = true;
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param dark
     */
    public void setBarTextColorIsDark(boolean dark) {
        if (isTransparencyBar) {
            StatusBarUtil.setAndroidNativeLightStatusBar(this, dark);
        } else {
            StatusBarUtil.changeStatusBarTextColor(this, dark);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isFullScreen = initFullScreen();
        setContentView(initLayoutId());
        super.onCreate(savedInstanceState);
        initData();
        initListener();
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public Context getContext() {
        return context;
    }
}
