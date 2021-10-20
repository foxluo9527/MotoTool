package com.motoll.one.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import com.motoll.one.MyApplication
import com.motoll.one.R
import com.motoll.one.common.CommonUtils
import com.motoll.one.common.CommonUtils.ACTION_ADD_BANK_CARD
import com.motoll.one.common.CommonUtils.ACTION_ADD_CREDIT_CARD
import com.motoll.one.common.SPUtils
import com.motoll.one.data.PayWay
import com.motoll.one.ui.BaseActivity
import com.xuexiang.xui.XUI
import com.xuexiang.xui.adapter.simple.AdapterItem
import com.xuexiang.xui.utils.KeyboardUtils
import com.xuexiang.xui.utils.StatusBarUtils
import com.xuexiang.xui.widget.spinner.editspinner.EditSpinnerAdapter
import com.xuexiang.xutil.tip.ToastUtils
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : BaseActivity() {
    private var action = ""
    private var isEdited = false
    private var payWay: PayWay? = null
    override fun initLayoutId(): Int {
        return R.layout.activity_add_card
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        XUI.initTheme(this)
        super.onCreate(savedInstanceState)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            if (isEdited)
                AlertDialog.Builder(this)
                    .setMessage("正在编辑的内容还未保存，是否退出?")
                    .setPositiveButton("是") { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                        finish()
                    }
                    .setNegativeButton(
                        "否"
                    ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                    .setCancelable(false)
                    .show()
            else
                finish()
        }
        if (action == ACTION_ADD_BANK_CARD || action == ACTION_ADD_CREDIT_CARD) {
            et_input_number.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    isEdited = true
                }
            })
            es_card_name.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    isEdited = true
                }
            })
        }
        et_input_money.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                isEdited = true
            }
        })
        tv_ok.setOnClickListener {
            when (action) {
                ACTION_ADD_BANK_CARD, ACTION_ADD_CREDIT_CARD -> {
                    //添加卡
                    payWay = PayWay()
                    if (action == ACTION_ADD_BANK_CARD)
                        payWay!!.type = "银行卡"
                    else
                        payWay!!.type = "信用卡"
                    payWay!!.isDefault=cb_default.isChecked
                    payWay!!.name=es_card_name.text
                    payWay!!.number=et_input_number.text.toString()
                    payWay!!.money=et_input_money.text.toString().toDouble()
                    if (SPUtils.addPayCard(payWay)){
                        setResult(RESULT_OK)
                        ToastUtils.toast("添加成功")
                        finish()
                    }else{
                        ToastUtils.toast("此卡号已添加，请修改后重新添加")
                    }
                }
                else -> {
                    //修改支付方式
                    payWay!!.money=et_input_money.text.toString().toDouble()
                    payWay!!.isDefault=cb_default.isChecked
                    if (SPUtils.editPayWay(payWay)){
                        MyApplication.initCards()
                        setResult(RESULT_OK)
                        ToastUtils.toast("修改成功")
                        isEdited=false
                    }else{
                        ToastUtils.toast("修改失败")
                    }
                }
            }
        }
    }

    override fun initData() {
        setStatusBarColor(R.color.green)
        setBarTextColorIsDark(true)
        val params = rl_top.layoutParams as LinearLayout.LayoutParams
        params.topMargin = StatusBarUtils.getStatusBarHeight(this)
        rl_top.layoutParams = params
        KeyboardUtils.setSoftInputAdjustResize(this)
        es_card_name.setAdapter(
            EditSpinnerAdapter(getList()).setTextColor(Color.BLACK)
                .setTextSize(es_card_name.editText.textSize)
        )
        action = intent.getStringExtra("action").toString()
        when (action) {
            ACTION_ADD_BANK_CARD -> {
                tv_title.text = "添加银行卡"
                ctv_bank_card.isChecked = true
            }
            ACTION_ADD_CREDIT_CARD -> {
                tv_title.text = "添加信用卡"
                ctv_credit_Card.isChecked = true
            }
            else -> {
                //编辑支付方式
                payWay = intent.getSerializableExtra("payWay") as PayWay
                if (payWay!!.type.equals("银行卡") || payWay!!.type.equals("信用卡")) {
                    es_card_name.text = payWay!!.name
                    et_input_number.setText(payWay!!.number)
                    es_card_name.isEnabled = false
                    et_input_number.isEnabled = false
                } else {
                    ll_input_name.visibility = View.GONE
                    ll_input_number.visibility = View.GONE
                }
                cb_default.isChecked=payWay!!.isDefault
                when (payWay!!.type) {
                    "现金" -> ctv_cash.isChecked = true
                    "银行卡" -> ctv_bank_card.isChecked = true
                    "信用卡" -> ctv_credit_Card.isChecked = true
                    "微信" -> ctv_wechat.isChecked = true
                    "支付宝" -> ctv_alipay.isChecked = true
                    "花呗" -> ctv_hb.isChecked = true
                    "借呗" -> ctv_jb.isChecked = true
                    "京东白条" -> ctv_jd.isChecked = true
                }
                et_input_money.setText(payWay!!.money.toString())
            }
        }
    }

    private fun getList(): ArrayList<AdapterItem> {
        val list = ArrayList<AdapterItem>()
        for (bank in CommonUtils.getBanks(this)) {
            list.add(AdapterItem(bank))
        }
        return list
    }

    override fun initFullScreen(): Boolean {
        return false
    }
}