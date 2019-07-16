package com.the.dietector.dietector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by TRIANAHE on 04/01/2018.
 */

public class ConsultViewAdapter extends ArrayAdapter<Evidences> {
    Context context;
    int resource;
    List<Evidences> data;


    public ConsultViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Evidences> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    class ViewHolder {
        CheckBox cbEvidences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.cbEvidences = (CheckBox) row.findViewById(R.id.cb_evidences);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        final Evidences evidences = data.get(position);
        holder.cbEvidences.setText(evidences.getName());
        holder.cbEvidences.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    FormConsultActivity.listcheckid.add(evidences.getId());
                //  Toast.makeText(getContext(), "id" + FormConsultActivity.listcheckid , Toast.LENGTH_SHORT).show();
                } else {
                    FormConsultActivity.listcheckid.remove(evidences.getId());
                //  Toast.makeText(getContext(), "id" + FormConsultActivity.listcheckid , Toast.LENGTH_SHORT).show();
                }
            }
        });
        return row;
    }

}
