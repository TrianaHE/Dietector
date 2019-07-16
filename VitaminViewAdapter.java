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

public class VitaminViewAdapter extends ArrayAdapter<Vitamin> {
    Context context;
    int resource;
    List<Vitamin> data;
    VitaminFragment vitaminFragment;


    public VitaminViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Vitamin> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    class ViewHolder {
        //ImageView ivGambar;
        TextView tvNama;
        TextView tvDeskripsi;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            //holder.ivGambar = (ImageView) row.findViewById(R.id.iv_vitamin);
            holder.tvNama = (TextView) row.findViewById(R.id.tv_nama_vitamin);
            holder.tvDeskripsi = (TextView) row.findViewById(R.id.tv_deskripsi_vitamin);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Vitamin vitamin= data.get(position);
        // holder.ivGambar.setImageResource(R.drawable.vitamin_a);
        holder.tvNama.setText(vitamin.getNamavit());
        holder.tvDeskripsi.setText(vitamin.getDeskripsivit());
        return row;
    }

}
