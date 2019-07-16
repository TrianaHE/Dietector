package com.the.dietector.dietector;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCheckActivity extends AppCompatActivity {
    EditText etNama, etBerat, etTinggi;
    Button btnAdd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Dietector");
        getSupportActionBar().setSubtitle("Add Check");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        etNama = (EditText) findViewById(R.id.et_nama);
        etBerat = (EditText) findViewById(R.id.et_berat);
        etTinggi = (EditText) findViewById(R.id.et_tinggi);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strnama = etNama.getText().toString();
                int berat = Integer.parseInt(etBerat.getText().toString());
                int tinggi = Integer.parseInt(etTinggi.getText().toString());
                String status;
                float tinggim = ((float)tinggi)/100;
                float bmi = (float)berat/(tinggim*tinggim);
                if(bmi>27){
                    status = "Gemuk (Kelebihan berat badan tingkat berat)";
                }else if(bmi>25){
                    status = "Gemuk (Kelebihan berat badan tingkat ringan)";
                }else if(bmi>18.4F){
                    status = "Normal";
                }else if(bmi>17){
                    status = "Kurus (Kekurangan berat badan tingkat ringan)";
                }else{
                    status = "Kurus (Kekurangan berat badan tingkat berat)";
                }
                float beratideal = (float)20*(tinggim*tinggim);

                //Toast.makeText(AddCheckActivity.this, "tinggi:" +tinggi+"\nberat:"+berat+"\nbmi: "+bmi+ "\nberatideal: "+beratideal, Toast.LENGTH_SHORT).show();

                insertDataCheck(strnama, berat, tinggi, status, beratideal);
            }
        });
    }
    public void onResume() {
        super.onResume();
        CheckFragment.newInstance();
    }

    public void insertDataCheck(String nama, int berat, int tinggi, String status, float beratideal) {
        progressDialog = new ProgressDialog(AddCheckActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        if (nama.trim().equals("") || berat==0 || tinggi==0) {
            Toast.makeText(AddCheckActivity.this, "Isian belum lengkap!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            String URL = new Utilities().getBaseURL();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIServices api = retrofit.create(APIServices.class);
            Call<InsertValue> call = api.insertCheck("dietector", nama, berat, tinggi, status, beratideal);
            call.enqueue(new Callback<InsertValue>() {
                @Override
                public void onResponse(Call<InsertValue> call, Response<InsertValue> response) {

                    if (response.body() != null) {
                        System.out.println("Response: " + response.message());
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            progressDialog.dismiss();
                            Toast.makeText(AddCheckActivity.this, "Cek berat badan berhasil ditambahkan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddCheckActivity.this, "Cek berat badan  gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<InsertValue> call, Throwable t) {
                    System.out.println("Retrofit error : " + t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
