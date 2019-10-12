package com.farm.dinh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.ui.iinterface.OnItemClick;

import java.util.ArrayList;
import java.util.List;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder> {
    private List<FarmerInfo> infoList = new ArrayList<>();
    private OnItemClick<FarmerInfo> onItemClick;

    public void setInfoList(List<FarmerInfo> list, boolean loadMore) {
        if (!loadMore) this.infoList.clear();
        this.infoList.addAll(list);
        notifyDataSetChanged();
    }

    public void setItemOnClick(OnItemClick<FarmerInfo> onClick) {
        this.onItemClick = onClick;
    }

    @NonNull
    @Override
    public FarmerAdapter.FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FarmerAdapter.FarmerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerAdapter.FarmerViewHolder holder, final int position) {
        holder.initData(infoList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) onItemClick.onClick(infoList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    protected static class FarmerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phone, area, address;

        public FarmerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            area = itemView.findViewById(R.id.area);
            address = itemView.findViewById(R.id.address);
        }

        public void initData(FarmerInfo info) {
            name.setText(info.getName());
            phone.setText(info.getPhone());
            area.setText(String.valueOf(info.getArea()));
            address.setText(info.getAddress());
        }
    }
}
