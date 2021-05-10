package com.dessiapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class RewardModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private RewardBody rewardBody;

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

    public RewardBody getRewardBody() {
        return rewardBody;
    }

    public void setRewardBody(RewardBody rewardBody) {
        this.rewardBody = rewardBody;
    }


    public class RewardHistory {

        @SerializedName("serialno")
        @Expose
        private Integer serialno;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("rewardid")
        @Expose
        private String rewardid;
        @SerializedName("requestedamount")
        @Expose
        private Integer requestedamount;
        @SerializedName("requestedon")
        @Expose
        private String requestedon;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("transactionid")
        @Expose
        private Object transactionid;
        @SerializedName("claimedon")
        @Expose
        private Object claimedon;
        @SerializedName("updatedby")
        @Expose
        private Object updatedby;
        @SerializedName("amountclaimed")
        @Expose
        private String amountclaimed;
        @SerializedName("claimedlikes")
        @Expose
        private Integer claimedlikes;
        @SerializedName("transactionmethod")
        @Expose
        private String transactionmethod;

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

        public String getRewardid() {
            return rewardid;
        }

        public void setRewardid(String rewardid) {
            this.rewardid = rewardid;
        }

        public Integer getRequestedamount() {
            return requestedamount;
        }

        public void setRequestedamount(Integer requestedamount) {
            this.requestedamount = requestedamount;
        }

        public String getRequestedon() {
            return requestedon;
        }

        public void setRequestedon(String requestedon) {
            this.requestedon = requestedon;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(Object transactionid) {
            this.transactionid = transactionid;
        }

        public Object getClaimedon() {
            return claimedon;
        }

        public void setClaimedon(Object claimedon) {
            this.claimedon = claimedon;
        }

        public Object getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(Object updatedby) {
            this.updatedby = updatedby;
        }

        public String getAmountclaimed() {
            return amountclaimed;
        }

        public void setAmountclaimed(String amountclaimed) {
            this.amountclaimed = amountclaimed;
        }

        public Integer getClaimedlikes() {
            return claimedlikes;
        }

        public void setClaimedlikes(Integer claimedlikes) {
            this.claimedlikes = claimedlikes;
        }

        public String getTransactionmethod() {
            return transactionmethod;
        }

        public void setTransactionmethod(String transactionmethod) {
            this.transactionmethod = transactionmethod;
        }

    }



    public class RewardDetails {

        @SerializedName("rewardfactor")
        @Expose
        private String rewardfactor;
        @SerializedName("minclaimamount")
        @Expose
        private String minclaimamount;
        @SerializedName("totallikes")
        @Expose
        private Integer totallikes;
        @SerializedName("availablerewardlikes")
        @Expose
        private Integer availablerewardlikes;
        @SerializedName("rewardclaimed")
        @Expose
        private String rewardclaimed;
        @SerializedName("availablerewardpoints")
        @Expose
        private Integer availablerewardpoints;
        @SerializedName("availablereward")
        @Expose
        private String availablereward;

        public String getRewardfactor() {
            return rewardfactor;
        }

        public void setRewardfactor(String rewardfactor) {
            this.rewardfactor = rewardfactor;
        }

        public String getMinclaimamount() {
            return minclaimamount;
        }

        public void setMinclaimamount(String minclaimamount) {
            this.minclaimamount = minclaimamount;
        }

        public Integer getTotallikes() {
            return totallikes;
        }

        public void setTotallikes(Integer totallikes) {
            this.totallikes = totallikes;
        }

        public Integer getAvailablerewardlikes() {
            return availablerewardlikes;
        }

        public void setAvailablerewardlikes(Integer availablerewardlikes) {
            this.availablerewardlikes = availablerewardlikes;
        }

        public String getRewardclaimed() {
            return rewardclaimed;
        }

        public void setRewardclaimed(String rewardclaimed) {
            this.rewardclaimed = rewardclaimed;
        }

        public Integer getAvailablerewardpoints() {
            return availablerewardpoints;
        }

        public void setAvailablerewardpoints(Integer availablerewardpoints) {
            this.availablerewardpoints = availablerewardpoints;
        }

        public String getAvailablereward() {
            return availablereward;
        }

        public void setAvailablereward(String availablereward) {
            this.availablereward = availablereward;
        }

    }
    public class RewardBody {

        @SerializedName("RewardDetails")
        @Expose
        private RewardDetails rewardDetails;
        @SerializedName("RewardHistory")
        @Expose
        private List<RewardHistory> rewardHistory = null;

        public RewardDetails getRewardDetails() {
            return rewardDetails;
        }

        public void setRewardDetails(RewardDetails rewardDetails) {
            this.rewardDetails = rewardDetails;
        }

        public List<RewardHistory> getRewardHistory() {
            return rewardHistory;
        }

        public void setRewardHistory(List<RewardHistory> rewardHistory) {
            this.rewardHistory = rewardHistory;
        }

    }
}
