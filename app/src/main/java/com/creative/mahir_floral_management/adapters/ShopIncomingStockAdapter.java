package com.creative.mahir_floral_management.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.model.ShopStock;

import java.util.ArrayList;
import java.util.List;

public class ShopIncomingStockAdapter extends RecyclerView.Adapter<ShopIncomingStockAdapter.MyViewHolder> implements Filterable {

    private List<ShopStock> shopStocks;
    private List<ShopStock> originalList;
    private final OnItemClickListener listener;
    private boolean isSoldStock = false;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemName, tv_quantity, tv_unit, tv_price, tv_comment;
        Button  btn_receive;
        CardView item_container;
        LinearLayout ll_sold_row;

        MyViewHolder(View view) {
            super(view);

            item_container = view.findViewById(R.id.item_container);


            tv_itemName = view.findViewById(R.id.tv_itemName);
            tv_quantity = view.findViewById(R.id.tv_itemQuantity);
            tv_price = view.findViewById(R.id.tv_price);
            tv_comment = view.findViewById(R.id.tv_comment);
            tv_unit = view.findViewById(R.id.tv_itemUnit);

            btn_receive = view.findViewById(R.id.btn_receive);

        }

        void bindClick(final ShopStock item, final OnItemClickListener listener) {



            if (null == listener) return;

            btn_receive.setVisibility(View.VISIBLE);
            btn_receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

    }


    public ShopIncomingStockAdapter(List<ShopStock> stockList, OnItemClickListener listener) {
        this.shopStocks = stockList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_incoming_stock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ShopStock rawStock = shopStocks.get(position);

        holder.tv_itemName.setText(rawStock.getProductName());
        holder.tv_quantity.setText(rawStock.getQuantity());
        holder.tv_unit.setText(rawStock.getUnit());

        holder.tv_price.setText(rawStock.getPrice() +" USD");
        holder.tv_comment.setText(rawStock.getComment());

        if (position % 2 == 0)
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.gray_lightest));
        else
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.white));

        holder.bindClick(rawStock, listener);


    }

    @Override
    public int getItemCount() {
        return shopStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ShopStock item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shopStocks = (List<ShopStock>) results.values;
                ShopIncomingStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ShopStock> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<ShopStock> getFilteredResults(String constraint) {
        List<ShopStock> results = new ArrayList<>();

        for (ShopStock item : originalList) {

            if (item.getProductName().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
