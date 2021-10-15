package com.motoll.one.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.motoll.one.R;
import com.motoll.one.data.Bill;
import com.motoll.one.ui.view.pinned.PinnedHeaderAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class HomeBillAdapter extends PinnedHeaderAdapter<RecyclerView.ViewHolder> {

	private static final int VIEW_TYPE_ITEM_TIME    = 0;
	private static final int VIEW_TYPE_ITEM_CONTENT = 1;

	private List<Bill> mDataList;

	public HomeBillAdapter() {
		this(null);
	}

	public HomeBillAdapter(List<Bill> dataList) {
		mDataList = dataList;
	}

	public void setData(List<Bill> dataList) {
		mDataList = dataList;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == VIEW_TYPE_ITEM_TIME) {
			return new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_group, parent, false));
		} else {
			return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_content, parent, false));
		}
	}

	@SuppressLint({"SimpleDateFormat", "SetTextI18n"})
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
			TitleHolder titleHolder = (TitleHolder) holder;
			titleHolder.mTextTitle.setText(new SimpleDateFormat("MM月dd日 yyyy").format(mDataList.get(position).getTime()));
		} else {
			ContentHolder contentHolder = (ContentHolder) holder;
			contentHolder.tv_type.setText(mDataList.get(position).getType());
			contentHolder.tv_time.setText(new SimpleDateFormat("HH:mm").format(mDataList.get(position).getTime()));
			contentHolder.tv_account.setText(mDataList.get(position).getAccount_name());
			if (mDataList.get(position).getPrice()>0){
				contentHolder.tv_money.setText("+￥ "+mDataList.get(position).getPrice());
				contentHolder.tv_money.setTextColor(Color.parseColor("#FF5252"));
			}else {
				contentHolder.tv_money.setText("-￥ "+mDataList.get(position).getPrice());
				contentHolder.tv_money.setTextColor(Color.parseColor("#41AA45"));
			}
		}
	}

	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (mDataList.get(position).getPrice() == 0) {
			return VIEW_TYPE_ITEM_TIME;
		} else {
			return VIEW_TYPE_ITEM_CONTENT;
		}
	}

	@Override
	public boolean isPinnedPosition(int position) {
		return getItemViewType(position) == VIEW_TYPE_ITEM_TIME;
	}

	static class ContentHolder extends RecyclerView.ViewHolder {

		TextView tv_type,tv_time,tv_money,tv_account;

		ContentHolder(View itemView) {
			super(itemView);
			tv_type = itemView.findViewById(R.id.tv_type);
			tv_time = itemView.findViewById(R.id.tv_time);
			tv_money = itemView.findViewById(R.id.tv_money);
			tv_account = itemView.findViewById(R.id.tv_account);
		}
	}

	static class TitleHolder extends RecyclerView.ViewHolder {

		TextView mTextTitle;

		TitleHolder(View itemView) {
			super(itemView);
			mTextTitle = itemView.findViewById(R.id.tv_group);
		}
	}

}