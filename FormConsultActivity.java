package com.the.dietector.dietector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormConsultActivity extends AppCompatActivity {
    ListView listEvidence;
    List<Evidences> evidenceData;
    List<Case> ruleCase;

    public static ArraySet<Integer> listcheckid = new ArraySet<Integer>();

    ProgressDialog progressDialog;
    ConsultViewAdapter adapter;


    Button btnSubmit;
    int totalEvidence = 0;
    int resultObesitas = 0;
    int resultSembelit = 0;
    int ncaseProblem_1 = 11;
    int ncaseProblem_2 = 16;
    String result = "abcde";
    float final_result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Dietector");
        getSupportActionBar().setSubtitle("Consult Form");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_form_consult);

        listEvidence = (ListView) findViewById(R.id.lv_evidences);

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listcheckid.size() <= 1) {
                    Toast.makeText(FormConsultActivity.this, "Maaf, setidaknya Anda harus mengisi 2 gejala yang dialami", Toast.LENGTH_SHORT).show();
                    listcheckid.clear();
                    onResume();
                } else {
                    for (int i = 0; i < listcheckid.size(); i++) {
                        for (int j = 0; j <= 27; j++) {
                            if (listcheckid.valueAt(i) == ruleCase.get(j).getId_evidence()) {
                                if (ruleCase.get(j).getId_problem() == 1) {
                                    resultObesitas = resultObesitas + 1;
                                    totalEvidence++;
                                } else if (ruleCase.get(j).getId_problem() == 2) {
                                    resultSembelit = resultSembelit + 1;
                                    totalEvidence++;
                                }
                            }
                        }
                    }
                    //1 = Obesitas, 2 = Sembelit
                    int maxVal_1 = Math.max(totalEvidence, ncaseProblem_1);
                    int maxVal_2 = Math.max(totalEvidence, ncaseProblem_2);
                    float result_1 = (float) resultObesitas / maxVal_1;
                    float result_2 = (float) resultSembelit / maxVal_2;
                    if (result_1 > result_2) {
                        final_result = result_1 * 100;
                        result = "Obesitas";
                    } else {
                        final_result = result_2 * 100;
                        result = "Sembelit";
                    }
                    //Toast.makeText(FormConsultActivity.this, "obesitas:" + resultObesitas + result_1 + "\nsembelit: " + resultSembelit + result_2 + "final:" + final_result, Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(FormConsultActivity.this, ResultConsult.class);
                    myIntent.putExtra("KEY_PROBLEM", result);
                    myIntent.putExtra("KEY_PERSENTASE", Float.toString(Math.round(final_result)));
                    startActivity(myIntent);
                    listcheckid.clear();
                }


            }
        });
    }

    public void onResume() {
        super.onResume();
        ambilDataGejala();
        ambilDataKasus();
    }

    private void ambilDataGejala() {
        progressDialog = new ProgressDialog(FormConsultActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        String URL = new Utilities().getBaseURL();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Evidences>> call = api.getAllEvidence("dietector");
        call.enqueue(new Callback<Value<Evidences>>() {
            @Override
            public void onResponse(Call<Value<Evidences>> call, Response<Value<Evidences>> response) {

                if (response.body() != null) {
                    System.out.println("Response: " + response.message());
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        evidenceData = response.body().getData();
                        adapter = new ConsultViewAdapter(FormConsultActivity.this, R.layout.row_list_evidences, evidenceData);
                        listEvidence.setAdapter(adapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(FormConsultActivity.this, "Tidak terhubung dengan server. Silahkan cek koneksi anda dan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value<Evidences>> call, Throwable t) {
                System.out.println("Retrofit error : " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public void ambilDataKasus() {
        String URL = new Utilities().getBaseURL();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Case>> call = api.getAllCase("dietector");
        call.enqueue(new Callback<Value<Case>>() {
            @Override
            public void onResponse(Call<Value<Case>> call, Response<Value<Case>> response) {
                if (response.body() != null) {
                    System.out.println("Response: " + response.message());
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        ruleCase = response.body().getData();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    System.out.println("Response: " + response.message());
                    Toast.makeText(FormConsultActivity.this, "Tidak terhubung dengan server. Silahkan cek koneksi anda dan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value<Case>> call, Throwable t) {
                System.out.println("Retrofit error : " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
