package com.cjw.rhclient.view.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.utils.UI;

public class ContentDialog {
	public static final class Builder {

		private Context context;
		private int height = UI.dip2px(120), width = UI.dip2px(140);
		private View view;
		private String title;
		private String content;
		private TextView mTvTitle;
		private TextView mTvContent;
		private RelativeLayout mRlContent;
		private View.OnClickListener cancelListener;
		private View.OnClickListener okListener;
		private final RelativeLayout.LayoutParams mParams;

		public Builder(Context context) {
			this.context = context;
			view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_common, null);
			mTvTitle = (TextView) view.findViewById(R.id.title);
			mRlContent = (RelativeLayout) view.findViewById(R.id.content);

			mTvContent = new TextView(context);
			mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			mTvContent.setTextColor(UI.getColor(R.color.tv_colorPrimary));
			mTvContent.setMaxLines(4);
			mTvContent.setGravity(Gravity.CENTER);
			mRlContent.addView(mTvContent);

			mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//			mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		}

		public Builder contentView(int resId) {
			View contentView = LayoutInflater.from(context).inflate(resId, null);
			contentView.setLayoutParams(mParams);
			mRlContent.removeAllViews();
			mRlContent.addView(contentView);
			return this;
		}

		public Builder contentView(View contentView) {
			contentView.setLayoutParams(mParams);
			mRlContent.removeAllViews();
			mRlContent.addView(contentView);
			return this;
		}

		public Builder height(int val) {
			height = UI.dip2px(val);
			return this;
		}

		public Builder width(int val) {
			width = UI.dip2px(val);
			return this;
		}

		public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
			view.findViewById(viewRes).setOnClickListener(listener);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			mTvTitle.setText(title);
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			mTvContent.setText(content);
			return this;
		}

		public Builder setCancelListener(View.OnClickListener listener) {
			this.cancelListener = listener;
			return this;
		}

		public Builder setOkListener(View.OnClickListener listener) {
			this.okListener = listener;
			return this;
		}

		public Builder setOkListener(String btName, View.OnClickListener listener) {
			((TextView) view.findViewById(R.id.ok)).setText(btName);
			this.okListener = listener;
			return this;
		}

		public Builder setSingleButton() {
			view.findViewById(R.id.cancel).setVisibility(View.GONE);
			return this;
		}

		public BaseCustomDialog build() {
			final BaseCustomDialog dialog = new BaseCustomDialog.Builder(context).height(height)
			  .width(width)
			  .isBackCancelable(true)
			  .isTouchOutCancel(false)
			  .view(view)
			  .addViewOnclick(R.id.cancel, cancelListener)
			  .addViewOnclick(R.id.ok, okListener)
			  .build();

			if (cancelListener == null) {
				cancelListener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				};
				view.findViewById(R.id.cancel).setOnClickListener(cancelListener);
			}
			if (okListener == null) {
				okListener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				};
				view.findViewById(R.id.ok).setOnClickListener(okListener);
			}
			return dialog;
		}
	}
}
