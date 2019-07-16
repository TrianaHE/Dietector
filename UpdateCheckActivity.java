package com.the.dietector.dietector;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateCheckActivity extends AppCompatActivity {
    int id;
    String nama;
    int berat, tinggi;
    EditText etNama, etBerat, etTinggi;
    Button btnUpdate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_check);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Dietector");
        getSupportActionBar().setSubtitle("Update Check");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        id = getIntent().getExtras().getInt("ID");
        nama = getIntent().getExtras().getString("NAMA");
        berat = getIntent().getExtras().getInt("BERAT");
        tinggi = getIntent().getExtras().getInt("TINGGI");

        etNama = (EditText) findViewById(R.id.et_unama);
        etBerat = (EditText) findViewById(R.id.et_uberat);
        etTinggi = (EditText) findViewById(R.id.et_utinggi);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        etNama.setText(nama);
        etBerat.setText(Integer.toString(berat));
        etTinggi.setText(Integer.toString(tinggi));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(UpdateCheckActivity.this);
                progressDialog.setMessage("Please Wait........");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();

                id = getIntent().getExtras().getInt("ID");
                String strnama = etNama.getText().toString();
                int intberat = Integer.parseInt(etBerat.getText().toString());
                int inttinggi = Integer.parseInt(etTinggi.getText().toString());
                String status;
                float tinggim = ((float) inttinggi) / 100;
                float bmi = (float) intberat / (tinggim * tinggim);
                if (bmi > 27) {
                    status = getString(R.string.fat1);
                } else if (bmi > 25) {
                    status = getString(R.string.fat2);
                } else if (bmi > 18.4F) {
                    status = getString(R.string.normal);
                } else if (bmi > 17) {
                    status = getString(R.string.thin1);
                } else {
                    status = getString(R.string.thin2);
                }
                float beratideal = (float) 20 * (tinggim * tinggim);
                //Toast.makeText(AddCheckActivity.this, "tinggi:" +tinggi+"\nberat:"+berat+"\nbmi: "+bmi+ "\nberatideal: "+beratideal, Toast.LENGTH_SHORT).show();
                UpdateDataCheck(id, strnama, intberat, inttinggi, status, beratideal);
                //   CheckFragment.newInstance();

            }
        });
    }

    public void onResume() {
        super.onResume();
        CheckFragment.newInstance();
    }

    public void UpdateDataCheck(int id, String nama, int berat, int tinggi, String status, float beratideal) {
        progressDialog = new ProgressDialog(UpdateCheckActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        if (nama.trim().equals("") || berat == 0 || tinggi == 0) {
            Toast.makeText(UpdateCheckActivity.this, "Isian belum lengkap!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            String URL = new Utilities().getBaseURL();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIServices api = retrofit.create(APIServices.class);
            Call<InsertValue> call = api.updateCheck("dietector", id, nama, berat, tinggi, status, beratideal);
            call.enqueue(new Callback<InsertValue>() {
                @Override
                public void onResponse(Call<InsertValue> call, Response<InsertValue> response) {
                    if (response.body() != null) {
                        System.out.println("Response: " + response.message());
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateCheckActivity.this, "Cek berat badan berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                        }
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateCheckActivity.this, "Cek berat badan  gagal diperbarui", Toast.LENGTH_SHORT).show();
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
