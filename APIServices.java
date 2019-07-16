package com.the.dietector.dietector;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by TRIANAHE on 15/12/2017.
 */

public interface APIServices {
    @FormUrlEncoded
    @POST("getAllVitamin")
    Call<Value<Vitamin>> getAllVitamin(@Field("key") String key);


    @FormUrlEncoded
    @POST("getAllCheck")
    Call<Value<Check>> getAllCheck(@Field("key") String key);

    @FormUrlEncoded
    @POST("insertCheck")
    Call<InsertValue> insertCheck(@Field("key") String key,
                                  @Field("nama") String nama,
                                  @Field("berat") int berat,
                                  @Field("tinggi") int tinggi,
                                  @Field("status") String status,
                                  @Field("berat_ideal") float berat_ideal
    );

    @FormUrlEncoded
    @POST("updateCheck")
    Call<InsertValue>updateCheck(@Field("key") String key,
                                 @Field("id")int id,
                                 @Field("nama") String nama,
                                 @Field("berat") int berat,
                                 @Field("tinggi") int tinggi,
                                 @Field("status") String status,
                                 @Field("berat_ideal") float berat_ideal
    );
    @FormUrlEncoded
    @POST ("deleteCheck")
    Call<InsertValue>deleteCheck(@Field("key")String key,
                                 @Field("id")int id);
    @FormUrlEncoded
    @POST("getAllEvidence")
    Call<Value<Evidences>> getAllEvidence(@Field("key") String key);
//
//    @FormUrlEncoded
//    @POST("getAllProblem")
//    Call<Value<Problem>> getAllProblem(@Field("key") String key);

    @FormUrlEncoded
    @POST("getAllCase")
    Call<Value<Case>> getAllCase(@Field("key") String key);
}

