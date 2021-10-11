package com.motoll.one.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.motoll.one.R;
import com.motoll.one.data.Bill;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private Context context;
    private BillActionListener listener;
    private ArrayList<Bill> bills;

    public interface BillActionListener {
        void onItemClick(int position);

        void onBillSEdit(int position);

        void onBillDelete(int position);
    }

    public void setBillActionListener(BillActionListener listener) {
        this.listener = listener;
    }

    public BillAdapter(Context context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.date.setText(bill.getDate());
        holder.name.setText(bill.getType() + "账单-" + bill.getName());
        if (bill.getPrice() > 0) {
            holder.price.setText("+" + bill.getPrice());
            holder.price.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.price.setText("-" + bill.getPrice());
            holder.price.setTextColor(Color.parseColor("#2FAF2F"));
        }
        holder.edit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBillSEdit(position);
            }
        });
        holder.del.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBillDelete(position);
            }
        });
        holder.item.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder {
        View edit, del, item;
        TextView price, date;
        EditText name;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            edit = itemView.findViewById(R.id.tv_edit);
            del = itemView.findViewById(R.id.tv_delete);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
