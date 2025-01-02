package com.example.bevasarlobeadando;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiService {

    @GET("OCm0jM/data")
    Call<List<Termek>> getProducts();

    @POST("OCm0jM/data")
    Call<Termek> createProduct(@Body Termek product);

    @DELETE("OCm0jM/data/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

}
