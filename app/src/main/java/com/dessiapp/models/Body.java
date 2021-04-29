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

    @SerializedName("isLikedbyMe")
    @Expose
    boolean isLikedbyMe;

    @SerializedName("isdisLikedbyMe")
    @Expose
    boolean isdisLikedbyMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}