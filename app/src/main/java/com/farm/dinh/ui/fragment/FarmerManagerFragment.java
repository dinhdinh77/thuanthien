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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.helper.TextChangeDelayAdapter;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.adapter.FarmerAdapter;
import com.farm.dinh.ui.iinterface.OnItemClick;
import com.farm.dinh.ui.viewmodel.FarmerManagerViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class FarmerManagerFragment extends Fragment {
    private ProgressDialog dialog;
    private NavController navController;
    private FarmerManagerViewModel viewModel;
    private TextChangeDelayAdapter textChangeDelayAdapter;
    private TextInputEditText searchView;
    private FarmerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager_farmer, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(FarmerManagerViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        view.findViewById(R.id.createFarmer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFarmer(null);
            }
        });
        final TextView noData = view.findViewById(R.id.txt_NoData);
        final FrameLayout frameLayout = view.findViewById(R.id.lvFarmer);
        final RecyclerView listFarmer = new RecyclerView(getContext()) {
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
        frameLayout.addView(listFarmer);
        searchView = view.findViewById(R.id.search_View);
        searchView.setSaveEnabled(false);
        textChangeDelayAdapter = new TextChangeDelayAdapter(searchView) {

            @Override
            protected long getDelayMs() {
                return 500L;
            }

            @Override
            public void afterDelay(String s) {
                getListFarmer(s);
            }
        };
        listFarmer.setLayoutManager(new LinearLayoutManager(getContext()));
        listFarmer.setHasFixedSize(true);
        listFarmer.addItemDecoration(new DividerItemDecoration(listFarmer.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new FarmerAdapter();
        listFarmer.setAdapter(adapter);
        adapter.setItemOnClick(new OnItemClick<FarmerInfo>() {
            @Override
            public void onClick(FarmerInfo data) {
                onClickFarmer(data);
            }
        });

        viewModel.getResult().observe(this, new Observer<ViewResult<List<FarmerInfo>>>() {
            @Override
            public void onChanged(ViewResult<List<FarmerInfo>> orderViewResult) {
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
                    adapter.setInfoList(orderViewResult.getSuccess(), orderViewResult.isLoadMore());
                } else if (!orderViewResult.isLoadMore()) {
                    frameLayout.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(getString(R.string.title_no_data));
                }
            }
        });
        getListFarmer(searchView.getText().toString());
    }

    private void needLoadMore(int position) {
        if (position >= adapter.getItemCount() - 1) {
            getListFarmer(searchView.getText().toString());
        }
    }

    private void getListFarmer(String keyword) {
        searchView.removeTextChangedListener(textChangeDelayAdapter);
        dialog = ProgressDialog.show(getActivity(), "", getContext().getResources().getString(R.string.message_loading), true);
        viewModel.getListPaging(keyword);
    }

    private void onClickFarmer(FarmerInfo farmerInfo) {
        UIHelper.hideSoftKeyboard(null, searchView);
        viewModel.resetData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FarmerInfo", farmerInfo);
        bundle.putString("Title", farmerInfo == null ? getString(R.string.title_create_farmer) : getString(R.string.title_edit_farmer));
        navController.navigate(R.id.action_farmerManagerFragment_to_createFarmerFragment, bundle);
    }
}
