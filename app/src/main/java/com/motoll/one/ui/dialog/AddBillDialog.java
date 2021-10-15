package com.motoll.one.ui.dialog;

import android.app.Activity;
import android.view.View;

import com.motoll.one.R;
import com.motoll.one.ui.BaseDialog;

public class AddBillDialog extends BaseDialog {
    View exit;
    public AddBillDialog(Activity context) {
        super(context);
    }

    @Override
    public int initLayoutId() {
        return R.layout.dialog_add_bill;
    }

    @Override
    public float initWidthPercent() {
        return 1.0f;
    }

    @Override
    public void initView(View view) {
        exit=view.findViewById(R.id.exit);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        exit.setOnClickListener(v->{
            dismiss();
        });
    }
}
