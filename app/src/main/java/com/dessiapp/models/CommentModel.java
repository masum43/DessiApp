package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<CommentBody> body = null;

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

    public List<CommentBody> getBody() {
        return body;
    }

    public void setBody(List<CommentBody> body) {
        this.body = body;
    }

    public class CommentBody {

        @SerializedName("postid")
        @Expose
        private String postid;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("commentby")
        @Expose
        private String commentby;
        @SerializedName("commentedon")
        @Expose
        private String commentedon;
        @SerializedName("commentbyname")
        @Expose
        private String commentbyname;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommentby() {
            return commentby;
        }

        public void setCommentby(String commentby) {
            this.commentby = commentby;
        }

        public String getCommentedon() {
            return commentedon;
        }

        public void setCommentedon(String commentedon) {
            this.commentedon = commentedon;
        }

        public String getCommentbyname() {
            return commentbyname;
        }

        public void setCommentbyname(String commentbyname) {
            this.commentbyname = commentbyname;
        }

    }

}