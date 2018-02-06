package com.cjw.rhclient.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cjw.rhclient.utils.UI;


public class BaseCustomDialog extends Dialog {
	private Context context;
	private int height, width;
	private boolean cancelTouchOut;
	private boolean backCancelable;
	private View view;

	private BaseCustomDialog(Builder builder) {
		super(builder.context);
		this.context = builder.context;
		this.height = builder.height;
		this.width = builder.width;
		this.cancelTouchOut = builder.cancelTouchOut;
		this.backCancelable = builder.backCancelable;
		this.view = builder.view;
	}


	private BaseCustomDialog(Builder builder, int resStyle) {
		super(builder.context, resStyle);
		this.context = builder.context;
		this.height = builder.height;
		this.width = builder.width;
		this.cancelTouchOut = builder.cancelTouchOut;
		this.backCancelable = builder.backCancelable;
		this.view = builder.view;
	}

	public void showDialog() {
		if (this.isShowing()) dismiss();
		this.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
		View divider = findViewById(divierId);
		divider.setBackgroundColor(Color.TRANSPARENT);

		setContentView(view);
		setCanceledOnTouchOutside(cancelTouchOut);
		setCancelable(backCancelable);

		Window window = getWindow();
		assert window != null;
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		params.height = height;
		params.width = width;
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		window.setAttributes(params);
	}

	public View getView(int viewRes) {
		return view.findViewById(viewRes);
	}

	public static final class Builder {

		private Context context;
		private int height, width;
		private boolean cancelTouchOut;
		private boolean backCancelable;
		private View view;
		private int resStyle = -1;


		public Builder(Context context) {
			this.context = context;
		}

		public Builder view(int resId) {
			view = LayoutInflater.from(context).inflate(resId, null);
			return this;
		}

		public Builder view(View view) {
			this.view = view;
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

		public Builder heightDimenRes(int dimenRes) {
			height = context.getResources().getDimensionPixelOffset(dimenRes);
			return this;
		}

		public Builder widthDimenRes(int dimenRes) {
			width = context.getResources().getDimensionPixelOffset(dimenRes);
			return this;
		}

		public Builder style(int resStyle) {
			this.resStyle = resStyle;
			return this;
		}

		public Builder isTouchOutCancel(boolean val) {
			cancelTouchOut = val;
			return this;
		}

		public Builder isBackCancelable(boolean val) {
			backCancelable = val;
			return this;
		}

		public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
			if (listener != null) view.findViewById(viewRes).setOnClickListener(listener);
			return this;
		}


		public BaseCustomDialog build() {
			if (resStyle != -1) {
				return new BaseCustomDialog(this, resStyle);
			} else {
				return new BaseCustomDialog(this);
			}
		}
	}
}
