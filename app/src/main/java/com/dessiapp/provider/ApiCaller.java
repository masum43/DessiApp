package com.dessiapp.provider;

import com.dessiapp.models.DashModel2;
import com.dessiapp.models.LoginModel;
import com.dessiapp.models.PeoplesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiCaller {

    public static String BASE_URL = "https://wesharenow.herokuapp.com/api/";
    public static final String DASH_API = "getAllposts";
    public static final String PEOPLES = "getAllUsers";
    public static final String LOGIN = "login";
    public static final String FOLLOW = "requestfollow";
    public static final String UNFOLLOW = "unfollow";
    public static final String LIKE = "likepost";
    public static final String REMOVE_LIKE = "removelike";
    public static final String DISLIKE = "dislikepost";
    public static final String REMOVE_DISLIKE = "removedislike";

    static PostService apiCaller;

    public static PostService getInstance() {
        if (apiCaller == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            apiCaller = retrofit.create(PostService.class);
        }

        return apiCaller;
    }

    public interface PostService {

        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(DASH_API)
        Call<DashModel2> getAllPost(@Query("userid") String userid);

        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(PEOPLES)
        Call<PeoplesModel> getAppPeopleName(@Query("userId") String userid);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(LOGIN)
        Call<LoginModel> loginApi(@Field("UserID") String userid, @Field("Password") String password);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(FOLLOW)
        Call<Object> requestFollow(@Field("userId") String userid, @Field("toFollow") String toFollow);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(UNFOLLOW)
        Call<Object> requestUnFollow(@Field("userId") String userid, @Field("toUnFollow") String toUnFollow);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(LIKE)
        Call<Object> likePost(@Field("postId") String postId, @Field("likedBy") String likedBy);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(REMOVE_LIKE)
        Call<Object> likeRemovePost(@Field("postId") String postId, @Field("likeRemovedBy") String likeRemovedBy);


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(DISLIKE)
        Call<Object> dislikePost(@Field("postId") String postId, @Field("dislikedBy") String likeRemovedBy);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(REMOVE_DISLIKE)
        Call<Object> dislikeRemovePost(@Field("postId") String postId, @Field("dislikeRemovedBy") String likeRemovedBy);




    }
}
