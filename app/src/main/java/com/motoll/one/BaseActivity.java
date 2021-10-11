package com.motoll.one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xutil.display.Colors;

public abstract class BaseActivity extends AppCompatActivity implements KeyboardUtils.SoftKeyboardToggleListener {
    private boolean isFullScreen;
    abstract int initLayoutId();
    abstract void initListener();
    abstract void initView();
    abstract void initData();
    private Context context;
    //是否全屏
    abstract boolean initFullScreen();
    public void setStatusBarTranslate() {
        StatusBarUtils.initStatusBarStyle(this, false, Colors.TRANSPARENT);
    }

    public void setStatusBarDark(boolean isDark) {
        if (isDark)
            StatusBarUtils.setStatusBarLightMode(this);
        else
            StatusBarUtils.setStatusBarDarkMode(this);
    }

    public void setFullScreen(boolean fullScreen){
        if (fullScreen) {
            StatusBarUtils.fullScreen(this);
        } else {
            StatusBarUtils.cancelFullScreen(this);
        }
    }
    public int getStatusBarHeight() {
        return StatusBarUtils.getNavigationBarHeight(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        XUI.initTheme(this);
        isFullScreen=initFullScreen();
        setContentView(initLayoutId());
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        context=this;
        KeyboardUtils.addKeyboardToggleListener(this, this);
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFullScreen(isFullScreen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setFullScreen(isFullScreen);
    }

    @Override
    public void onToggleSoftKeyboard(boolean isVisible) {
        //键盘收起切回全屏
        if (!isVisible)
            setFullScreen(isFullScreen);
    }
}
