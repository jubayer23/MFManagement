package com.creative.mahir_floral_management.appdata.remote;

public class APIUrl {


    public static String BaseUrl = "http://10.0.2.2/mahir_floral_api_project";
    //public static String BaseUrl = "none";
    public static final String URL_LOGIN =  BaseUrl + "/staff/login.php";
    public static final String URL_USER_INFO = BaseUrl +  "/staff/get_user_info.php";
    public static final String URL_SET_USER_CHECK = BaseUrl +  "/staff/user_check.php";
    public static final String URL_GET_USER_CHECK = BaseUrl +  "/staff/get_user_online_status.php";
    public static final String URL_GET_RATING = BaseUrl +  "getRating.jsp";
    public static final String URL_GET_ROUTES = BaseUrl +  "getRoutes.jsp";
    public static final String URL_GET_ROUTE_TRACK = BaseUrl +  "getRouteTrack.jsp";
    public static final String URL_SET_FAV = BaseUrl +  "setFavourite.jsp";
    public static final String URL_DELETE_ROUTE = BaseUrl +  "deleteRoute.jsp";
}
