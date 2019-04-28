package com.creative.mahir_floral_management.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.model.DemandedStock;
import com.creative.mahir_floral_management.model.ReadyStock;

import java.util.ArrayList;
import java.util.List;

public class DemandedStockAdapter extends RecyclerView.Adapter<DemandedStockAdapter.MyViewHolder> implements Filterable {

    private List<DemandedStock> readyStocks;
    private List<DemandedStock> originalList;
    private final OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, date, shop_name, priority;
        Button btn_complete;
        CardView layout;

        MyViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.card_view);
            itemName = view.findViewById(R.id.tv_product_name);
            itemQuantity = view.findViewById(R.id.tv_demanded_quantity);
            date = view.findViewById(R.id.tv_demanded_date);
            shop_name = view.findViewById(R.id.tv_demanded_shop);
            priority = view.findViewById(R.id.tv_priority);
            btn_complete = view.findViewById(R.id.btn_complete);
        }

        void bindClick(final DemandedStock item, final OnItemClickListener listener) {

            if (null == listener) return;


            btn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

    public DemandedStockAdapter(List<DemandedStock> stockList) {
        this(stockList, null);
    }

    public DemandedStockAdapter(List<DemandedStock> stockList, OnItemClickListener listener) {
        this.readyStocks = stockList;
        this.originalList = stockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_demanded_stock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DemandedStock demandedStock = readyStocks.get(position);

        holder.itemName.setText(demandedStock.getProductName());
        holder.itemQuantity.setText(demandedStock.getDemandedQuantity() + " " +demandedStock.getUnit());
        String formatDate = CommonMethods.changeFormat(demandedStock.getDemandedDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT);
        holder.date.setText(formatDate);
        holder.shop_name.setText(demandedStock.getDemandedShopName());
        holder.priority.setText(demandedStock.getPriority());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.gray_lightest));
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.white));

        holder.bindClick(demandedStock, listener);

    }

    @Override
    public int getItemCount() {
        return readyStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(DemandedStock item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                readyStocks = (List<DemandedStock>) results.values;
                DemandedStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<DemandedStock> filteredResults = null;
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

    protected List<DemandedStock> getFilteredResults(String constraint) {
        List<DemandedStock> results = new ArrayList<>();

        for (DemandedStock item : originalList) {

            if (item.getProductName().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
