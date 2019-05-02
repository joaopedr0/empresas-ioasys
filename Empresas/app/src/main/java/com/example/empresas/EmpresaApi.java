package com.example.empresas;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EmpresaApi {

    @FormUrlEncoded
    @POST("users/auth/sign_in")
    Call<JsonObject> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("enterprises")
    Call<JsonObject> pesquisaEmpresas(
            @Header("access-token") String access_token,
            @Header("client") String client,
            @Header("uid") String uid
    );

}
