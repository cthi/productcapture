package com.pc.productcapture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pc.productcapture.adapter.ItemAdapter;
import com.pc.productcapture.rest.WalmartService;
import com.pc.productcapture.rest.WalmartServiceImpl;
import com.pc.productcapture.rest.wmModel.SearchResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WalmartActivity extends AppCompatActivity {
    private final static String API_KEY = "hd9nvfbv9rdj5gy46ea7yqzb";
    private WalmartService mApi;
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = getIntent().getStringExtra("query");
        setContentView(R.layout.activity_wmart);

        mApi = WalmartServiceImpl.getInstance();

        mApi.searchForItems(query, API_KEY, new Callback<SearchResponse>() {
            @Override
            public void success(SearchResponse searchResponse, Response response) {
                mRv.setAdapter(new ItemAdapter(WalmartActivity.this, searchResponse.items));
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getKind());
            }
        });

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle(query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
