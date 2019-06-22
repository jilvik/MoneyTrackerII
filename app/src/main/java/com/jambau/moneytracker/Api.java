package com.jambau.moneytracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface Api {

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userId);

    @GET("items")
    Call<List<Record>> getItems(@Query("type") String type, @Query("auth-token") String token);

    @POST("items/add")
    Call<AddItemResult> addItem(@Query("price") String price,
                                @Query("name") String name,
                                @Query("type") String type,
                                @Query("auth-token") String token);
}
