package com.farm.dinh.api;

import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("/api/login.php")
    Call<APIResponse<UserInfo>> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/getUserInfo.php")
    Call<APIResponse<UserInfo>> getUserInfo(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/api/updateUserInfo.php")
    Call<APIResponse<UserInfo>> updateUserInfo(@Field("userId") int userId, @Field("name") String name, @Field("old_password") String old_password, @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("/api/getQuestionsList.php")
    Call<APIResponse<Questions>> getQuestionsList(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/api/addAnswer.php")
    Call<APIResponse> addAnswer(@Field("userId") int userId, @Field("questionId") int questionId, @Field("answer") String answer);

}
