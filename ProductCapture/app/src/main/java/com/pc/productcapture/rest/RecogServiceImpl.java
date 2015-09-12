package com.pc.productcapture.rest;

import retrofit.RestAdapter;

public class RecogServiceImpl {
    private static RecogService mApi;

    protected RecogServiceImpl() {
    }

    public static RecogService getInstance() {
        if (null == mApi) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(RecogService.API_URL)
                    .build();

            mApi = restAdapter.create(RecogService.class);
        }
        return mApi;
    }
}
