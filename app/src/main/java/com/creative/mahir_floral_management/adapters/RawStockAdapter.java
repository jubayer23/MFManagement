package com.creative.mahir_floral_management.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.model.RawStock;

import java.util.List;

public class RawStockAdapter extends RecyclerView.Adapter<RawStockAdapter.MyViewHolder> {

    private List<RawStock> rawStocks;
    private final OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, recieveDate, deliver, unit;
        ConstraintLayout layout;

        MyViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.raw_row);
            itemName = view.findViewById(R.id.tv_itemName);
            itemQuantity = view.findViewById(R.id.tv_itemQuantity);
            recieveDate = view.findViewById(R.id.tv_recieveDate);
            deliver = view.findViewById(R.id.tv_deliver);
            unit = view.findViewById(R.id.tv_itemUnit);
        }

        void bindClick(final RawStock item, final OnItemClickListener listener) {

            if (null == listener) return;

            deliver.setVisibility(View.VISIBLE);
            deliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

    public RawStockAdapter(List<RawStock> stockList) {
        this(stockList, null);
    }

    public RawStockAdapter(List<RawStock> stockList, OnItemClickListener listener) {
        this.rawStocks = stockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rawstock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RawStock rawStock = rawStocks.get(position);

        holder.itemName.setText(rawStock.getName());
        holder.itemQuantity.setText(rawStock.getQuantity());
        holder.recieveDate.setText(rawStock.getReceived_date());
        holder.unit.setText(rawStock.getUnit());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.gray_lightest));
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.white));

        holder.bindClick(rawStock, listener);

    }

    @Override
    public int getItemCount() {
        return rawStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(RawStock item);
    }
}
