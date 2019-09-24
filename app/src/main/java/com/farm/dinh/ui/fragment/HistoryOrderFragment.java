package com.farm.dinh.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.ui.adapter.OrderHistoryAdapter;
import com.farm.dinh.ui.viewmodel.OrderHistoryViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryOrderFragment extends Fragment {
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history_order, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderHistoryViewModel viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(OrderHistoryViewModel.class);
        final TextView noData = view.findViewById(R.id.txt_NoData);
        final RecyclerView listOrderHistory = view.findViewById(R.id.lvOrder);
        listOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        listOrderHistory.setHasFixedSize(true);
        listOrderHistory.addItemDecoration(new DividerItemDecoration(listOrderHistory.getContext(), DividerItemDecoration.VERTICAL));
        final OrderHistoryAdapter adapter = new OrderHistoryAdapter();
        listOrderHistory.setAdapter(adapter);
        viewModel.getResult().observe(this, new Observer<ViewResult<List<Order>>>() {
            @Override
            public void onChanged(ViewResult<List<Order>> orderViewResult) {
                if (orderViewResult == null) {
                    if (dialog != null) dialog.hide();
                    return;
                }
                if (orderViewResult.getError() != null) {
                    listOrderHistory.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(orderViewResult.getError());
                    if (dialog != null) dialog.hide();
                } else if (orderViewResult.getSuccess() != null) {
                    listOrderHistory.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    adapter.setOrderList(orderViewResult.getSuccess());
                    if (dialog != null) dialog.hide();
                }
            }
        });
        viewModel.getOrderHistory();
        dialog = ProgressDialog.show(getActivity(), "", getContext().getResources().getString(R.string.message_loading), true);
    }
}
