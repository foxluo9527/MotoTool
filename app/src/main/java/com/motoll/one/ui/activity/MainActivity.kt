package com.motoll.one.ui.activity

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.jpeng.jptabbar.OnTabSelectListener
import com.jpeng.jptabbar.anno.NorIcons
import com.jpeng.jptabbar.anno.SeleIcons
import com.jpeng.jptabbar.anno.Titles
import com.motoll.one.R
import com.motoll.one.common.CommonUtils
import com.motoll.one.ui.BaseActivity
import com.motoll.one.ui.adapter.TabPageAdapter
import com.motoll.one.ui.dialog.AddBillDialog
import com.motoll.one.ui.fragment.BagFragment
import com.motoll.one.ui.fragment.HomeFragment
import com.motoll.one.ui.fragment.MineFragment
import com.motoll.one.ui.fragment.StatisFragment
import com.xuexiang.xui.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val tabPageAdapter=TabPageAdapter(supportFragmentManager)
    private val fragments = ArrayList<Fragment>()
    private var addBillDialog: AddBillDialog? = null

    @Titles
    val mTitles = arrayOf("首页", "统计", "钱包", "我的")

    @SeleIcons
    val mSelectIcons = intArrayOf(
        R.drawable.tab_ic_hm_nor,
        R.drawable.tab_ic_tj_nor,
        R.drawable.tab_ic_zc_nor,
        R.drawable.tab_ic_mn_nor
    )

    @NorIcons
    val mNormalIcons = intArrayOf(
        R.drawable.tab_ic_hm_nor,
        R.drawable.tab_ic_tj_nor,
        R.drawable.tab_ic_zc_nor,
        R.drawable.tab_ic_mn_nor
    )
    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initListener() {

    }

    override fun initData() {
        setStatusBarColor(R.color.white)
        val layoutParams=view_pager.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin=CommonUtils.dipToPx(this, StatusBarUtils.getStatusBarHeight(this)*1.0f)
        view_pager.layoutParams=layoutParams
        setBarTextColorIsDark(true)
        val params = tabbar.middleView.layoutParams as FrameLayout.LayoutParams
        params.bottomMargin = CommonUtils.dipToPx(this, 11f)
        tabbar.middleView.layoutParams = params
        fragments.add(HomeFragment())
        fragments.add(StatisFragment())
        fragments.add(BagFragment())
        fragments.add(MineFragment())
        tabPageAdapter.setFragments(fragments)
        view_pager.adapter=tabPageAdapter
        tabbar.setContainer(view_pager)
        tabbar.middleView.setOnClickListener {
            if (addBillDialog == null)
                addBillDialog = AddBillDialog(this)
            else
                addBillDialog?.show()
        }
        tabbar.setGradientEnable(false)
        tabbar.setPageAnimateEnable(false)
        tabbar.setTabListener(object : OnTabSelectListener{
            override fun onTabSelect(index: Int) {
                view_pager.setCurrentItem(index,false)
            }

            override fun onInterruptSelect(index: Int): Boolean {
                return false
            }
        })
    }

    override fun initFullScreen(): Boolean {
        return false
    }

}