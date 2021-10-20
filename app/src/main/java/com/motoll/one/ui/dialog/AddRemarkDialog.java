package com.motoll.one.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.motoll.one.R;
import com.motoll.one.ui.BaseDialog;

public class AddRemarkDialog extends BaseDialog {
    private RemarkChangedListener listener;
    private TextView tvOk;
    private EditText input;
    private String remark;

    public AddRemarkDialog(Activity context, Bundle data) {
        super(context, data);
    }

    public void setListener(RemarkChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public int initLayoutId() {
        return R.layout.dialog_add_remark;
    }

    @Override
    public float initWidthPercent() {
        return 1.0f;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.exit).setOnClickListener(v -> dismiss());
        tvOk = view.findViewById(R.id.tv_ok);
        input = view.findViewById(R.id.et_input);
    }

    @Override
    public void initData() {
        String remark = getData().getString("remark");
        if (remark != null) {
            this.remark = remark;
            input.setText(remark);
        }
    }

    @Override
    public void initListener() {
        tvOk.setOnClickListener(v -> returnRemark());
    }

    private void returnRemark() {
        if (listener != null) {
            remark=input.getText().toString();
            listener.onRemarkChanged(remark);
            dismiss();
        }
    }

    public interface RemarkChangedListener {
        void onRemarkChanged(String Remarks);
    }
}
