package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<ActivityList> body = null;

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

    public List<ActivityList> getBody() {
        return body;
    }

    public void setBody(List<ActivityList> body) {
        this.body = body;
    }


    public class ActivityList {

        @SerializedName("serialno")
        @Expose
        private Integer serialno;
        @SerializedName("activity")
        @Expose
        private String activity;

        public Integer getSerialno() {
            return serialno;
        }

        public void setSerialno(Integer serialno) {
            this.serialno = serialno;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

    }

}
