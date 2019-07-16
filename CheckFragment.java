package com.the.dietector.dietector;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listCheck;
    List<Check> checkData;
    ProgressDialog progressDialog;
    CheckViewAdapter adapter;
    FloatingActionButton fab;

    int idMenu;
    String titleMenu;
    int iconMenu;
    int id;
    String nama;
    int berat, tinggi;

    public CheckFragment(){

    }
    public static CheckFragment newInstance() {
        return new CheckFragment();
    }

    public static CheckFragment newInstance(String param1, String param2) {
        CheckFragment fragment = new CheckFragment();
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
            listCheck = (ListView) listCheck.findViewById(R.id.lv_check);
            fab = (FloatingActionButton) listCheck.findViewById(R.id.fb_add_cek);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        listCheck = (ListView) view.findViewById(R.id.lv_check);
        fab = (FloatingActionButton) view.findViewById(R.id.fb_add_cek);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCheckActivity.class);
                startActivity(intent);
            }
        });


        listCheck.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idMenu = checkData.get(i).getId();
                titleMenu = checkData.get(i).getNama();
                iconMenu = R.drawable.check;
                id = checkData.get(i).getId();
                nama = checkData.get(i).getNama();
                berat = checkData.get(i).getBerat();
                tinggi = checkData.get(i).getTinggi();
                registerForContextMenu(adapterView);
                progressDialog.openContextMenu(adapterView);
                return true;
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.mipmap.ic_dietector);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dietector");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Check");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lv_check) {
            menu.setHeaderTitle(titleMenu);
            menu.setHeaderIcon(iconMenu);
          final MenuInflater menuInflater = getActivity().getMenuInflater();
           menuInflater.inflate(R.menu.check_main, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                Intent myIntent = new Intent(getActivity(), UpdateCheckActivity.class);
                myIntent.putExtra("ID", id);
                myIntent.putExtra("NAMA", nama);
                myIntent.putExtra("BERAT", berat);
                myIntent.putExtra("TINGGI", tinggi);
                startActivity(myIntent);
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Konfirmasi Penghapusan");
                builder.setMessage("Apakah Anda ingin menghapus data berat badan ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDataCek(idMenu);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                return true;
            default:
        }
        return super.onContextItemSelected(item);
    }

    public void ambilDataCheck() {
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
        Call<Value<Check>> call = api.getAllCheck("dietector");
        call.enqueue(new Callback<Value<Check>>() {
            @Override
            public void onResponse(Call<Value<Check>> call, Response<Value<Check>> response) {
                if (response.body() != null) {
                    System.out.println("Response: " + response.message());
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        checkData= response.body().getData();
                        adapter = new CheckViewAdapter(getContext(), R.layout.row_list_cekberat, checkData);
                        listCheck.setAdapter(adapter);
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
            public void onFailure(Call<Value<Check>> call, Throwable t) {
                System.out.println("Retrofit error : " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public void onResume() {
        super.onResume();
        ambilDataCheck();
    }
    public void deleteDataCek(int id) {
        progressDialog = new ProgressDialog(getActivity());
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
        Call<InsertValue> call = api.deleteCheck("dietector", id);
        call.enqueue(new Callback<InsertValue>() {
            @Override
            public void onResponse(Call<InsertValue> call, Response<InsertValue> response) {
                if (response.body() != null) {
                    System.out.println("Response: " + response.message());
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        ambilDataCheck();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    System.out.println("Response: " + response.message());
                    Toast.makeText(getContext(), "Tidak terhubung dengan server. Silahkan cek koneksi anda dan coba lagi", Toast.LENGTH_SHORT).show();
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
