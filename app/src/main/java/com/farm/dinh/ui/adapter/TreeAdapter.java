package com.farm.dinh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.ui.iinterface.OnItemClick;

import java.util.ArrayList;
import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> {
    private List<TreeInfo> treeInfos = new ArrayList<>();
    private OnItemClick<TreeInfo> onItemClick;

    public void setTreeInfos(List<TreeInfo> treeInfos) {
        this.treeInfos = treeInfos;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick<TreeInfo> onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, final int position) {
        holder.initData(treeInfos.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) onItemClick.onClick(treeInfos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return treeInfos.size();
    }

    protected static class TreeViewHolder extends RecyclerView.ViewHolder {
        private TextView treeName, treeAge, treeQuantity;

        public TreeViewHolder(@NonNull View itemView) {
            super(itemView);
            treeName = itemView.findViewById(R.id.treeName);
            treeAge = itemView.findViewById(R.id.treeAge);
            treeQuantity = itemView.findViewById(R.id.treeQuantity);
        }

        public void initData(TreeInfo treeInfo) {
            treeName.setText(treeInfo.getName());
            treeAge.setText(treeInfo.getAge());
            treeQuantity.setText(String.valueOf(treeInfo.getAmount()));
        }
    }
}
