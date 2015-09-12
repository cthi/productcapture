package com.pc.productcapture.rest;

import com.pc.productcapture.rest.models.SearchResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WalmartService {

    public static final String API_URL = "https://developer.walmartlabs.com/v1";

    @GET("/search")
    void searchForItems(@Query("apiKey") int apiKey, @Query("lsPublishedId") String lsPublisherId, @Query("query") String query, Callback<SearchResponse> cb);
}
