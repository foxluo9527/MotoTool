package com.motoll.one.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.motoll.one.R;
import com.motoll.one.ui.BaseDialog;
import com.motoll.one.ui.adapter.BillMemberAdapter;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;

public class ChoiceMemberDialog extends BaseDialog {
    private ArrayList<String> members;
    private BillMemberAdapter adapter;
    private MemberChangedListener listener;
    private RecyclerView rv_info_member;
    private TextView tvOk;
    private EditText input;
    private int onEditPosition = 0;

    public ChoiceMemberDialog(Activity context, Bundle data) {
        super(context, data);
    }

    public void setListener(MemberChangedListener listener) {
        this.listener = listener;
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
        view.findViewById(R.id.exit).setOnClickListener(v -> dismiss());
        rv_info_member = view.findViewById(R.id.rv_info_member);
        tvOk = view.findViewById(R.id.tv_ok);
        input = view.findViewById(R.id.et_input);
    }

    @Override
    public void initData() {
        String members = getData().getString("members");
        this.members = new ArrayList<>();
        this.members.add("新建");
        if (members.length() > 0) {
            Collections.addAll(this.members, members.split("\\|"));
        }
        adapter = new BillMemberAdapter(this.members);
        rv_info_member.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rv_info_member.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        tvOk.setOnClickListener(v -> {
            if (onEditPosition == 0) {
                new AlertDialog.Builder(activity)
                        .setMessage("确认添加此成员?")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            String content = input.getText().toString();
                            if (content.contains("|")){
                                ToastUtils.toast("名称不能包含符号[]|/*");
                                return;
                            }
                            members.add(content);
                            adapter.notifyItemInserted(members.size() - 1);
                            input.setText("");
                            returnMembers();
                            dialog.dismiss();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();
            } else {
                new AlertDialog.Builder(activity)
                        .setMessage("确认修改此成员信息")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            String content = input.getText().toString();
                            if (content.contains("|")){
                                ToastUtils.toast("名称不能包含符号[]|/*");
                                return;
                            }
                            members.set(onEditPosition, content);
                            adapter.notifyItemChanged(onEditPosition);
                            returnMembers();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();
            }
        });
        adapter.setListener(new BillMemberAdapter.MemberClickListener() {
            @Override
            public void onItemClick(int position) {
                onEditPosition = position;
                if (position == 0) {
                    input.setText("");
                    tvOk.setText("添加");
                } else {
                    input.setText(members.get(position));
                    tvOk.setText("修改");
                }
            }

            @Override
            public void onItemDelete(int position) {
                new AlertDialog.Builder(activity)
                        .setMessage("是否删除此成员信息")
                        .setPositiveButton("是", (dialog, which) -> {
                            members.remove(position);
                            adapter.notifyItemRemoved(position);
                            returnMembers();
                            input.setText("");
                            dialog.dismiss();
                        })
                        .setNegativeButton("否", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();
            }
        });
    }

    private void returnMembers() {
        if (listener != null) {
            String memberString = "";
            for (int i = 1; i < members.size(); i++) {
                memberString += members.get(i);
                if (i != members.size() - 1)
                    memberString += "|";
            }
            listener.onMemberChanged(memberString);
        }
    }

    public interface MemberChangedListener {
        void onMemberChanged(String members);
    }
}
