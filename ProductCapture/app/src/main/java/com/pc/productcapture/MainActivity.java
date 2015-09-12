package com.pc.productcapture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pc.productcapture.rest.RecogService;
import com.pc.productcapture.rest.RecogServiceImpl;
import com.pc.productcapture.rest.models.RecogResponse;
import com.pc.productcapture.rest.models.TokenResponse;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class MainActivity extends AppCompatActivity {
    private static final int CAPTURE_IMG = 10;
    private RecogService mRecogService;
    @InjectView(R.id.activity_main_capture)
    Button mCapture;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mRecogService = RecogServiceImpl.getInstance();
        setCaptureListener();
    }

    private void setCaptureListener() {
        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (null == intent) {
            mRecogService.postImageRecognitions("en_US", new TypedFile("image/jpeg", new File(fileUri.getPath())),new Callback<TokenResponse>() {
                @Override
                public void success(TokenResponse tokenResponse, Response response) {
                    mRecogService.getImageRecognitions(tokenResponse.token, new Callback<RecogResponse>() {
                        @Override
                        public void success(RecogResponse recogResponse, Response response) {
                            System.out.println(response);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error.getKind());
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/pics.jpg");
        return new File(mediaStorageDir.getPath());
    }
}
