package com.cjw.rhclient.been;

public class Location {
	private double longitude = 113.28697867723801;
	private double latitude = 23.113492321480198;
	private String address = "仲恺农业工程学院";

	public Location() {}

	public Location(double latitude, double longitude, String address) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
