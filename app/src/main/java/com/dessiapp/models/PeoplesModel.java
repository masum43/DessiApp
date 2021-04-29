package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
//
//public class PeoplesModel {
//    public String status;
//    public String message;
//    public List<PeopleListModel> body;
//}
//
//



public class PeoplesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<PeopleListModel> body = null;

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

    public List<PeopleListModel> getBody() {
        return body;
    }

    public void setBody(List<PeopleListModel> body) {
        this.body = body;
    }

}



