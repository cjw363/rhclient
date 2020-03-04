package com.cjw.rhclient.been.common;

public class Common {
//		public static String baseUrl = "http://10.0.2.2:8090/rhserver/rh/";
//		public static String baseUrl = "http://10.0.3.2:8090/rhserver/rh/";
	public static String baseUrl = "http://152.136.134.235:8080/rhserver/rh/";

	//0 审核中，1 上架中，2 审核未过，3 主动下架，4 违规下架，5 已下架
	public static final int STATUS_0_UNDER_REVIEWING = 0;
	public static final int STATUS_1_ON_SHELFING = 1;
	public static final int STATUS_2_REVIEW_FAIL = 2;
	public static final int STATUS_3_OFF_SHELF_BY_SELF = 3;
	public static final int STATUS_4_OFF_SHELF_ILLEGAL = 4;
	public static final int STATUS_5_OFF_SHELF_COMMON = 5;
}
