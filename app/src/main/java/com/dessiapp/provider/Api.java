package com.dessiapp.provider;

public class Api {
    //final static public String BASE_URL = "https://desiiapp.herokuapp.com/api";
    final static public String BASE_URL1 = "https://www.desiiapp.com";
    final static public String BASE_URL = BASE_URL1+"/api";
    final static public String STATE_API = BASE_URL + "/getStates";
    final static public String CITY_API = BASE_URL + "/getCity?state=";
    final static public String REG_REQ_OTP = BASE_URL + "/register";
    final static public String SIGN_UP = BASE_URL + "/signUp";
    final static public String SIGN_IN = BASE_URL + "/login";
    final static public String CREATE_POST1 = BASE_URL + "/userpost";
    final static public String CREATE_POST_TXT = BASE_URL + "/posttext";
    final static public String DASH_API = BASE_URL + "/getAllposts?userid=";
    final static public String ACTIVITY = BASE_URL + "/activities";
    final static public String ALL_USER = BASE_URL + "/getAllUsers";
    final static public String SET_USER_PIC = BASE_URL + "/setUserProfile";
    final static public String FORGOT_PASS = BASE_URL + "/forgotpassword";
    final static public String RESET_PASS = BASE_URL + "/resetpassword";
    final static public String PRIVACY_POLICY = BASE_URL1+"/uploads/privacyPolicy.html";

    static public String CREATE_POST(String userId, String desc) {
        return BASE_URL + "/userpost?postedBy=" + userId + "&postDesc=" + desc;
    }


}

