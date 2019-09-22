package com.farm.dinh.api;

import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.UserInfo;

import java.util.List;

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
    Call<APIResponse<UserInfo>> updateUserInfo(@Field("userId") int userId, @Field("name") String name,
                                               @Field("old_password") String old_password, @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("/api/getQuestionsList.php")
    Call<APIResponse<Questions>> getQuestionsList(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/api/addAnswer.php")
    Call<APIResponse> addAnswer(@Field("userId") int userId, @Field("questionId") int questionId, @Field("answer") String answer);

    @POST("/api/getProductsList.php")
    Call<APIResponse<List<Product>>> getProductsList();

    @FormUrlEncoded
    @POST("/api/createUser.php")
    Call<APIResponse<FarmerInfo>> createUser(@Field("userId") int userId, @Field("phone") String phone, @Field("name") String name,
                                             @Field("street") String street, @Field("ward") String ward, @Field("district") String district, @Field("city") String city);

    @FormUrlEncoded
    @POST("/api/createOrder.php")
    Call<APIResponse> createOrder(@Field("agencyId") int agencyId, @Field("phone") String phone, @Field("quantity") String quantity, @Field("productId") String productId);

    @FormUrlEncoded
    @POST("/api/getOrderHistory.php")
    Call<APIResponse<List<Order>>> getOrderHistory(@Field("userId") int userId);

}
