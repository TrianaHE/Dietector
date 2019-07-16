package com.the.dietector.dietector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultConsult extends AppCompatActivity {

    TextView tvProblem, tvPersentase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_consult);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Dietector");
        getSupportActionBar().setSubtitle("Result Consult");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        tvProblem = (TextView)findViewById(R.id.tv_problem);
        tvPersentase = (TextView)findViewById(R.id.tv_persentase);

        String problem= getIntent().getExtras().getString("KEY_PROBLEM");
        String persentase = getIntent().getExtras().getString("KEY_PERSENTASE");

        tvProblem.setText(problem);
        tvPersentase.setText(persentase+"%");

    }
}
