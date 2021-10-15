package com.motoll.one.ui;

import android.app.Activity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public abstract class BaseDialog {
    private View view;
    private AlertDialog dialog;
    private Activity activity;

    public abstract int initLayoutId();

    /**
     * @return 弹窗占屏幕宽度百分比
     */
    public abstract float initWidthPercent();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initListener();

    public BaseDialog(Activity context) {
        activity=context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        view = View.inflate(context, initLayoutId(), null);
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int) (d.getWidth() * initWidthPercent());
        p.height = d.getHeight();
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);
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
