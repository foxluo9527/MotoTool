package com.motoll.one.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.motoll.one.R;
import com.motoll.one.data.PayWay;

import java.util.ArrayList;

public class BillPayAdapter extends RecyclerView.Adapter<BillPayAdapter.PayViewHolder> {
    private ArrayList<PayWay> payWays;
    private PayWayOnclickListener listener;
    public BillPayAdapter(ArrayList<PayWay> payWays) {
        this.payWays = payWays;
    }

    public ArrayList<PayWay> getPayWays() {
        return payWays;
    }

    public void setListener(PayWayOnclickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_select,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PayViewHolder holder, int position) {
        PayWay payWay=payWays.get(position);
        holder.defaultView.setVisibility(payWay.isDefault()?View.VISIBLE:View.GONE);
        holder.number.setText(payWay.getNumber());
        holder.name.setText(payWay.getName());
        holder.type.setText(payWay.getType());
        holder.itemView.setOnClickListener(v->{
            if (listener!=null){
                listener.onWayClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return payWays.size();
    }

    public static class PayViewHolder extends RecyclerView.ViewHolder{
        private TextView name,number,type;
        private View defaultView;
        public PayViewHolder(@NonNull View view) {
            super(view);
            name= view.findViewById(R.id.tv_card_name);
            number=view.findViewById(R.id.tv_card_number);
            type=view.findViewById(R.id.tv_card_type);
            defaultView=view.findViewById(R.id.v_default);
        }
    }
    public interface PayWayOnclickListener{
        void onWayClick(int position);
    }
}
