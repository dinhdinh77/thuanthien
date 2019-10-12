package com.farm.dinh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.ui.iinterface.OnItemClick;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList = new ArrayList<>();
    private OnItemClick<Order> onItemClick;

    public void setOrderList(List<Order> list, boolean loadMore) {
        if (!loadMore) this.orderList.clear();
        this.orderList.addAll(list);
        notifyDataSetChanged();
    }

    public void setItemOnClick(OnItemClick<Order> onClick) {
        this.onItemClick = onClick;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        holder.initData(orderList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) onItemClick.onClick(orderList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    protected static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderDate, orderId, name, orderPhone;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderId = itemView.findViewById(R.id.orderId);
            name = itemView.findViewById(R.id.name);
            orderPhone = itemView.findViewById(R.id.orderPhone);
        }

        public void initData(Order order) {
            orderDate.setText(order.getCreatedDate());
            orderId.setText(order.getOrderCode());
            name.setText(order.getName());
            orderPhone.setText(order.getPhone());
        }
    }
}
