package com.motoll.one.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.motoll.one.R;

public abstract class BaseDialog {
    private View view;
    private AlertDialog dialog;
    public Activity activity;
    private Bundle data;
    public abstract int initLayoutId();

    /**
     * @return 弹窗占屏幕宽度百分比
     */
    public abstract float initWidthPercent();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initListener();

    public BaseDialog(Activity context, Bundle data){
        activity=context;
        this.data=data;
        init();
    }

    public Bundle getData() {
        return data;
    }

    public BaseDialog(Activity context) {
        activity=context;
        init();
    }

    @SuppressLint("RestrictedApi")
    private void init(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.Dialog_Fullscreen);
        view = View.inflate(activity, initLayoutId(), null);
        builder.setView(view,0,0,0,0);
        builder.setCancelable(false);
        dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int) (d.getWidth() * initWidthPercent());
        p.height=WindowManager.LayoutParams.MATCH_PARENT;
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);
        dialogWindow.setWindowAnimations(R.style.windowAnim);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initView(view);
        initData();
        initListener();
    }
    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public Activity getActivity(){
        return activity;
    }
}
