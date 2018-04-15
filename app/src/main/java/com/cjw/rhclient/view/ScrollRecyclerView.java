package com.cjw.rhclient.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ScrollRecyclerView extends RecyclerView {
	public ScrollRecyclerView(Context context) {
		super(context);
	}

	public ScrollRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, // 设计一个较大的值和AT_MOST模式
		  MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);//再调用原方法测量
	}
}

