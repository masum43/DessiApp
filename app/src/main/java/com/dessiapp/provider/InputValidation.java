package com.dessiapp.provider;


import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean isEditTextContainEmail(EditText argEditText) {

        try {

            Pattern pattern = Pattern.compile("[a-zA-Z0-9.\\-_]{2,32}@[a-zA-Z0-9.\\-_]{2,32}\\.[A-Za-z]{2,4}");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPasswordMatches(EditText pass, EditText conf) {

        boolean check = false;
        if (pass.getText().toString().equals(conf.getText().toString()) && pass.getText().toString().length() >= 6) {
            check = true;
        }

        return check;
    }

    public static boolean isPasswordLengthCheck(EditText pass) {

        boolean check = false;
        if (pass.getText().toString().length() >= 6) {
            check = true;
        }

        return check;
    }

    public static boolean isPincodeCheck(EditText pass) {

        boolean check = false;
        if (pass.getText().toString().length() == 6) {
            check = true;
        }

        return check;
    }

    public static boolean isEditTextHasvalue(EditText edt) {
        boolean check;
        if (edt.getText().toString().trim().equals("")) {
            check = false;
        } else
            check = true;

        return check;

    }

    public static boolean isTextViewHasValue(TextView textView) {
        boolean check;
        if (textView.getText().toString().trim().equals("") || textView.equals(null) || textView.equals("")) {
            check = false;
        } else
            check = true;

        return check;

    }

    public static boolean isSpinnerSelected(Spinner spin) {
        boolean check;
       if (spin.getSelectedItemPosition() < 1) {
           check = false;
     } else
          check = true;
       /* if (spin.getSelectedItemPosition()!=-1) {
            check = true;
        } else
            check = false;*/

        return check;

    }

    public static boolean isNumberselected(EditText number) {
        boolean check;
        if (number.getText().toString().length() > 9) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isEdittextOwnerName(EditText argEditText) {

        try {

            Pattern pattern = Pattern.compile("[a-z A-Z]");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMobileNumberValidationCheck(EditText pass) {

        boolean check = false;
        if (pass.getText().toString().length() == 10) {
            //String mobileno="^[0-9]{10}$";
            check = true;
        }

        return check;
    }


}
