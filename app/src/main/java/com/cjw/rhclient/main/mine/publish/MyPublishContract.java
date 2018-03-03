package com.cjw.rhclient.main.mine.publish;

import com.cjw.rhclient.been.Rent;

import java.util.List;

public interface MyPublishContract {
	interface MyPublishView {
		void showMyPublishList(List<Rent> result);

		void updateStatusAdapter(int position, int status);

		void deleteRentAdapter(int position);
	}

	interface MyPublishPresenter {
		void getMyPublish();

		void deleteRent(int rentId, int position);

		void updateStatusRent(int rentId, int status, int position);
	}
}
