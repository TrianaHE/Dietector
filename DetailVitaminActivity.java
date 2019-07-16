package com.the.dietector.dietector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailVitaminActivity extends AppCompatActivity {
    TextView namaVit, deskripsiVit, manfaatVit, contohVit;
    ImageView imgVitamin;

    String nama, deskripsi, manfaat, contoh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vitamin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Dietector");
        getSupportActionBar().setSubtitle("Detail Vitamin");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        nama= getIntent().getExtras().getString("KEY_NAMA");
        deskripsi = getIntent().getExtras().getString("KEY_DESKRIPSI");
        manfaat= getIntent().getExtras().getString("KEY_MANFAAT");
        contoh = getIntent().getExtras().getString("KEY_CONTOH");

        imgVitamin = (ImageView)findViewById(R.id.iv_detail_vitamin);
        namaVit= (TextView) findViewById(R.id.tv_dnama);
        deskripsiVit = (TextView)findViewById(R.id.tv_ddeskripsi);
        manfaatVit= (TextView) findViewById(R.id.tv_dmanfaat);
        contohVit = (TextView)findViewById(R.id.tv_dcontoh);

        String codeVit = nama.substring(8);
        if(codeVit.equals("A")){
            imgVitamin.setImageResource(R.drawable.vitamin_a);
        }else if(codeVit=="B"){
            imgVitamin.setImageResource(R.drawable.vitamin_b);
        }else if(codeVit.equals("C")){
            imgVitamin.setImageResource(R.drawable.vitamin_c);
        }else if(codeVit.equals("D")){
            imgVitamin.setImageResource(R.drawable.vitamin_d);
        }else if(codeVit.equals("E")){
            imgVitamin.setImageResource(R.drawable.vitamin_e);
        }else if(codeVit.equals("K")){
            imgVitamin.setImageResource(R.drawable.vitamin_k);
        }else{
            imgVitamin.setImageResource(R.drawable.vitamin_b);
        }
        namaVit.setText(nama);
        deskripsiVit.setText(deskripsi);
        manfaatVit.setText(manfaat);
        contohVit.setText(contoh);
    }

}

