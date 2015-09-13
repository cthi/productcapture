package com.pc.productcapture.rest;

import retrofit.RestAdapter;

public class WalmartServiceImpl {
    private static WalmartService mApi;

    protected WalmartServiceImpl() {
    }

    public static WalmartService getInstance() {
        if (null == mApi) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(WalmartService.API_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            mApi = restAdapter.create(WalmartService.class);
        }
        return mApi;
    }
}
