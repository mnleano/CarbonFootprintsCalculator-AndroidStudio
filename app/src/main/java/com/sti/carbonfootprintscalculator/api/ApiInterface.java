package com.sti.carbonfootprintscalculator.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by mykelneds on 28/03/2017.
 */

public interface ApiInterface {

    @GET("emissions")
    Call<TotalEmissionResponse> getTotalEmission();

    @POST("emissions")
    Call<Void> postEmission(@Body EmissionRequest request);

}
