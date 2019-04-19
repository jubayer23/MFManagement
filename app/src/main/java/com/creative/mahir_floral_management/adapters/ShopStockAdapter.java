package com.creative.mahir_floral_management.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.model.ShopStock;

import java.util.List;

public class ShopStockAdapter extends RecyclerView.Adapter<ShopStockAdapter.MyViewHolder> {

    private List<ShopStock> shopStocks;
    private final OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, unit, price, comment;
        Button deliver;
        ConstraintLayout layout;

        MyViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.raw_row);
            itemName = view.findViewById(R.id.tv_itemName);
            itemQuantity = view.findViewById(R.id.tv_itemQuantity);
            price = view.findViewById(R.id.tv_price);
            comment = view.findViewById(R.id.tv_comment);
            unit = view.findViewById(R.id.tv_itemUnit);

            deliver = view.findViewById(R.id.btn_deliver);
        }

        void bindClick(final ShopStock item, final OnItemClickListener listener) {

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

    public ShopStockAdapter(List<ShopStock> stockList) {
        this(stockList, null);
    }

    public ShopStockAdapter(List<ShopStock> stockList, OnItemClickListener listener) {
        this.shopStocks = stockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopstock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ShopStock rawStock = shopStocks.get(position);

        holder.itemName.setText(rawStock.getProductName());
        holder.itemQuantity.setText(rawStock.getQuantity());
        holder.unit.setText(rawStock.getUnit());

        holder.price.setText(rawStock.getPrice());
        holder.comment.setText(rawStock.getComment());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.gray_lightest));
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.white));

        holder.bindClick(rawStock, listener);

    }

    @Override
    public int getItemCount() {
        return shopStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ShopStock item);
    }
}
