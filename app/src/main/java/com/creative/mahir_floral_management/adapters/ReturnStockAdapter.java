package com.creative.mahir_floral_management.adapters;

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
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.model.ReturnStock;
import com.creative.mahir_floral_management.model.ShopStock;

import java.util.ArrayList;
import java.util.List;

public class ReturnStockAdapter extends RecyclerView.Adapter<ReturnStockAdapter.MyViewHolder> implements Filterable {

    private List<ReturnStock> shopStocks;
    private List<ReturnStock> originalList;
    private final OnItemClickListener listener;
    private boolean isSoldStock = false;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemName, tv_quantity, tv_unit, tv_return_date, tv_comment, tv_receive_date;
        Button  btn_receive;
        CardView item_container;
        LinearLayout ll_sold_row, ll_receivedate_container;

        MyViewHolder(View view) {
            super(view);

            item_container = view.findViewById(R.id.item_container);
            ll_receivedate_container = view.findViewById(R.id.ll_receivedate_container);



            tv_itemName = view.findViewById(R.id.tv_itemName);
            tv_quantity = view.findViewById(R.id.tv_itemQuantity);
            tv_return_date = view.findViewById(R.id.tv_return_date);
            tv_comment = view.findViewById(R.id.tv_comment);
            tv_unit = view.findViewById(R.id.tv_itemUnit);
            tv_receive_date = view.findViewById(R.id.tv_receivedate);

            btn_receive = view.findViewById(R.id.btn_receive);

        }

        void bindClick(final int itemPosition, final ReturnStock item, final OnItemClickListener listener) {



            if (null == listener) return;

           // btn_receive.setVisibility(View.VISIBLE);
            btn_receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemPosition, item);
                }
            });

        }

    }


    public ReturnStockAdapter(List<ReturnStock> stockList, OnItemClickListener listener) {
        this.shopStocks = stockList;
        this.originalList = stockList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_return_stock, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReturnStock rawStock = shopStocks.get(position);

        holder.tv_itemName.setText(rawStock.getProductName());
        holder.tv_quantity.setText(rawStock.getQuantity());
        holder.tv_unit.setText(rawStock.getUnit());

        holder.tv_return_date.setText(CommonMethods.changeFormat(rawStock.getReturnDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT));
        holder.tv_comment.setText(rawStock.getComment());

        if(rawStock.getStatus().equals("1")){
            holder.btn_receive.setVisibility(View.GONE);
            holder.ll_receivedate_container.setVisibility(View.VISIBLE);
            holder.tv_receive_date.setText(CommonMethods.changeFormat(rawStock.getReturnDate(), GlobalAppAccess.SERVER_DATE_FORMAT, GlobalAppAccess.MOBILE_DATE_FORMAT));
        }else{
            holder.ll_receivedate_container.setVisibility(View.GONE);
            holder.btn_receive.setVisibility(View.VISIBLE);
        }


        if (position % 2 == 0)
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.gray_lightest));
        else
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.white));

        holder.bindClick(position, rawStock, listener);


    }

    @Override
    public int getItemCount() {
        return shopStocks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int itemPosition, ReturnStock item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shopStocks = (List<ReturnStock>) results.values;
                ReturnStockAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ReturnStock> filteredResults = null;
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

    protected List<ReturnStock> getFilteredResults(String constraint) {
        List<ReturnStock> results = new ArrayList<>();

        for (ReturnStock item : originalList) {

            if (item.getProductName().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
