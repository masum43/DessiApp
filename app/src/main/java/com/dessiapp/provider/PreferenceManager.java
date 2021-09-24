package com.dessiapp.provider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 1/25/2017.
 */
public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    // Shared preferences file areaname
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

  /*  public PreferenceManager(SalarySlipFragment salarySlipFragment) {
    }*/

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void putString(Context context, String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public String getString(Context context, String key, String defaultValue){
        return pref.getString(key,defaultValue);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void removeKeyValue(Context context, String key){
        editor.remove(key);
        editor.commit();

    }

}
