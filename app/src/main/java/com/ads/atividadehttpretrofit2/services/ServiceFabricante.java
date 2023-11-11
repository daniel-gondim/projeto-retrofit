package com.ads.atividadehttpretrofit2.services;

import com.ads.atividadehttpretrofit2.model.Fabricante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServiceFabricante {

    @Headers("Accept: application/json")
    @POST("/fabricante")
    Call<Fabricante> postFabricante(@Body Fabricante fabricante);

    @Headers("Accept: application/json")
    @POST("/fabricante")
    Call<List<Fabricante>> getFabricante();


}
