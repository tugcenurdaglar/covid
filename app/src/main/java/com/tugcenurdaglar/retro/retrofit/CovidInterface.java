package com.tugcenurdaglar.retro.retrofit;

import com.tugcenurdaglar.retro.models.CountryModel;
import com.tugcenurdaglar.retro.models.CovidModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CovidInterface {
    @GET("dayone/country/{countries}/status/confirmed")
    Call<List<CovidModel>> callList(@Path(value = "countries",encoded = true) String country);

  @GET("countries")
    Call<List<CountryModel>> getCountries();


}
