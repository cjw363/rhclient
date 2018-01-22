package com.cjw.rhclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishTypeContentView extends FrameLayout {

	@BindView(R.id.tv_title)
	TextView mTvTitle;
	@BindView(R.id.tv_content)
	TextView mTvContent;
	private String mTitle;
	private String mContent;
	private String mHint;

	public PublishTypeContentView(Context context) {
		this(context, null);
	}

	public PublishTypeContentView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PublishTypeContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View view = UI.inflate(R.layout.layout_publish_type);
		ButterKnife.bind(this, view);
		addView(view);

		// 当自定义属性时, 系统会自动生成属性相关id, 此id通过R.styleable来引用
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PublishTypeContentView);
		// id = 属性名_具体属性字段名称 (此id系统自动生成)
		mTitle = typedArray.getString(R.styleable.PublishTypeContentView_title);
		mContent = typedArray.getString(R.styleable.PublishTypeContentView_content);
		mHint = typedArray.getString(R.styleable.PublishTypeContentView_hint);
		typedArray.recycle();// 回收typearray, 提高性能

		mTvTitle.setText(mTitle);
		mTvContent.setText(mContent);
	}

	public void setTitle(String title) {
		this.mTitle = title;
		mTvTitle.setText(mTitle);
	}

	public void setContent(String content) {
		this.mContent = content;
		mTvContent.setText(mContent);
	}

	public void setHint(String hint) {
		this.mHint = hint;
	}

}
