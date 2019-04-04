package com.creative.mahir_floral_management.appdata.remote;

public class APIUrl {


    public static String BaseUrl = "http://60d252e6.ngrok.io/mahir_floral_api_project/";
    //public static String BaseUrl = "none";
    public static final String URL_LOGIN =  BaseUrl + "staff/login.php";
    public static final String URL_REGISTRATION = BaseUrl +  "register.jsp";
    public static final String URL_ADD_ROUTE = BaseUrl +  "addRoute.jsp";
    public static final String URL_ADD_RATING = BaseUrl +  "addRating.jsp";
    public static final String URL_GET_RATING = BaseUrl +  "getRating.jsp";
    public static final String URL_GET_ROUTES = BaseUrl +  "getRoutes.jsp";
    public static final String URL_GET_ROUTE_TRACK = BaseUrl +  "getRouteTrack.jsp";
    public static final String URL_SET_FAV = BaseUrl +  "setFavourite.jsp";
    public static final String URL_DELETE_ROUTE = BaseUrl +  "deleteRoute.jsp";
}
