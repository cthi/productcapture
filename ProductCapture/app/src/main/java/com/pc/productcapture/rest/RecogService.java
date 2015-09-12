package com.pc.productcapture.rest;

import com.pc.productcapture.rest.models.RecogResponse;
import com.pc.productcapture.rest.models.TokenResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface RecogService {

    public static final String API_URL = "https://camfind.p.mashape.com";

    @Headers("X-Mashape-Key: d8mFYRBxhSmsh7cl21wAeqzUURjNp1BluDvjsnjjj02GGHBm7g")
    @Multipart
    @POST("/image_requests")
    void postImageRecognitions(@Part("image_request[locale]") String locale, @Part("image_request[image]") TypedFile typedFile, Callback<TokenResponse> cb);

    @Headers("X-Mashape-Key: d8mFYRBxhSmsh7cl21wAeqzUURjNp1BluDvjsnjjj02GGHBm7g")
    @GET("/image_responses/{token}")
    void getImageRecognitions(@Path("token") String token, Callback<RecogResponse> cb);
}
