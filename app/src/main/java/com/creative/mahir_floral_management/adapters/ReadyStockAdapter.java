package com.creative.mahir_floral_management.adapters;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.model.ReadyStock;

import java.util.ArrayList;
import java.util.List;

public class ReadyStockAdapter extends RecyclerView.Adapter<ReadyStockAdapter.MyViewHolder> implements Filterable {

    private List<ReadyStock> readyStocks;
    private List<ReadyStock> originalList;
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

        void bindClick(final ReadyStock item, final OnItemClickListener listener) {

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

    public ReadyStockAdapter(List<ReadyStock> stockList) {
        this(stockList, null);
    }

    public ReadyStockAdapter(List<ReadyStock> stockList, OnItemClickListener listener) {
        this.readyStocks = stockList;
        this.originalList = stockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ready_stock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReadyStock readyStock = readyStocks.get(position);

        holder.itemName.setText(readyStock.getName());
        holder.itemQuantity.setText(readyStock.getQuantity());
        //holder.recieveDate.setText(readyStock.getReceivedDate());
        String formatDate = CommonMethods.changeFormat(readyStock.getReceivedDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT);
        holder.recieveDate.setText(formatDate);
        holder.unit.setText(readyStock.getUnit());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.gray_lightest));
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.white));

        holder.bindClick(readyStock, listener);

    }

    @Override
    public int getItemCount() {
        return readyStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ReadyStock item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                readyStocks = (List<ReadyStock>) results.values;
                ReadyStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ReadyStock> filteredResults = null;
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

    protected List<ReadyStock> getFilteredResults(String constraint) {
        List<ReadyStock> results = new ArrayList<>();

        for (ReadyStock item : originalList) {

            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
