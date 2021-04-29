package com.dessiapp.provider;

public class Api {
    static public String BASE_URL="https://wesharenow.herokuapp.com/api";
    static public String STATE_API=BASE_URL+"/getStates";
    static public String CITY_API=BASE_URL+"/getCity?state=";
    static public String REG_REQ_OTP=BASE_URL+"/register";
    static public String SIGN_UP=BASE_URL+"/signUp";
    static public String SIGN_IN=BASE_URL+"/login";
    static public String CREATE_POST1=BASE_URL+"/userpost";
    static public String CREATE_POST_TXT=BASE_URL+"/textpost";
    static public String DASH_API=BASE_URL+"/getAllposts?userid=";
    static public String ACTIVITY=BASE_URL+"/activities";
    static public String ALL_USER=BASE_URL+"/getAllUsers";
    static public String SET_USER_PIC=BASE_URL+"/setUserProfile";
    static public String CREATE_POST(String userId,String desc){
     return BASE_URL+"/userpost?postedBy="+userId+"&postDesc="+desc;
    }


}

