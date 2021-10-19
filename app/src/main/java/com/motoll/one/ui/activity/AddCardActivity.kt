package com.motoll.one.ui.activity

import com.motoll.one.R
import com.motoll.one.ui.BaseActivity

class AddCardActivity : BaseActivity() {

    override fun initLayoutId(): Int {
        return R.layout.activity_add_card
    }

    override fun initListener() {

    }

    override fun initData() {
        setStatusBarColor(R.color.green)
        setBarTextColorIsDark(true)
    }

    override fun initFullScreen(): Boolean {
        return false
    }
}