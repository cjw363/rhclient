package com.cjw.rhclient.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

	private static final int SPAN_COUNT = 2;
	private int mSpace;

	public SpaceItemDecoration(int space) {
		this.mSpace = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

		if (parent.getChildPosition(view) != 0)
			//outRect.top = mSpace;
		outRect.left=mSpace;
		outRect.right=mSpace;
		outRect.bottom=mSpace;

//		outRect.set(mSpace / 2, 0, mSpace / 2, 0);
//		// 从第二行开始 top = mSpace
//		if (pos >= SPAN_COUNT) {
//			outRect.top = mSpace;
//		} else {
//			outRect.top = 0;
//		}
	}
}
