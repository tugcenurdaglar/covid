package com.tugcenurdaglar.retro.retrofit;

public class ApiUtils {
    public static final String BASE_URL = "https://api.covid19api.com/";


    public static CovidInterface getCovidInterface(){
        return RetrofitClient.getClient(BASE_URL).create(CovidInterface.class);
    }
}
