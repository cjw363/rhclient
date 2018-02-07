package com.cjw.rhclient.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.holder.MoreHolder;
import com.cjw.rhclient.utils.UI;

import java.util.List;

/**
 * @param <T>表示传入的数据为未知
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

	private static final int TYPE_DEFAULT_ITEM = 1;// 表示默认的布局的类型
	private static final int TYPE_FOOTER_ITEM = 0;// 表示加载更多的布局的类型
	public final Context mContext;

	public List<T> data;

	public BaseRecyclerViewAdapter(Context context, List<T> data) {
		// 通过构造方法把数据传入进来
		this.data = data;
		this.mContext=context;
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getItemCount() - 1) {
			// 滑动到最后一条目
			return TYPE_FOOTER_ITEM;
		}
		return getInnerType(position);// 普通布局
	}

	// 子类可以重写方法来更改返回的布局类型
	public int getInnerType(int position) {
		return TYPE_DEFAULT_ITEM;
	}

	@Override
	public void onBindViewHolder(BaseHolder<T> viewHolder, int position) {
		if (viewHolder instanceof MoreHolder) {
			moreHolder = (MoreHolder) viewHolder;
			if (moreHolder.getData() == MoreHolder.STATE_MORE) {
				// 只有在加载更多的状态下才加载更多
				loadMoreData(moreHolder);
			}
		} else {
			viewHolder.loadData(data.get(position));// 刷新数据ui
		}
	}

	@Override
	public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_FOOTER_ITEM) {
			View moreView = LayoutInflater.from(UI.getContext()).inflate(R.layout.layout_load_more, parent, false);
			return new MoreHolder(moreView, isHasMore());
		} else {
			return getHolder(initHolderView(parent, viewType), itemClickListener, viewType);
		}
	}

	private boolean isLoadMore = false;// 标记是否正在加载更多

	private void loadMoreData(final MoreHolder moreHolder) {
		if (!isLoadMore) {
			isLoadMore = true;// 正在加载更多
			onLoadMore();
		}
	}

	/**
	 * 根据网络加载后的结果，更新更多数据,子类调用
	 */
	public void updateMoreData(final List<T> moreData) {
		// 在主线程中更新UI
		if (moreData != null) {
			if (moreData.size() < 9) {// 一次加载9条数据
				moreHolder.loadData(MoreHolder.STATE_NONE);// 没有更多数据
				UI.showToast("没有更多数据了");
			} else {
				moreHolder.loadData(MoreHolder.STATE_MORE);
			}

			// 将更多数据追加到当前集合中
			data.addAll(moreData);
			// 刷新界面
			BaseRecyclerViewAdapter.this.notifyDataSetChanged();
		} else {
			// 加载失败
			moreHolder.loadData(MoreHolder.STATE_FAIL);
		}

		isLoadMore = false;// 加载结束
	}

	// 子类可以重写
	public boolean isHasMore() {
		return true;
	}

	public abstract void onLoadMore();// 子类去加载更多数据,已经运行在子线程中

	public abstract View initHolderView(ViewGroup parent, int viewType);// holder的布局

	public abstract BaseHolder<T> getHolder(View initHolderView, OnItemClickListener<T> itemClickListener, int viewType);// 子类生成对应holder

	public OnItemClickListener<T> itemClickListener;

	private MoreHolder moreHolder;

	public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	//创建一个点击事件的接口
	public interface OnItemClickListener<T> {
		void onItemClick(View view, int position, T data);
	}

}
