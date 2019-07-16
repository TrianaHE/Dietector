package com.the.dietector.dietector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TRIANAHE on 09/12/2017.
 */

public class CheckViewAdapter extends ArrayAdapter<Check> {
    Context context;
    int resource;
    List<Check> data;

    public CheckViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Check> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    class ViewHolder {
        TextView tvNama;
        TextView tvBerat;
        TextView tvTinggi;
        TextView tvStatus;
        TextView tvBeratIdeal;
        TextView tvWaktu;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.tvNama = (TextView) row.findViewById(R.id.tv_nama);
            holder.tvBerat = (TextView) row.findViewById(R.id.tv_berat);
            holder.tvTinggi = (TextView) row.findViewById(R.id.tv_tinggi);
            holder.tvBeratIdeal = (TextView) row.findViewById(R.id.tv_beratideal);
            holder.tvStatus = (TextView) row.findViewById(R.id.tv_status);
            holder.tvWaktu = (TextView) row.findViewById(R.id.tv_waktu);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Check check = data.get(position);
        holder.tvNama.setText(check.getNama());
        holder.tvBerat.setText(Integer.toString(check.getBerat()));
        holder.tvTinggi.setText(Integer.toString(check.getTinggi()));
        holder.tvStatus.setText(check.getStatus());
        holder.tvBeratIdeal.setText(Float.toString(check.getBeratIdeal()));
        String tanggal = check.getCreateAt().substring(0, 10);
        holder.tvWaktu.setText(tanggal);
        return row;
    }

}
