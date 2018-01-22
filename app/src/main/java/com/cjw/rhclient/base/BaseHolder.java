package com.cjw.rhclient.base;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @param <T>表示list集合里面单个item对象
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

	private BaseRecyclerViewAdapter.OnItemClickListener<T> mItemClickListener;
	private T itemData;

	public BaseHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public BaseHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<T> itemClickListener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.mItemClickListener = itemClickListener;
		itemView.setOnClickListener(this);
	}

	/**
	 * 把数据传入，并刷新数据更新UI
	 */
	protected void loadData(T itemData) {
		this.itemData = itemData;
		refreshData(itemData);
	}

	public T getData() {
		return itemData;
	}

	public abstract void refreshData(T data);//交由子类由具体更新UI

	@Override
	public void onClick(View v) {
		if (mItemClickListener != null) mItemClickListener.onItemClick(v, getPosition(), itemData);
	}
}
