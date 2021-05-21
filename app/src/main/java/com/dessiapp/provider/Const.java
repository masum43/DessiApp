package com.dessiapp.provider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Const {
    static public String NAME = "NAME";
    static public String AGE = "AGE";
    static public String USERNAME = "USERNAME";
    static public String EMAIL = "EMAIL";
    static public String PHONE = "PHONE";
    static public String GENDER = "GENDER";
    static public String STATE = "STATE";
    static public String CITY = "CITY";
    static public String PASS = "PASS";
    static public String OTP = "OTP";


    static public String serialno = "serialno";
    static public String userid = "userid";
    static public String username = "username";
    static public String age = "age";
    static public String gender = "gender";
    static public String password = "password";
    static public String mobileno = "mobileno";
    static public String email = "email";
    static public String role = "role";
    static public String status = "status";
    static public String state = "state";
    static public String city = "city";
    static public String statename = "statename";
    static public String cityname = "cityname";
    static public String profileimg = "profileimg";
    static public String createdon = "createdon";
    static public String STATUS = "status";
    static public String MESSAGE = "message";


    static public String SUCCESS = "success";
    static public String DEFAULT = "Default";
    static public final String HEAD_TOKEN = "x-access-token";
    static public final String TOKEN_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0aW1lIjoiRnJpIEFwciAyMyAyMDIxIDE1OjM0OjM5IEdNVCswNTMwIChJbmRpYSBTdGFuZGFyZCBUaW1lKSIsInVzZXJJZCI6MTIsImlhdCI6MTYxOTE3MjI3OX0.Xm8tSMGetxn6Lxm38tLrhLM5bz9Su-Ws-RR1__ApIgY";


    public static String getGenderName(String gender) {
        switch (gender) {
            case "0":
                return "Male";
            case "1":
                return "Female";
            case "2":
                return "Transgender";
            default:
                return "Unknown";
        }
    }

    public static String getDateFormat1(String dateStr) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = format.parse(dateStr);
        DateFormat format1 = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        return format1.format(date);
    }

    public static String getDateFormat(String dateStr) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = format.parse(dateStr);
        DateFormat format1 = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa", Locale.ENGLISH);
        return format1.format(date);
    }
}
