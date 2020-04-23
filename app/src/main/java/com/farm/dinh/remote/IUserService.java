package com.farm.dinh.remote;

import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserService {
    @FormUrlEncoded
    @POST("/api/getUserInfo.php")
    Call<StandardResponse<UserInfo>> getUserInfo(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/api/updateUserInfo.php")
    Call<StandardResponse<UserInfo>> updateUserInfo(@Field("userId") int userId, @Field("name") String name, @Field("district") String district, @Field("street") String street, @Field("ward") String ward,
                                                    @Field("city") String city, @Field("area") String area, @Field("old_password") String old_password, @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("/api/createUser.php")
    Call<StandardResponse<FarmerInfo>> createUser(@Field("userId") int userId, @Field("phone") String phone, @Field("name") String name,
                                                  @Field("street") String street, @Field("ward") String ward, @Field("district") String district, @Field("city") String city, @Field("area") String area);

    @FormUrlEncoded
    @POST("/api/editUser.php")
    Call<StandardResponse<FarmerInfo>> editUser(@Field("userId") int userId, @Field("farmerId") int farmerId, @Field("phone") String phone, @Field("name") String name,
                                                @Field("street") String street, @Field("ward") String ward, @Field("district") String district, @Field("city") String city, @Field("area") String area);

    @POST("/api/getAddress.php")
    Call<StandardResponse<List<City>>> getAddress();

    @FormUrlEncoded
    @POST("/api/getFarmersList.php")
    Call<PagingResponse<List<FarmerInfo>>> getFarmersList(@Field("userId") int userId, @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/searchFarmers..php")
    Call<PagingResponse<List<FarmerInfo>>> searchFarmers(@Field("userId") int userId, @Field("searchKey") String searchKey, @Field("page") int page);
}