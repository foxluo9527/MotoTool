package com.motoll.one.common;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.motoll.one.BaseActivity;
import com.motoll.one.R;
import com.motoll.one.data.Bill;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xutil.data.DateUtils;
import com.xuexiang.xutil.tip.ToastUtils;

public class EditDialog extends BaseDialog {
    private OnEditListener listener;
    private EditText name;
    private EditText price;
    private TextView date;
    private View sure;
    private View close;
    private Bill bill;

    public EditDialog(BaseActivity context, Bill bill) {
        super(context);
        this.bill = bill;
        initData();
    }

    @Override
    int initLayoutId() {
        return R.layout.dialog_edit_bill;
    }

    @Override
    float initWidthPercent() {
        return 1f;
    }

    @Override
    void initView(View view) {
        name = (EditText) view.findViewById(R.id.et_name);
        price = (EditText) view.findViewById(R.id.et_price);
        date = (TextView) view.findViewById(R.id.tv_date);
        sure = view.findViewById(R.id.iv_edit_sure);
        close = view.findViewById(R.id.iv_editor_close);
    }

    @SuppressLint("SetTextI18n")
    @Override
    void initData() {
        if (bill != null) {
            name.setText(bill.getName());
            price.setText(bill.getPrice()>0?bill.getPrice()+"":(0- bill.getPrice()+""));
            date.setText(bill.getDate());
        }
    }

    @Override
    void initListener() {
        sure.setOnClickListener(v -> {
            String name = this.name.getText().toString();
            String date = this.date.getText().toString();
            String priceString = price.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtils.toast("请输入名称");
                return;
            }
            if (TextUtils.isEmpty(priceString)) {
                ToastUtils.toast("请输入金额");
                return;
            }
            if (TextUtils.isEmpty(date)) {
                ToastUtils.toast("请选择日期");
                return;
            }
            double price = Double.parseDouble(priceString);
            if (price <= 0) {
                ToastUtils.toast("请输入0以上的金额");
                return;
            }
            bill.setName(name);
            bill.setPrice(bill.getPrice()>0?price:(0-price));
            bill.setDate(date);
            try {
                MyApplication.mDBService.updateData(bill);
                if (listener != null)
                    listener.success(bill);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.toast("修改失败");
            }
            dismiss();
        });
        date.setOnClickListener(v -> {
            dismiss();
            TimePickerView pickerView=new TimePickerBuilder(getActivity(), (d, v1) ->{
                date.setText(DateUtils.date2String(d, DateUtils.yyyyMMdd.get()));
                show();
            }).setOutSideCancelable(false).setTitleText("选择日期").build();
            pickerView.findViewById(R.id.btnCancel).setOnClickListener(view -> {
                pickerView.dismiss();
                show();
            });
            pickerView.show();
        });
        close.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.cancel();
            }
        });
    }

    public void setOnEditListener(OnEditListener listener) {
        this.listener = listener;
    }

    public interface OnEditListener {
        void success(Bill bill);

        void cancel();
    }
}
