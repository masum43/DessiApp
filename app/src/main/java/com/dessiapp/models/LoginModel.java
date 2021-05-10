package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<Body> body = null;

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

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

    public class Body {
        @SerializedName("serialno")
        @Expose
        private int serialno;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("age")
        @Expose
        private int age;
        @SerializedName("gender")
        @Expose
        private int gender;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("profileimg")
        @Expose
        private String profileimg;
        @SerializedName("createdon")
        @Expose
        private String createdon;
        @SerializedName("statename")
        @Expose
        private String statename;
        @SerializedName("cityname")
        @Expose
        private String cityname;

        public int getSerialno() {
            return serialno;
        }

        public void setSerialno(int serialno) {
            this.serialno = serialno;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProfileimg() {
            return profileimg;
        }

        public void setProfileimg(String profileimg) {
            this.profileimg = profileimg;
        }

        public String getCreatedon() {
            return createdon;
        }

        public void setCreatedon(String createdon) {
            this.createdon = createdon;
        }

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

    }
}