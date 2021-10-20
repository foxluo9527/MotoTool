package com.motoll.one.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motoll.one.MyApplication;
import com.motoll.one.R;
import com.motoll.one.common.CommonUtils;
import com.motoll.one.common.SPUtils;
import com.motoll.one.data.PayWay;
import com.motoll.one.ui.BaseDialog;
import com.motoll.one.ui.activity.AddCardActivity;
import com.motoll.one.ui.adapter.BillPayAdapter;

public class ChoiceAccountDialog extends BaseDialog implements View.OnClickListener {
    private AccountChangedListener listener;
    private boolean bankExpand = false;
    private boolean creditExpand = false;
    private ImageView bankExpandImg, creditExpandImg;
    private View bankView, creditView,banEmptyView,creditEmptyView;
    private RecyclerView rvBankCard,rvCreditCard;
    private BillPayAdapter bankAdapter,creditAdapter;
    public ChoiceAccountDialog(Activity context) {
        super(context);
    }

    public void setListener(AccountChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public int initLayoutId() {
        return R.layout.dialog_choice_account;
    }

    @Override
    public float initWidthPercent() {
        return 1.0f;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.exit).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.ll_cash).setOnClickListener(this);
        view.findViewById(R.id.ll_bank_card).setOnClickListener(this);
        view.findViewById(R.id.ll_credit_card).setOnClickListener(this);
        view.findViewById(R.id.ll_wechat).setOnClickListener(this);
        view.findViewById(R.id.ll_alipay).setOnClickListener(this);
        view.findViewById(R.id.ll_hb).setOnClickListener(this);
        view.findViewById(R.id.ll_jb).setOnClickListener(this);
        view.findViewById(R.id.ll_jd).setOnClickListener(this);
        bankExpandImg = view.findViewById(R.id.iv_expand_bank_card);
        creditExpandImg = view.findViewById(R.id.iv_expand_credit_card);
        bankView = view.findViewById(R.id.ll_bank_desc);
        creditView = view.findViewById(R.id.ll_credit_desc);
        rvBankCard=view.findViewById(R.id.rv_info_card_bank);
        rvCreditCard=view.findViewById(R.id.rv_info_card_credit);
        banEmptyView=view.findViewById(R.id.ll_bank_empty);
        creditEmptyView=view.findViewById(R.id.ll_credit_empty);
        view.findViewById(R.id.tv_add_bank_card).setOnClickListener(v->{
            Intent intent=new Intent(activity, AddCardActivity.class);
            intent.putExtra("action", CommonUtils.ACTION_ADD_BANK_CARD);
            activity.startActivityForResult(intent,101);
        });
        view.findViewById(R.id.tv_add_credit_card).setOnClickListener(v->{
            Intent intent=new Intent(activity, AddCardActivity.class);
            intent.putExtra("action",CommonUtils.ACTION_ADD_CREDIT_CARD);
            activity.startActivityForResult(intent,101);
        });
        if (MyApplication.cash.isDefault()){
            view.findViewById(R.id.tv_cash_default).setVisibility(View.VISIBLE);
        }else if (MyApplication.wechat.isDefault()){
            view.findViewById(R.id.tv_wechat_default).setVisibility(View.VISIBLE);
        }else if (MyApplication.alipay.isDefault()){
            view.findViewById(R.id.tv_alipay_default).setVisibility(View.VISIBLE);
        }else if (MyApplication.hb.isDefault()){
            view.findViewById(R.id.tv_hb_default).setVisibility(View.VISIBLE);
        }else if (MyApplication.jb.isDefault()){
            view.findViewById(R.id.tv_jb_default).setVisibility(View.VISIBLE);
        }else if (MyApplication.jd.isDefault()){
            view.findViewById(R.id.tv_jd_default).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        rvBankCard.setLayoutManager(new LinearLayoutManager(getActivity()));
        bankAdapter=new BillPayAdapter(SPUtils.getBankWay());
        rvBankCard.setAdapter(bankAdapter);
        if (bankAdapter.getPayWays().size()==0){
            banEmptyView.setVisibility(View.VISIBLE);
            rvBankCard.setVisibility(View.GONE);
        }else {
            banEmptyView.setVisibility(View.GONE);
            rvBankCard.setVisibility(View.VISIBLE);
        }

        rvCreditCard.setLayoutManager(new LinearLayoutManager(getActivity()));
        creditAdapter=new BillPayAdapter(SPUtils.getCreditWay());
        rvCreditCard.setAdapter(creditAdapter);
        if (creditAdapter.getPayWays().size()==0){
            creditEmptyView.setVisibility(View.VISIBLE);
            rvCreditCard.setVisibility(View.GONE);
        }else {
            creditEmptyView.setVisibility(View.GONE);
            rvCreditCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        bankAdapter.setListener(position -> returnPayWay(bankAdapter.getPayWays().get(position)));
        creditAdapter.setListener(position -> returnPayWay(creditAdapter.getPayWays().get(position)));
    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cash:
                returnPayWay(MyApplication.cash);
                break;
            case R.id.ll_bank_card:
                if (bankExpand) {
                    bankExpandImg.setImageDrawable(getActivity().getDrawable(R.drawable.right));
                    bankView.setVisibility(View.GONE);
                } else {
                    bankExpandImg.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrowdown));
                    bankView.setVisibility(View.VISIBLE);
                }
                bankExpand = !bankExpand;
                break;
            case R.id.ll_credit_card:
                if (creditExpand) {
                    creditExpandImg.setImageDrawable(getActivity().getDrawable(R.drawable.right));
                    creditView.setVisibility(View.GONE);
                } else {
                    creditExpandImg.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrowdown));
                    creditView.setVisibility(View.VISIBLE);
                }
                creditExpand = !creditExpand;
                break;
            case R.id.ll_wechat:
                returnPayWay(MyApplication.wechat);
                break;
            case R.id.ll_alipay:
                returnPayWay(MyApplication.alipay);
                break;
            case R.id.ll_hb:
                returnPayWay(MyApplication.hb);
                break;
            case R.id.ll_jb:
                returnPayWay(MyApplication.jb);
                break;
            case R.id.ll_jd:
                returnPayWay(MyApplication.jd);
                break;
        }
    }

    private void returnPayWay(PayWay payWay) {
        if (listener != null) {
            listener.onAccountChanged(payWay);
            dismiss();
        }
    }

    public interface AccountChangedListener {
        void onAccountChanged(PayWay payWay);
    }
}
