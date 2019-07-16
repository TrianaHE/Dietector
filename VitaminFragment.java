package com.the.dietector.dietector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VitaminFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView mTextViewHome;
    ListView listVitamin;
    List<Vitamin> vitaminData;
    ProgressDialog progressDialog;
    VitaminViewAdapter adapter;
    int id;
    String nama, deskripsi, manfaat, contoh;

    public VitaminFragment(){

    }
    public static VitaminFragment newInstance() {
        return new VitaminFragment();
    }

    public static VitaminFragment newInstance(String param1, String param2) {
        VitaminFragment fragment = new VitaminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            listVitamin = (ListView) listVitamin.findViewById(R.id.lv_vitamin);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vitamin, container, false);
        listVitamin = (ListView) view.findViewById(R.id.lv_vitamin);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dietector");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Vitamin");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);


        listVitamin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(getActivity(), DetailVitaminActivity.class);
                int id = vitaminData.get(i).getId();
                nama = vitaminData.get(i).getNamavit();
                deskripsi = vitaminData.get(i).getDeskripsivit();
                manfaat = vitaminData.get(i).getManfaatvit();
                contoh = vitaminData.get(i).getContohvit();

                myIntent.putExtra("KEY_ID", id);
                myIntent.putExtra("KEY_NAMA", nama);
                myIntent.putExtra("KEY_DESKRIPSI", deskripsi);
                myIntent.putExtra("KEY_MANFAAT", manfaat);
                myIntent.putExtra("KEY_CONTOH", contoh);
                startActivity(myIntent);
            }
        });
        setHasOptionsMenu(true);
        ambilDataVitamin();

        return view;
    }


    public void ambilDataVitamin() {
        progressDialog = new ProgressDialog(getContext());
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
        Call<Value<Vitamin>> call = api.getAllVitamin("dietector");
        call.enqueue(new Callback<Value<Vitamin>>() {
            @Override
            public void onResponse(Call<Value<Vitamin>> call, Response<Value<Vitamin>> response) {
                if (response.body() != null) {
                    System.out.println("Response: " + response.message());
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        vitaminData = response.body().getData();
                        adapter = new VitaminViewAdapter(getContext(), R.layout.row_list_vitamin, vitaminData);
                        listVitamin.setAdapter(adapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    System.out.println("Response: " + response.message());
                    Toast.makeText(getContext(), "Tidak terhubung dengan server. Silahkan cek koneksi anda dan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value<Vitamin>> call, Throwable t) {
                System.out.println("Retrofit error : " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
