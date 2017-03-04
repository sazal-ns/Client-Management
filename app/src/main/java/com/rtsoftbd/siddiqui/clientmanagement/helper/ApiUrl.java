package com.rtsoftbd.siddiqui.clientmanagement.helper;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-23.
 */

public class ApiUrl {
    private static final String BASE_URL = "http://demo2.rtsoftbd.us/api/";

    public static final String LOGIN = BASE_URL.concat("user");

    public static final String INDEX = BASE_URL.concat("adminpage");

    public static final String ADD_CLIENT = BASE_URL.concat("addclient");

    public static final String  CHANGE_PASSWORD = BASE_URL.concat("profileUpdate");

    public static final String EDIT_CLIENT = BASE_URL.concat("clientedit");

    public static final String  CREDIT_HISTORY = BASE_URL.concat("creditHistory");

    public static final String  PAID_HISTORY = BASE_URL.concat("paidHistory");

    public static final String TOTAL_HISTORY = BASE_URL.concat("totalHistory");

    public static final String CLIENT_LIST = BASE_URL.concat("clientList");

    public static final String LEDGAR = BASE_URL.concat("ledgar");

    public static final String LEGGER_BY_DATE = BASE_URL.concat("ledgarByDate");

    public static final String CREDIT_PAYMENT = BASE_URL.concat("creditpayment");

    public static final String PAID_PAYMENT = BASE_URL.concat("paidpayment");

}
