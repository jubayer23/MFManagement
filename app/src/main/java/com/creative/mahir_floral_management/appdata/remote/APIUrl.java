package com.creative.mahir_floral_management.appdata.remote;

public class APIUrl {

    //public static String BaseUrl = " http://2dcc51a6.ngrok.io/mahir_floral_api_project";
    public static String BaseUrl = "https://mahirfloralevents.com/mahir_floral_api_project";
    //public static String BaseUrl = "http://10.0.2.2/mahir_floral_api_project";

    //public static String BaseUrl = "none";
    public static final String URL_LOGIN = BaseUrl + "/staff/Login.php?";
    public static final String URL_USER_INFO = BaseUrl + "/staff/get_user_info.php";
    public static final String URL_SET_USER_CHECK = BaseUrl + "/staff/user_check.php";
    public static final String URL_GET_USER_CHECK = BaseUrl + "/staff/get_user_online_status.php";
    public static final String URL_USER_TIMESHEET = BaseUrl + "/staff/timesheet.php";
    public static final String URL_GET_SHOPS = BaseUrl + "/admin/getshops.php";
    public static final String URL_GET_ROUTE_TRACK = BaseUrl + "getRouteTrack.jsp";
    public static final String URL_SET_FAV = BaseUrl + "setFavourite.jsp";
    public static final String URL_DELETE_ROUTE = BaseUrl + "deleteRoute.jsp";

    public static final String URL_RAW_STOCK = BaseUrl + "/rawstocker/get_raw_stock.php";
    public static final String URL_Entry_RAW_STOCK = BaseUrl + "/rawstocker/entry_raw_stock.php";

    public static final String URL_READY_STOCK = BaseUrl + "/rawstocker/get_ready_stock.php";
    public static final String URL_ENTRY_READY_STOCK = BaseUrl + "/rawstocker/entry_ready_stock.php";
    public static final String URL_DELIVER_READY_STOCK = BaseUrl + "/rawstocker/entry_deliver_product.php";
    public static final String URL_DELIVERED_STOCKS = BaseUrl + "/rawstocker/get_delivered_stock.php";

    public static final String URL_INCOMING_SHOP_STOCK = BaseUrl + "/shopstocker/get_incoming_shop_stocks.php";
    public static final String URL_SHOP_STOCK = BaseUrl + "/shopstocker/get_shop_stocks.php";

    public static final String URL_MARK_SHOP_STOCK_RECEIVED = BaseUrl + "/shopstocker/entry_receive_stock.php";
    public static final String URL_ENTRY_SHOP_STOCK = BaseUrl + "/shopstocker/entry_sold_stock.php";
    public static final String URL_SOLD_SHOP_STOCK = BaseUrl + "/shopstocker/get_sold_stocks.php";


    public static final String URL_SHOP_MAKE_DEMAND = BaseUrl + "/shopstocker/make_demand.php";
    public static final String URL_COMPLETE_DEMAND = BaseUrl + "/distributor/complete_demand.php";
    public static final String URL_GET_DEMANDED_STOCKS = BaseUrl + "/distributor/get_demanded_stocks.php";

    public static final String URL_STAFF_REGISTRATION = BaseUrl + "/admin/staff_registration.php";

}
