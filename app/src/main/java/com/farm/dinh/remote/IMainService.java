package com.farm.dinh.remote;

import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IMainService {
    @FormUrlEncoded
    @POST("/api/getQuestionsList.php")
    Call<StandardResponse<Questions>> getQuestionsList(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/api/addAnswer.php")
    Call<SimpleResponse> addAnswer(@Field("userId") int userId, @Field("questionId") int questionId, @Field("answer") String answer);

    @Headers("Content-Type: application/json")
    @POST("/api/createOrder.php")
    Call<SimpleResponse> createOrder(@Body Order inputOrder);

    @Headers("Content-Type: application/json")
    @POST("/api/editOrder.php")
    Call<SimpleResponse> editOrder(@Body Order inputOrder);

    @FormUrlEncoded
    @POST("/api/getOrderHistory.php")
    Call<PagingResponse<List<Order>>> getOrderHistory(@Field("userId") int userId, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/searchOrders.php")
    Call<PagingResponse<List<Order>>> searchOrders(@Field("userId") int userId, @Field("searchKey") String searchKey, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/addTree.php")
    Call<SimpleResponse> addTree(@Field("userId") int userId, @Field("treeId") int treeId, @Field("age") String age, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("/api/editTree.php")
    Call<SimpleResponse> editTree(@Field("id") int id, @Field("treeId") int treeId, @Field("age") String age, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("/api/getTreesByFarmer.php")
    Call<StandardResponse<List<TreeInfo>>> getTreesByFarmer(@Field("userId") int userId);

    @POST("/api/getTreesList.php")
    Call<StandardResponse<List<Tree>>> getTreesList();

    @POST("/api/getProductsList.php")
    Call<StandardResponse<List<Product>>> getProductsList();
}
