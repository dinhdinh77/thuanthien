package com.farm.dinh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private List<Order> orderList = new ArrayList<>();

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        holder.initData(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    protected static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView orderDate;
        private TextView orderId;
        private TextView orderPhone;
        private TextView orderQuantity;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderId = itemView.findViewById(R.id.orderId);
            orderPhone = itemView.findViewById(R.id.orderPhone);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
        }

        public void initData(Order order) {
            orderDate.setText(order.getCreatedDate());
            orderId.setText(order.getOrderId());
            orderPhone.setText(order.getPhone());
            orderQuantity.setText(String.valueOf(order.getQuantity()));
        }
    }
}
