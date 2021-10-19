package com.motoll.one.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.motoll.one.R;

import java.util.ArrayList;

public class BillMemberAdapter extends RecyclerView.Adapter<BillMemberAdapter.MemberViewHolder> {
    private ArrayList<String> members;
    private MemberClickListener listener;
    public BillMemberAdapter(ArrayList<String> members){
        this.members=members;
    }

    public void setListener(MemberClickListener listener) {
        this.listener = listener;
    }

    public interface MemberClickListener{
        void onItemClick(int position);
        void onItemDelete(int position);
    }
    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_member,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.content.setText(members.get(position));
        holder.del.setVisibility(position==0?View.GONE:View.VISIBLE);
        holder.itemView.setOnClickListener(v->{
            if (listener!=null)
                listener.onItemClick(position);
        });
        holder.del.setOnClickListener(v->{
            if (listener!=null&&position!=0){
                listener.onItemDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private ImageView del;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.tv_content);
            del=itemView.findViewById(R.id.iv_del);
        }
    }
}
