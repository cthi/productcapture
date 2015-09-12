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
        System.out.println(resultCode);

        if (null == intent) {
            System.out.println(mRecogService.toString());
            mRecogService.postImageRecognitions("en_US", new TypedFile("application/octet-stream", new File(fileUri.getPath())), new Callback<TokenResponse>() {
                @Override
                public void success(TokenResponse tokenResponse, Response response) {
                    System.out.println(tokenResponse.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.getKind());
                }
            });
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/pics");
        return new File(mediaStorageDir.getPath());
    }
}
