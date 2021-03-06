package com.farm.dinh.api;

import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.data.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
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
    Call<APIResponse<UserInfo>> updateUserInfo(@Field("userId") int userId, @Field("name") String name, @Field("district") String district, @Field("street") String street, @Field("ward") String ward,
                                               @Field("city") String city, @Field("area") String area, @Field("old_password") String old_password, @Field("new_password") String new_password);

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
                                             @Field("street") String street, @Field("ward") String ward, @Field("district") String district, @Field("city") String city, @Field("area") String area);

    @FormUrlEncoded
    @POST("/api/editUser.php")
    Call<APIResponse<FarmerInfo>> editUser(@Field("userId") int userId, @Field("farmerId") int farmerId, @Field("phone") String phone, @Field("name") String name,
                                           @Field("street") String street, @Field("ward") String ward, @Field("district") String district, @Field("city") String city, @Field("area") String area);

    @Headers("Content-Type: application/json")
    @POST("/api/createOrder.php")
    Call<APIResponse> createOrder(@Body Order inputOrder);

    @Headers("Content-Type: application/json")
    @POST("/api/editOrder.php")
    Call<APIResponse> editOrder(@Body Order inputOrder);

    @FormUrlEncoded
    @POST("/api/getOrderHistory.php")
    Call<APIResponse<List<Order>>> getOrderHistory(@Field("userId") int userId, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/searchOrders.php")
    Call<APIResponse<List<Order>>> searchOrders(@Field("userId") int userId, @Field("searchKey") String searchKey, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/getFarmersList.php")
    Call<APIResponse<List<FarmerInfo>>> getFarmersList(@Field("userId") int userId, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/searchFarmers.php")
    Call<APIResponse<List<FarmerInfo>>> searchFarmers(@Field("userId") int userId, @Field("searchKey") String searchKey, @Field("page") int page);

    @POST("/api/getAddress.php")
    Call<APIResponse<List<City>>> getAddress();

    @FormUrlEncoded
    @POST("/api/getTreesByFarmer.php")
    Call<APIResponse<List<TreeInfo>>> getTreesByFarmer(@Field("userId") int userId);

    @POST("/api/getTreesList.php")
    Call<APIResponse<List<Tree>>> getTreesList();

    @FormUrlEncoded
    @POST("/api/addTree.php")
    Call<APIResponse<List<TreeInfo>>> addTree(@Field("userId") int userId, @Field("treeId") int treeId, @Field("age") String age, @Field("amount") int amount);


    @FormUrlEncoded
    @POST("/api/editTree.php")
    Call<APIResponse<List<TreeInfo>>> editTree(@Field("id") int id, @Field("treeId") int treeId, @Field("age") String age, @Field("amount") int amount);

}
