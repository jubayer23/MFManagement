package com.creative.mahir_floral_management.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    private final String buttonName;
    private boolean isSoldStock = false;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, unit, price, comment, columnSold, soldDateLbl, soldDate;
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

            columnSold = view.findViewById(R.id.tv_column_5);
            soldDateLbl = view.findViewById(R.id.tv_sold_date_lbl);
            soldDate = view.findViewById(R.id.tv_sold_date);
        }

        void bindClick(final ShopStock item, final OnItemClickListener listener) {

            if (!TextUtils.isEmpty(buttonName))
                deliver.setText(buttonName);

            if (null == listener) return;

            deliver.setVisibility(View.VISIBLE);
            deliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

        void setForSold(final ShopStock item) {

            if (!isSoldStock) return;

            deliver.setVisibility(View.GONE);

            columnSold.setVisibility(View.VISIBLE);
            soldDateLbl.setVisibility(View.VISIBLE);
            soldDate.setVisibility(View.VISIBLE);

            soldDate.setText(item.getSoldDate());

        }
    }

    public ShopStockAdapter(List<ShopStock> stockList, OnItemClickListener listener) {
        this(stockList, listener, "");
    }

    public ShopStockAdapter(List<ShopStock> stockList, OnItemClickListener listener, String buttonName) {
        this.shopStocks = stockList;
        this.listener = listener;
        this.buttonName = buttonName;
    }

    public void setSoldStock(boolean soldStock) {
        isSoldStock = soldStock;
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
        holder.setForSold(rawStock);

    }

    @Override
    public int getItemCount() {
        return shopStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ShopStock item);
    }
}
