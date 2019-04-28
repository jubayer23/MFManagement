package com.creative.mahir_floral_management.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.model.Shop;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<Shop> {

    LayoutInflater flater;

    public CustomSpinnerAdapter(Context context, int resouceId, int textviewId, List<Shop> list) {

        super(context, resouceId, textviewId, list);

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        Shop rowItem = getItem(position);

        MyViewHolder holder;
        View rowview = convertView;

        if (rowview == null) {

            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_item, null, false);

            holder = new MyViewHolder(rowview);

            rowview.setTag(holder);
        } else {
            holder = (MyViewHolder) rowview.getTag();
        }

        holder.itemName.setText(rowItem.getName());

        return rowview;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        MyViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.tv_item);
        }
    }
}