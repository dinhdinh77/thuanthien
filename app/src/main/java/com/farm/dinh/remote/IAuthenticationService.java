package com.farm.dinh.remote;

import com.farm.dinh.data.model.UserInfo;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAuthenticationService {
    @FormUrlEncoded
    @POST("/api/login.php")
    Call<StandardResponse<UserInfo>> login(@Field("phone") String phone, @Field("password") String password);
}
