package com.farm.dinh.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Order;
import com.farm.dinh.helper.TextChangeDelayAdapter;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.adapter.OrderAdapter;
import com.farm.dinh.ui.iinterface.OnItemClick;
import com.farm.dinh.ui.viewmodel.OrderManagerViewModel;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class OrderManagerFragment extends BaseFragment<OrderManagerViewModel> {
    private ProgressDialog dialog;
    private NavController navController;
    private TextChangeDelayAdapter textChangeDelayAdapter;
    private TextInputEditText searchView;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager_order, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        view.findViewById(R.id.createOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOrder(null);
            }
        });
        final TextView noData = view.findViewById(R.id.txt_NoData);
        final FrameLayout frameLayout = view.findViewById(R.id.lvOrder);
        RecyclerView listOrder = new RecyclerView(getContext()) {
            private int scrollDeltaY;

            @Override
            public void onScrolled(int dx, int dy) {
                scrollDeltaY = dy;
                super.onScrolled(dx, dy);
            }

            @Override
            public void onScrollStateChanged(int state) {
                int lastVisiblePos = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                if (state == SCROLL_STATE_SETTLING && scrollDeltaY >= 0) {
                    needLoadMore(lastVisiblePos);
                }
            }
        };
        frameLayout.addView(listOrder);
        searchView = view.findViewById(R.id.search_View);
        searchView.setSaveEnabled(false);
        textChangeDelayAdapter = new TextChangeDelayAdapter(searchView) {

            @Override
            protected long getDelayMs() {
                return 500L;
            }

            @Override
            public void afterDelay(String s) {
                getListOrder(s);
            }
        };
        listOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        listOrder.setHasFixedSize(true);
        listOrder.addItemDecoration(new DividerItemDecoration(listOrder.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter();
        listOrder.setAdapter(adapter);
        adapter.setItemOnClick(new OnItemClick<Order>() {
            @Override
            public void onClick(Order data) {
                onClickOrder(data);
            }
        });

        getViewModel().getResult().observe(this, new Observer<ViewResult<List<Order>>>() {
            @Override
            public void onChanged(ViewResult<List<Order>> orderViewResult) {
                if (dialog != null) dialog.hide();
                searchView.addTextChangedListener(textChangeDelayAdapter);
                if (orderViewResult == null) {
                    return;
                }
                if (orderViewResult.getError() != null && !orderViewResult.isLoadMore()) {
                    frameLayout.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(orderViewResult.getError());
                }
                if (orderViewResult.getSuccess() != null) {
                    frameLayout.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    adapter.setOrderList(orderViewResult.getSuccess(), orderViewResult.isLoadMore());
                } else if (!orderViewResult.isLoadMore()) {
                    frameLayout.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(getString(R.string.title_no_data));
                }
            }
        });
        getListOrder(searchView.getText().toString());
    }

    private void needLoadMore(int position) {
        if (position >= adapter.getItemCount() - 1) {
            getListOrder(searchView.getText().toString());
        }
    }

    private void getListOrder(String keyword) {
        searchView.removeTextChangedListener(textChangeDelayAdapter);
        dialog = ProgressDialog.show(getActivity(), "", getContext().getResources().getString(R.string.message_loading), true);
        getViewModel().getListPaging(keyword);
    }

    private void onClickOrder(Order order) {
        UIHelper.hideSoftKeyboard(null, searchView);
        getViewModel().resetData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Order", order);
        bundle.putString("Title", order == null ? getString(R.string.title_create_order) : getString(R.string.title_edit_order));
        navController.navigate(R.id.action_orderManagerFragment_to_createOrderFragment, bundle);
    }

    @Override
    public Class<OrderManagerViewModel> getViewModelType() {
        return OrderManagerViewModel.class;
    }
}
