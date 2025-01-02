package com.example.bevasarlobeadando;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiService {

    @GET("Qy9vk7/data")
    Call<List<Termek>> getProducts();

    @POST("Qy9vk7/data")
    Call<Termek> createProduct(@Body Termek product);

    @DELETE("Qy9vk7/data/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

}
