package com.cjw.rhclient.main.home.rent;

import com.cjw.rhclient.been.Rent;

import java.util.List;

public interface RentContract {
	interface RentView {
		void showRentList(List<Rent> result);
	}
	interface RentPresenter {
		void getRentList(int type, String sortType);
	}
}
