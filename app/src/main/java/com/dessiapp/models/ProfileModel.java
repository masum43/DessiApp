package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class ProfileModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private ProfileBody profileBody;

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

    public ProfileBody getProfileBody() {
        return profileBody;
    }

    public void setProfileBody(ProfileBody profileBody) {
        this.profileBody = profileBody;
    }


    public class Post {

        @SerializedName("serialno")
        @Expose
        private Integer serialno;
        @SerializedName("postid")
        @Expose
        private String postid;
        @SerializedName("posttitle")
        @Expose
        private Object posttitle;
        @SerializedName("postdesc")
        @Expose
        private String postdesc;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("filepath")
        @Expose
        private String filepath;
        @SerializedName("postedby")
        @Expose
        private String postedby;
        @SerializedName("likes")
        @Expose
        private Integer likes;
        @SerializedName("dislikes")
        @Expose
        private Integer dislikes;
        @SerializedName("likedby")
        @Expose
        private String likedby;
        @SerializedName("dislikedby")
        @Expose
        private String dislikedby;
        @SerializedName("postedon")
        @Expose
        private String postedon;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("posttype")
        @Expose
        private Integer posttype;
        @SerializedName("activity")
        @Expose
        private Integer activity;
        @SerializedName("comments")
        @Expose
        private Integer comments;

        public Integer getSerialno() {
            return serialno;
        }

        public void setSerialno(Integer serialno) {
            this.serialno = serialno;
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

        public String getPostedby() {
            return postedby;
        }

        public void setPostedby(String postedby) {
            this.postedby = postedby;
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

        public String getLikedby() {
            return likedby;
        }

        public void setLikedby(String likedby) {
            this.likedby = likedby;
        }

        public String getDislikedby() {
            return dislikedby;
        }

        public void setDislikedby(String dislikedby) {
            this.dislikedby = dislikedby;
        }

        public String getPostedon() {
            return postedon;
        }

        public void setPostedon(String postedon) {
            this.postedon = postedon;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPosttype() {
            return posttype;
        }

        public void setPosttype(Integer posttype) {
            this.posttype = posttype;
        }

        public Integer getActivity() {
            return activity;
        }

        public void setActivity(Integer activity) {
            this.activity = activity;
        }

        public Integer getComments() {
            return comments;
        }

        public void setComments(Integer comments) {
            this.comments = comments;
        }

    }



    public class ProfileBody {

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
        private String following;
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
        @SerializedName("totalposts")
        @Expose
        private String totalposts;
        @SerializedName("totallikes")
        @Expose
        private String totallikes;
        @SerializedName("totaldislikes")
        @Expose
        private String totaldislikes;
        @SerializedName("posts")
        @Expose
        private List<Post> posts = null;

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

        public String getFollowing() {
            return following;
        }

        public void setFollowing(String following) {
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

        public String getTotalposts() {
            return totalposts;
        }

        public void setTotalposts(String totalposts) {
            this.totalposts = totalposts;
        }

        public String getTotallikes() {
            return totallikes;
        }

        public void setTotallikes(String totallikes) {
            this.totallikes = totallikes;
        }

        public String getTotaldislikes() {
            return totaldislikes;
        }

        public void setTotaldislikes(String totaldislikes) {
            this.totaldislikes = totaldislikes;
        }

        public List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

    }

}
