package com.example.socialpocket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    String URL = "https://192.168.1.69:44304/";

    @POST("api/Account/Login")
    Call<String>login(@Body User user);
    @POST("api/Account/Register")
    Call<String>register(@Body User user);
    @POST("api/Account/Forgot")
    Call<String>forgot(@Body User user);
}
