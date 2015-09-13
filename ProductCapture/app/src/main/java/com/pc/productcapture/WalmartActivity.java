package com.pc.productcapture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pc.productcapture.rest.WalmartService;
import com.pc.productcapture.rest.WalmartServiceImpl;
import com.pc.productcapture.rest.wmModel.SearchResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WalmartActivity extends AppCompatActivity {
    private final static String API_KEY = "hd9nvfbv9rdj5gy46ea7yqzb";
    private WalmartService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = getIntent().getStringExtra("query");

        mApi = WalmartServiceImpl.getInstance();

        mApi.searchForItems(query, API_KEY, new Callback<SearchResponse>() {
            @Override
            public void success(SearchResponse searchResponse, Response response) {
                System.out.println(searchResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getKind());
            }
        });
    }
}
