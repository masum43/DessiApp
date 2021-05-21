package com.dessiapp.provider;

import com.dessiapp.models.ActivityModel;
import com.dessiapp.models.ChangeModel;
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.models.LoginModel;
import com.dessiapp.models.PeoplesModel;
import com.dessiapp.models.PostMultiModel;
import com.dessiapp.models.ProfileModel;
import com.dessiapp.models.RewardModel;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiCaller {

    //public static String BASE_URL = "https://wesharenow.herokuapp.com/api/";
    //public static String BASE_URL = "https://desiiapp.herokuapp.com/api/";
    public static String BASE_URL = "https://www.desiiapp.com/api/";
    public static final String DASH_API = "getAllposts";
    public static final String PEOPLES = "getAllUsers";
    public static final String LOGIN = "login";
    public static final String FOLLOW = "requestfollow";
    public static final String UNFOLLOW = "unfollow";
    public static final String LIKE = "likepost";
    public static final String REMOVE_LIKE = "removelike";
    public static final String DISLIKE = "dislikepost";
    public static final String REMOVE_DISLIKE = "removedislike";
    public static final String PROFILE_DATA = "getUserProfile";
    public static final String UPDATE_PROFILE_DATA = "getUserProfile";
    public static final String POST_CMNT = "addcomment";
    public static final String GET_COMMENT = "getcomments";
    public static final String CHANGE_PASS = "changepassword";
    public static final String FOLLWERS = "getfollowers";
    public static final String FOLOWING = "getfollowings";
    public static final String SEARCH = "searchPeople";
    public static final String DELETE_POST = "deletepost";
    public static final String REWARD_DETAIL = "getRewardDetails";

    static PostService apiCaller;

    public static PostService getInstance() {
        if (apiCaller == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
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


        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(PROFILE_DATA)
        Call<ProfileModel> getProfileData(@Query("userId") String userId);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(UPDATE_PROFILE_DATA)
        Call<Object> updateProfileData(@Field("userId") String userId, @Field("newUserId") String newUserId, @Field("userName") String userName, @Field("email") String email, @Field("mobile") String mobile, @Field("dob") String dob);


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(POST_CMNT)
        Call<Object> postComment(@Field("postId") String postId, @Field("comment") String comment, @Field("commentBy") String commentBy);


        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(GET_COMMENT)
        Call<CommentModel> getComment(@Query("postId") String postId);

        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(CHANGE_PASS)
        Call<Object> changePass(@Field("userId") String userId, @Field("Old_PWD") String Old_PWD, @Field("Password") String Password, @Field("ConfPWD") String ConfPWD);


        @Multipart
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(Api.CREATE_POST1)
        Call<PostMultiModel> postMultiPart(@Part MultipartBody.Part filePart, @Part("postedBy") RequestBody postedBy, @Part("postDesc") RequestBody postDesc, @Part("activity") RequestBody activity);


        @Multipart
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(Api.SET_USER_PIC)
        Call<ChangeModel> changeDp(@Part MultipartBody.Part filePart, @Part("userId") RequestBody postedBy);


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(Api.CREATE_POST_TXT)
        Call<PostMultiModel> postTxt(@Field("postedBy") String userid, @Field("postDesc") String textToPost, @Field("activity") String activity);


        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(FOLLWERS)
        Call<PeoplesModel> getFollowers(@Query("userId") String userid);


        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(FOLOWING)
        Call<PeoplesModel> getFollowing(@Query("userId") String userid);


        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @GET(Api.ACTIVITY)
        Call<ActivityModel> getActivity();


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(SEARCH)
        Call<PeoplesModel> searchPerson(@Field("userId") String userId, @Field("searchVal") String searchVal);


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(DELETE_POST)
        Call<Object> deletePost();


        @FormUrlEncoded
        @Headers({Const.HEAD_TOKEN + ":" + Const.TOKEN_KEY})
        @POST(REWARD_DETAIL)
        Call<RewardModel> getRewards(@Field("userId") String userId);

    }
}
