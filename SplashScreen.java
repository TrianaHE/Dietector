package com.the.dietector.dietector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {
    private GifImageView givMaskot;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        givMaskot = (GifImageView) findViewById(R.id.giv_maskot);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(progressBar.VISIBLE);

        try{
            InputStream inputStream = getAssets().open("maskotDietector.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            givMaskot.setBytes(bytes);
            givMaskot.startAnimation();
        }catch (IOException ex){

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
                SplashScreen.this.finish();
            }
        },3000); //3000 = 3 second
    }
}
