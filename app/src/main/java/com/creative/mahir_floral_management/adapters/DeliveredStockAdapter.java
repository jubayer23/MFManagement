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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.model.DeliveredStock;

import java.util.ArrayList;
import java.util.List;

public class DeliveredStockAdapter extends RecyclerView.Adapter<DeliveredStockAdapter.MyViewHolder> implements Filterable {

    private List<DeliveredStock> deliveredStocks;
    private List<DeliveredStock> originalList;
    private final OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, deliverTo, deliverDate, recieveDate, tv_itemUnit;
        LinearLayout layout;

        MyViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.raw_row);
            itemName = view.findViewById(R.id.tv_itemName);
            itemQuantity = view.findViewById(R.id.tv_itemQuantity);
            recieveDate = view.findViewById(R.id.tv_receivedate);
            deliverDate = view.findViewById(R.id.tv_deliverdate);
            deliverTo = view.findViewById(R.id.tv_deliverto);
            tv_itemUnit = view.findViewById(R.id.tv_itemUnit);
        }

        void bindClick(final DeliveredStock item, final OnItemClickListener listener) {

            if (null == listener) return;



        }
    }

    public DeliveredStockAdapter(List<DeliveredStock> stockList) {
        this(stockList, null);
    }

    public DeliveredStockAdapter(List<DeliveredStock> stockList, OnItemClickListener listener) {
        this.deliveredStocks = stockList;
        this.originalList = stockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivered_stock2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DeliveredStock deliveredStock = deliveredStocks.get(position);

        holder.itemName.setText(deliveredStock.getProductName());
        holder.itemQuantity.setText(deliveredStock.getQuantity());
        holder.deliverTo.setText(deliveredStock.getDeliverTo());

        String formatDateD = CommonMethods.changeFormat(deliveredStock.getDeliveryDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT);
        holder.deliverDate.setText(deliveredStock.getDeliveryDate());


        if(deliveredStock.getReceivedDate() == null || deliveredStock.getReceivedDate().isEmpty()){
            holder.recieveDate.setText("Not received yet.");
        }else{
            String formatDateR = CommonMethods.changeFormat(deliveredStock.getReceivedDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT);
            holder.recieveDate.setText(formatDateR);
        }

        //holder.recieveDate.setText(readyStock.getReceivedDate());

        //holder.tv_itemUnit.setText(readyStock.getUnit());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.gray_lightest));
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.layout.getContext(), R.color.white));

        holder.bindClick(deliveredStock, listener);

    }

    @Override
    public int getItemCount() {
        return deliveredStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(DeliveredStock item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                deliveredStocks = (List<DeliveredStock>) results.values;
                DeliveredStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<DeliveredStock> filteredResults = null;
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

    protected List<DeliveredStock> getFilteredResults(String constraint) {
        List<DeliveredStock> results = new ArrayList<>();

        for (DeliveredStock item : originalList) {

            if (item.getDeliverTo().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
