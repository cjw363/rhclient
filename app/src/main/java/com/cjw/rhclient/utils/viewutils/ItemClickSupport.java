package com.cjw.rhclient.utils.viewutils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjw.rhclient.R;

public class ItemClickSupport {
	private final RecyclerView mRecyclerView;
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mOnItemClickListener != null) {
				RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
				mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
			}
		}
	};
	private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (mOnItemLongClickListener != null) {
				RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
				return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
			}
			return false;
		}
	};
	private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
		@Override
		public void onChildViewAttachedToWindow(View view) {
			if (mOnItemClickListener != null) {
				view.setOnClickListener(mOnClickListener);
			}
			if (mOnItemLongClickListener != null) {
				view.setOnLongClickListener(mOnLongClickListener);
			}
		}

		@Override
		public void onChildViewDetachedFromWindow(View view) {

		}
	};

	private ItemClickSupport(RecyclerView recyclerView) {
		mRecyclerView = recyclerView;
		mRecyclerView.setTag(R.id.item_click_support, this);
		mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
	}

	public static ItemClickSupport addTo(RecyclerView view) {
		ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
		if (support == null) {
			support = new ItemClickSupport(view);
		}
		return support;
	}

	public static ItemClickSupport removeFrom(RecyclerView view) {
		ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
		if (support != null) {
			support.detach(view);
		}
		return support;
	}

	public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
		return this;
	}

	public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
		mOnItemLongClickListener = listener;
		return this;
	}

	private void detach(RecyclerView view) {
		view.removeOnChildAttachStateChangeListener(mAttachListener);
		view.setTag(R.id.item_click_support, null);
	}

	public interface OnItemClickListener {

		void onItemClicked(RecyclerView recyclerView, int position, View v);
	}

	/**
	 * 长按的时候会先调用onItemLongClicked，然后再调用onItemClicked方法，也就是先长按后单击；如果这个时候不想调用单击事件，长按回调方法onItemLongClicked里返回true
	 */
	public interface OnItemLongClickListener {

		boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
	}
}
