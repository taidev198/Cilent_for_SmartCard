package com.example.client.data.api;

public class UtilsApi {
    private static String BASE_URL = "http://192.168.0.103:5000/";

    public static ApiClient getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiClient.class);
    }

}
