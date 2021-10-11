package com.motoll.one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.motoll.one.R;
import com.motoll.one.common.CommonUtils;

public class CarsAdapter extends RecyclerView.Adapter {
    private Context context;
    private CarSelectListener listener;

    public interface CarSelectListener {
        void onCarSelect(int position);
    }

    public void setCarSelectListener(CarSelectListener listener) {
        this.listener = listener;
    }

    public CarsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(context)
                .load(CommonUtils.getInstance().getSelectCars().get(position))
                .into((ImageView) holder.itemView);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onCarSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CommonUtils.getInstance().getSelectCars().size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
