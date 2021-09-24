package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//public class Body{
//        public String username;
//        public String userid;
//        public String profileimg;
//        public String postid;
//        public String posttitle;
//        public String postdesc;
//        public String filename;
//        public String filepath;
//        public int likes;
//        public int dislikes;
//        public int comments;
//        public String postedby;
//        public String postedon;
//        public int posttype;
//        public String activity;
//}


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("username")
    @Expose
    String username;
    @SerializedName("userid")
    @Expose
    String userid;
    @SerializedName("profileimg")
    @Expose
    String profileimg;
    @SerializedName("postid")
    @Expose
    String postid;
    @SerializedName("posttitle")
    @Expose
    Object posttitle;
    @SerializedName("postdesc")
    @Expose
    String postdesc;
    @SerializedName("filename")
    @Expose
    String filename;
    @SerializedName("filepath")
    @Expose
    String filepath;
    @SerializedName("likes")
    @Expose
    Integer likes;
    @SerializedName("dislikes")
    @Expose
    Integer dislikes;
    @SerializedName("comments")
    @Expose
    Integer comments;
    @SerializedName("postedby")
    @Expose
    String postedby;
    @SerializedName("postedon")
    @Expose
    String postedon;
    @SerializedName("posttype")
    @Expose
    Integer posttype;

    @SerializedName("activity")
    @Expose
    String activity;

    @SerializedName("isofficial")
    @Expose
    private Integer isofficial;

    @SerializedName("isLikedbyMe")
    @Expose
    boolean isLikedbyMe;

    @SerializedName("isdisLikedbyMe")
    @Expose
    boolean isdisLikedbyMe;


    @SerializedName("firebasetoken")
    @Expose
    String firebasetoken;

    public Body(){

    }
    public Body(String username, String userid, String profileimg, String postid, Object posttitle,
                String postdesc, String filename, String filepath, Integer likes,
                Integer dislikes, Integer comments, String postedby, String postedon,
                Integer posttype, String activity, Integer isofficial,
                boolean isLikedbyMe, boolean isdisLikedbyMe, String firebasetoken) {
        this.username = username;
        this.userid = userid;
        this.profileimg = profileimg;
        this.postid = postid;
        this.posttitle = posttitle;
        this.postdesc = postdesc;
        this.filename = filename;
        this.filepath = filepath;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
        this.postedby = postedby;
        this.postedon = postedon;
        this.posttype = posttype;
        this.activity = activity;
        this.isofficial = isofficial;
        this.isLikedbyMe = isLikedbyMe;
        this.isdisLikedbyMe = isdisLikedbyMe;
        this.firebasetoken = firebasetoken;
    }

    public String getUsername() {
        return username;
    }
    public String getFirebasetoken() {
        return firebasetoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setFirebasetoken(String firebasetoken) {
        this.firebasetoken = firebasetoken;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public Object getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(Object posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostdesc() {
        return postdesc;
    }

    public void setPostdesc(String postdesc) {
        this.postdesc = postdesc;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostedon() {
        return postedon;
    }

    public void setPostedon(String postedon) {
        this.postedon = postedon;
    }

    public Integer getPosttype() {
        return posttype;
    }

    public void setPosttype(Integer posttype) {
        this.posttype = posttype;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public boolean isLikedbyMe() {
        return isLikedbyMe;
    }

    public void setLikedbyMe(boolean likedbyMe) {
        isLikedbyMe = likedbyMe;
    }

    public boolean isIsdisLikedbyMe() {
        return isdisLikedbyMe;
    }

    public void setIsdisLikedbyMe(boolean isdisLikedbyMe) {
        this.isdisLikedbyMe = isdisLikedbyMe;
    }

    public Integer getIsofficial() {
        return isofficial;
    }

    public void setIsofficial(Integer isofficial) {
        this.isofficial = isofficial;
    }
}