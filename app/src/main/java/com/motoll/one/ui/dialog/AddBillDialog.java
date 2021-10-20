package com.motoll.one.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.motoll.one.R;
import com.motoll.one.common.CommonUtils;
import com.motoll.one.common.SPUtils;
import com.motoll.one.data.PayWay;
import com.motoll.one.ui.BaseDialog;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;

public class AddBillDialog extends BaseDialog implements View.OnClickListener {
    private String type = "上衣";
    public static final int BILL_IN = 1;
    public static final int BILL_OUT = 2;
    private int bill_type = BILL_IN;
    private boolean resultDone = false;
    private String members="";
    private PayWay payWay;
    private String remark="";
    View exit;
    EditText input;
    ArrayList<CheckedTextView> typesView;
    CheckedTextView billIn, billOut;
    ChoiceAccountDialog choiceAccountDialog;
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
        exit = view.findViewById(R.id.exit);
        input = view.findViewById(R.id.et_input);
        typesView = new ArrayList<>();
        typesView.add(view.findViewById(R.id.ctv_coat));
        typesView.add(view.findViewById(R.id.ctv_helmet));
        typesView.add(view.findViewById(R.id.ctv_gloves));
        typesView.add(view.findViewById(R.id.ctv_pants));
        typesView.add(view.findViewById(R.id.ctv_other));
        typesView.add(view.findViewById(R.id.ctv_moto));
        billIn = view.findViewById(R.id.ctv_bill_in);
        billOut = view.findViewById(R.id.ctv_bill_out);
        view.findViewById(R.id.key1).setOnClickListener(this);
        view.findViewById(R.id.key2).setOnClickListener(this);
        view.findViewById(R.id.key3).setOnClickListener(this);
        view.findViewById(R.id.key4).setOnClickListener(this);
        view.findViewById(R.id.key5).setOnClickListener(this);
        view.findViewById(R.id.key6).setOnClickListener(this);
        view.findViewById(R.id.key7).setOnClickListener(this);
        view.findViewById(R.id.key8).setOnClickListener(this);
        view.findViewById(R.id.key9).setOnClickListener(this);
        view.findViewById(R.id.key0).setOnClickListener(this);
        view.findViewById(R.id.key_plus).setOnClickListener(this);
        view.findViewById(R.id.key_sub).setOnClickListener(this);
        view.findViewById(R.id.key_mul).setOnClickListener(this);
        view.findViewById(R.id.key_div).setOnClickListener(this);
        view.findViewById(R.id.key_point).setOnClickListener(this);
        view.findViewById(R.id.key_equal).setOnClickListener(this);
        view.findViewById(R.id.key_del).setOnClickListener(this);
        view.findViewById(R.id.key_user).setOnClickListener(v-> {
            Bundle bundle=new Bundle();
            bundle.putString("members",members);
            ChoiceMemberDialog dialog=new ChoiceMemberDialog(activity,bundle);
            dialog.setListener(members -> {
                this.members=members;
            });
            dialog.show();
        });
        view.findViewById(R.id.key_account).setOnClickListener(v-> {
            choiceAccountDialog=new ChoiceAccountDialog(activity);
            choiceAccountDialog.setListener(payWay -> {
                this.payWay=payWay;
                String payName=payWay.getType();
                if (payWay.getType().equals("银行卡")||payWay.getType().equals("信用卡"))
                    payName+="-"+payWay.getName();
                ToastUtils.toast("已选择:"+payName);
            });
            choiceAccountDialog.show();
        });
        view.findViewById(R.id.tv_remark).setOnClickListener(v->{
            Bundle bundle=new Bundle();
            bundle.putString("remark",remark);
            AddRemarkDialog dialog=new AddRemarkDialog(activity,bundle);
            dialog.setListener(remark -> this.remark=remark);
            dialog.show();
        });
    }

    @Override
    public void initData() {
        input.setClickable(false);
        input.setKeyListener(null);
        if (payWay==null)
            payWay= SPUtils.getDefaultPayWay();
    }
    public void refreshChoiceAccountDialog(){
        if (choiceAccountDialog!=null){
            choiceAccountDialog.initData();
        }
    }
    @Override
    public void initListener() {
        for (CheckedTextView view : typesView) {
            view.setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.ctv_coat:
                        selectType(0);
                        break;
                    case R.id.ctv_helmet:
                        selectType(1);
                        break;
                    case R.id.ctv_gloves:
                        selectType(2);
                        break;
                    case R.id.ctv_pants:
                        selectType(3);
                        break;
                    case R.id.ctv_other:
                        selectType(4);
                        break;
                    case R.id.ctv_moto:
                        selectType(5);
                        break;
                }
            });
        }
        exit.setOnClickListener(v -> dismiss());
        billOut.setOnClickListener(v -> {
            billOut.setChecked(true);
            billIn.setChecked(false);
            bill_type = BILL_OUT;
        });
        billIn.setOnClickListener(v -> {
            billIn.setChecked(true);
            billOut.setChecked(false);
            bill_type = BILL_IN;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String content = input.getText().toString();
        char lastChar = content.charAt(content.length() - 1);
        if ((content.length() == 1 && lastChar == '0')) {
            input.setText("");
            content = "";
        }
        switch (view.getId()) {
            case R.id.key0:
                if (resultDone)
                    input.setText("");
                input.append("0");
                break;
            case R.id.key1:
                if (resultDone)
                    input.setText("");
                input.append("1");
                break;
            case R.id.key2:
                if (resultDone)
                    input.setText("");
                input.append("2");
                break;
            case R.id.key3:
                if (resultDone)
                    input.setText("");
                input.append("3");
                break;
            case R.id.key4:
                if (resultDone)
                    input.setText("");
                input.append("4");
                break;
            case R.id.key5:
                if (resultDone)
                    input.setText("");
                input.append("5");
                break;
            case R.id.key6:
                if (resultDone)
                    input.setText("");
                input.append("6");
                break;
            case R.id.key7:
                if (resultDone)
                    input.setText("");
                input.append("7");
                break;
            case R.id.key8:
                if (resultDone)
                    input.setText("");
                input.append("8");
                break;
            case R.id.key9:
                if (resultDone)
                    input.setText("");
                input.append("9");
                break;
            case R.id.key_plus:
                addSymbol(content, '+');
                break;
            case R.id.key_sub:
                addSymbol(content, '-');
                break;
            case R.id.key_mul:
                addSymbol(content, '*');
                break;
            case R.id.key_div:
                addSymbol(content, '/');
                break;
            case R.id.key_point:
                if (content.length() == 0 || lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    //直接小数点先加0
                    input.append("0.");
                } else if (!CommonUtils.isInPutInt(content)) {
                    //最后一位小数点或正在输入的数字已经是小数则不加点
                    break;
                } else {
                    input.append(".");
                }
                break;
            case R.id.key_equal:
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/')
                    return;
                if (!content.contains("+") && !content.contains("-") && !content.contains("*") && !content.contains("/")) {
                    if (content.length() == 0) {
                        input.setText("0");
                    }
                    return;
                }
                if (lastChar == '.') {
                    //最后一位为小数点最后添0
                    content += '0';
                }
                double result = CommonUtils.equal(content);
                if (result < 0) {
                    ToastUtils.toast("账单不能为负！");
                    result = 0 - result;
                }
                String stringResult;
                if ((result == Math.floor(result)) && !Double.isInfinite(result)) {
                    stringResult = String.valueOf((int) result);
                } else {
                    stringResult = String.valueOf(result);
                }
                input.setText(stringResult);
                break;
            case R.id.key_del:
                if (content.length() > 0)
                    content = content.substring(0, content.length() - 1);
                if (content.length() == 0)
                    input.setText("0");
                else
                    input.setText(content);
                break;
        }
        resultDone = view.getId() == R.id.key_equal;
    }

    private void addSymbol(String content, char symbol) {
        if (content.length() == 0)
            content = "0";
        char lastChar = content.charAt(content.length() - 1);
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/')
            content = content.substring(0, content.length() - 1);
        else if (lastChar == '.')
            content += '0';
        content += symbol;
        input.setText(content);
    }

    private void selectType(int position) {
        for (int i = 0; i < typesView.size(); i++) {
            typesView.get(i).setChecked(false);
        }
        type = typesView.get(position).getText().toString();
        typesView.get(position).setChecked(true);
    }
}
