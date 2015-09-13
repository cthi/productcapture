package com.pc.productcapture.rest;

import com.pc.productcapture.rest.wmModel.SearchResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WalmartService {
    public static final String API_URL = "https://api.walmartlabs.com/v1";

    @GET("/search")
    void searchForItems(@Query("query") String query, @Query("apiKey") String apiKey,
                        Callback<SearchResponse> cb);
}
