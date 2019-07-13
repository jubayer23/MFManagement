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
import com.creative.mahir_floral_management.model.RawStock;

import java.util.ArrayList;
import java.util.List;

public class RawStockAdapter extends RecyclerView.Adapter<RawStockAdapter.MyViewHolder> implements Filterable {

    private List<RawStock> rawStocks;
    private List<RawStock> originalList;
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
        this.originalList = stockList;
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
        String formatDate = CommonMethods.changeFormat(rawStock.getReceivedDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT);
        holder.recieveDate.setText(formatDate);
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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                rawStocks = (List<RawStock>) results.values;
                RawStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<RawStock> filteredResults = null;
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

    protected List<RawStock> getFilteredResults(String constraint) {
        List<RawStock> results = new ArrayList<>();

        for (RawStock item : originalList) {

            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
