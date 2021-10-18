package com.motoll.one.ui.dialog;

import android.app.Activity;
import android.view.View;

import com.motoll.one.R;
import com.motoll.one.ui.BaseDialog;

public class ChoiceMemberDialog extends BaseDialog {
    public ChoiceMemberDialog(Activity context) {
        super(context);
    }

    @Override
    public int initLayoutId() {
        return R.layout.dialog_choice_member;
    }

    @Override
    public float initWidthPercent() {
        return 1.0f;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.exit).setOnClickListener(v-> dismiss());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
