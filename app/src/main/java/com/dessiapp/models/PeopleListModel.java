package com.dessiapp.models;

//public class PeopleListModel{
//    public int serialno;
//    public String userid;
//    public String username;
//    public int age;
//    public int gender;
//    public String password;
//    public String mobileno;
//    public String email;
//    public String role;
//    public String status;
//    public String state;
//    public String city;
//}


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PeopleListModel {

    @SerializedName("serialno")
    @Expose
    private Integer serialno;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("gender")
    @Expose
    private Integer gender;
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
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("profileimg")
    @Expose
    private Object profileimg;
    @SerializedName("createdon")
    @Expose
    private String createdon;
    @SerializedName("following")
    @Expose
    private Object following;
    @SerializedName("followers")
    @Expose
    private Object followers;
    @SerializedName("totalfollowing")
    @Expose
    private Integer totalfollowing;
    @SerializedName("totalfollowers")
    @Expose
    private Integer totalfollowers;
    @SerializedName("isofficial")
    @Expose
    private Integer isofficial;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("isFollowing")
    @Expose
    private Boolean isFollowing;

    public Integer getSerialno() {
        return serialno;
    }

    public void setSerialno(Integer serialno) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(Object profileimg) {
        this.profileimg = profileimg;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public Object getFollowing() {
        return following;
    }

    public void setFollowing(Object following) {
        this.following = following;
    }

    public Object getFollowers() {
        return followers;
    }

    public void setFollowers(Object followers) {
        this.followers = followers;
    }

    public Integer getTotalfollowing() {
        return totalfollowing;
    }

    public void setTotalfollowing(Integer totalfollowing) {
        this.totalfollowing = totalfollowing;
    }

    public Integer getTotalfollowers() {
        return totalfollowers;
    }

    public void setTotalfollowers(Integer totalfollowers) {
        this.totalfollowers = totalfollowers;
    }

    public Integer getIsofficial() {
        return isofficial;
    }

    public void setIsofficial(Integer isofficial) {
        this.isofficial = isofficial;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

}


/*

public class PeopleListModel {

    @SerializedName("serialno")
    @Expose
    private Integer serialno;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("gender")
    @Expose
    private Integer gender;
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
    @SerializedName("following")
    @Expose
    private Object following;
    @SerializedName("followers")
    @Expose
    private Object followers;
    @SerializedName("totalfollowing")
    @Expose
    private Integer totalfollowing;
    @SerializedName("totalfollowers")
    @Expose
    private Integer totalfollowers;
    @SerializedName("isofficial")
    @Expose
    private Integer isofficial;

    public Integer getSerialno() {
        return serialno;
    }

    public void setSerialno(Integer serialno) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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

    public Object getFollowing() {
        return following;
    }

    public void setFollowing(Object following) {
        this.following = following;
    }

    public Object getFollowers() {
        return followers;
    }

    public void setFollowers(Object followers) {
        this.followers = followers;
    }

    public Integer getTotalfollowing() {
        return totalfollowing;
    }

    public void setTotalfollowing(Integer totalfollowing) {
        this.totalfollowing = totalfollowing;
    }

    public Integer getTotalfollowers() {
        return totalfollowers;
    }

    public void setTotalfollowers(Integer totalfollowers) {
        this.totalfollowers = totalfollowers;
    }

    public Integer getIsofficial() {
        return isofficial;
    }

    public void setIsofficial(Integer isofficial) {
        this.isofficial = isofficial;
    }

}*/
