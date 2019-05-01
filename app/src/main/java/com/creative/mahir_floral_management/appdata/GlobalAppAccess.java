package com.creative.mahir_floral_management.appdata;


public class GlobalAppAccess {


    public static final String APP_NAME = "Mahir Floral Management";


    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MOBILE_DATE_FORMAT = "dd/MM/yyyy";


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;


    public static final String KEY_CALL_FROM = "call_from";
    public static final String KEY_NOTIFICATION_ID = "notification_id";

    public static final String TAG_ALARM_RECEIVER = "alarm_receiver";

    public static final String[] reminder_time_options = {"Select a time", "Before time expires", "15 mins before",
            "30 mins before", "1 hour before"};



    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_SHOP_STOCKER = "Shop Stock";
    public static final String ROLE_RAW_STOCKER = "Raw Stock";
    public static final String ROLE_DISTRIBUTOR = "Distributor";

    public static final String[] rideshares_options = {"Uber", "Lyft", "Uber or Lyft"};
    public static final String[] week_day_name = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static final String[] role_alias = {"Select Role", "On Spot Staff", "Raw Stock Staff", "Distributor"};
    public static final String[] role_set_in_server = {"Dummy", ROLE_SHOP_STOCKER, ROLE_RAW_STOCKER, ROLE_DISTRIBUTOR};

    public static final String[] icon_name = {
            "icon_1",
            "icon_2",
            "icon_3",
            "icon_4",
            "icon_5",
            "icon_6",
            "icon_7",
            "icon_8",
            "icon_9",
            "icon_10",
            "icon_11",
            "icon_12",
            "icon_13",
            "icon_14",
            "icon_15",
            "icon_16",
            "icon_17",
            "icon_18",

    };
    public static final String[] icon_details = {

            "smoking area: official or not, there are many smokers in this area.",
            "uphill: we might not want the exercise. help warn others.",
            "downhill: steep downhill is difficult if carrying something heavy. ",
            "construction: loud, dusty and unhealthy. Good to avoid.",
            "stairs: good to know for people with bicycles and more.",
            "downstairs: helpful to know if we have luggage.",
            "taxi: this place is good to wait for taxi OR there are many taxis here.",
            "medical: hospitals for emergency situations, also pharmacies.",
            "electrical outlet: you can charge your phone!",
            "printing: it's so difficult to find a printer sometimes.",
            "donation boxes: for clothes or other",
            "no sidewalk: better to avoid if walking",
            "accident prone: bad lights, sidewalk or sharp corner, something makes this place prone to accidents.",
            "shoe fix: shoe emergencies are real.",
            "dark streets: bad things happen in the dark",
            "bathroom: no need to explain",
            "police station: never there when we need them.",
            "narrow streets: It's a good idea not to drive through here."

    };



    public static final String KEY_SHOP_ID = "shop_id";
    public static final String KEY_SHOP_NAME = "shop_name";


    public static final int SHOP_ID_HQ = 1;
    public static final int SHOP_ID_CONNECTICUT = 2;
    public static final int SHOP_ID_EAST = 3;
    public static final int SHOP_ID_WEST = 4;
    public static final int SHOP_ID_VILLAGE = 5;
    public static final int SHOP_ID_HUDSONYARD = 6;


}
