package com.pc.productcapture;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.pc.productcapture.rest.RecogService;
import com.pc.productcapture.rest.RecogServiceImpl;
import com.pc.productcapture.rest.recogModel.RecogResponse;
import com.pc.productcapture.rest.recogModel.TokenResponse;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class MainActivity extends AppCompatActivity {
    private static final int CAPTURE_IMG = 10;
    private RecogService mRecogService;
    private ImageButton mCapture;
    private View mBg;
    private ProgressBar mPb;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        mRecogService = RecogServiceImpl.getInstance();
        setCaptureListener();
        startAnim();
    }

    private void bindViews() {
        mCapture = (ImageButton) findViewById(R.id.activity_main_capture);
        mBg = findViewById(R.id.bg);
        mPb = (ProgressBar) findViewById(R.id.activity_main_loader);
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
            mPb.setVisibility(View.VISIBLE);
            mCapture.setVisibility(View.GONE);

            mRecogService.postImageRecognitions("en_US", new TypedFile("image/jpeg", new File
                    (fileUri.getPath())), new Callback<TokenResponse>() {
                @Override
                public void success(final TokenResponse tokenResponse, Response response) {
                    checkIfComplete(tokenResponse.token);
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    private void checkIfComplete(final String token) {
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                mRecogService.getImageRecognitions(token, new Callback<RecogResponse>() {
                    @Override
                    public void success(RecogResponse recogResponse, Response response) {
                        if (recogResponse.status.equals("not completed")) {
                            checkIfComplete(token);
                        } else {
                            Intent intent = new Intent(MainActivity.this, WalmartActivity.class);
                            intent.putExtra("query", recogResponse.name);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error.getKind());
                    }
                });
            }
        };
        worker.schedule(r, 8, TimeUnit.SECONDS);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/pics.jpg");
        return new File(mediaStorageDir.getPath());
    }

    private void startAnim() {
        final AnimatorSet animSet = new AnimatorSet();

        int color1 = getResources().getColor(R.color.tg);
        int color2 = getResources().getColor(R.color.tt);
        int color3 = getResources().getColor(R.color.tb);

        final ValueAnimator colorAnim1 = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2);
        colorAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBg.setBackgroundColor((int)colorAnim1.getAnimatedValue());
            }
        });

        final ValueAnimator colorAnim2 = ValueAnimator.ofObject(new ArgbEvaluator(), color2, color3);
        colorAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBg.setBackgroundColor((int)colorAnim2.getAnimatedValue());
            }
        });

        final ValueAnimator colorAnim3 = ValueAnimator.ofObject(new ArgbEvaluator(), color3, color1);
        colorAnim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBg.setBackgroundColor((int)colorAnim3.getAnimatedValue());
            }
        });

        animSet.setDuration(5000);
        animSet.playSequentially(colorAnim1, colorAnim2, colorAnim3);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animSet.start();
            }
        });

        animSet.start();
    }
}
