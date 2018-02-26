package com.cjw.rhclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;

public class DrawableUtils {

	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 2;

	//系统数据库存放图片的路径
	private static final Uri STORAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

	//获取一个shape对象
	public static GradientDrawable getGradientDrawable(int color, int radius) {
		// xml中定义的shape标签 对应此类
		GradientDrawable shape = new GradientDrawable();
		shape.setShape(GradientDrawable.RECTANGLE);// 矩形
		shape.setCornerRadius(radius);// 圆角半径
		shape.setColor(color);// 颜色

		return shape;
	}

	//获取状态选择器
	public static StateListDrawable getSelector(Drawable normal, Drawable press) {
		StateListDrawable selector = new StateListDrawable();
		selector.addState(new int[]{android.R.attr.state_pressed}, press);// 按下图片
		selector.addState(new int[]{}, normal);// 默认图片

		return selector;
	}

	//获取状态选择器
	public static StateListDrawable getSelector(int normal, int press, int radius) {
		GradientDrawable bgNormal = getGradientDrawable(normal, radius);
		GradientDrawable bgPress = getGradientDrawable(press, radius);
		StateListDrawable selector = getSelector(bgNormal, bgPress);
		return selector;
	}

	public static Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable == null) {
			return null;
		}

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		try {
			Bitmap bitmap;

			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
		Drawable drawable = ContextCompat.getDrawable(context, drawableId);

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);

			return bitmap;
		} else {
			throw new IllegalArgumentException("unsupported drawable type");
		}
	}
}
