package com.cjw.rhclient.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cjw.rhclient.R;
import com.cjw.rhclient.utils.UI;


public class MyDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Window window = getDialog().getWindow();
		assert window != null;
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().setCanceledOnTouchOutside(true);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		View view = inflater.inflate(R.layout.fragment_test, ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
		window.setLayout(300, 200);//这2行,和上面的一样,注意顺序就行;
		return view;
	}

	private static MyDialogFragment dialog;

	public static void show(Context context) {
//		if (UI.getHasWindowFocus()) {
			close();
			dialog = new MyDialogFragment();
			dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
//		}
	}

	public static void close() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
}
