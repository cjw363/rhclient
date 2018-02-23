package com.cjw.rhclient.main.home.detail;

import com.cjw.rhclient.been.BBs;

import java.util.List;

public interface DetailContract {
	interface DetailView {
		void showBBsList(List<BBs> result);
	}
	interface DetailPresenter {
		void bbs(String input, int rentId);

		void getBBsList(int id);
	}
}
