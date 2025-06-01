package com.example.garage_car_app.api;

import com.example.garage_car_app.model.NhtsaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NhtsaApiService {
    @GET("vehicles/decodevin/{vin}")
    Call<NhtsaResponse> decodeVin(@Path("vin") String vin, @Query("format") String format);

}
