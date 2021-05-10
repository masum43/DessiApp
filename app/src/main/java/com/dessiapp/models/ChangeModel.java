package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChangeModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private Result body;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getBody() {
        return body;
    }

    public void setBody(Result body) {
        this.body = body;
    }


    public class Result {

        @SerializedName("profileimgurl")
        @Expose
        private String profileimgurl;

        public String getProfileimgurl() {
            return profileimgurl;
        }

        public void setProfileimgurl(String profileimgurl) {
            this.profileimgurl = profileimgurl;
        }

    }

}
