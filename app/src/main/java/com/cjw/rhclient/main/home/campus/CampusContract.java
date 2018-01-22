package com.cjw.rhclient.main.home.campus;

import com.cjw.rhclient.been.Rent;

import java.util.List;

public interface CampusContract {
	interface CampusView{
		void showCampusList(List<Rent> result);
	}
	interface CampusPresenter{
		void getCampusList();
	}
}
