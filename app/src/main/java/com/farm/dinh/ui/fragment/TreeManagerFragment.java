package com.farm.dinh.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.ui.adapter.TreeAdapter;
import com.farm.dinh.ui.iinterface.OnItemClick;
import com.farm.dinh.ui.viewmodel.TreeManagerViewModel;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class TreeManagerFragment extends BaseFragment<TreeManagerViewModel> {
    private ProgressDialog dialog;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager_tree, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        final int farmerId = getArguments().getInt("FarmerId");
        Button createTree = view.findViewById(R.id.createTree);
        final TextView noData = view.findViewById(R.id.txt_NoData);
        final RecyclerView listView = view.findViewById(R.id.lvTree);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        final TreeAdapter adapter = new TreeAdapter();
        listView.setAdapter(adapter);
        adapter.setOnItemClick(new OnItemClick<TreeInfo>() {
            @Override
            public void onClick(TreeInfo data) {
                onClickTree(data, farmerId);
            }
        });
        createTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTree(null, farmerId);
            }
        });
        getViewModel().getResult().observe(this, new Observer<ViewResult<List<TreeInfo>>>() {
            @Override
            public void onChanged(ViewResult<List<TreeInfo>> orderViewResult) {
                if (dialog != null) dialog.hide();
                if (orderViewResult == null) {
                    return;
                }
                if (orderViewResult.getError() != null) {
                    listView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(orderViewResult.getError());
                } else if (orderViewResult.getSuccess() != null) {
                    listView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    adapter.setTreeInfos(orderViewResult.getSuccess());
                } else {
                    listView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(getString(R.string.title_no_data));
                }
            }
        });
        getViewModel().getTreesByFarmer(farmerId);
        dialog = ProgressDialog.show(getActivity(), "", getContext().getResources().getString(R.string.message_loading), true);
    }

    private void onClickTree(TreeInfo treeInfo, int farmerId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("TreeInfo", treeInfo);
        bundle.putInt("FarmerId", farmerId);
        bundle.putString("Title", treeInfo == null ? getString(R.string.title_create_tree) : getString(R.string.title_edit_tree));
        navController.navigate(R.id.action_treeManagerFragment_to_createTreeFragment, bundle);
    }

    @Override
    public Class<TreeManagerViewModel> getViewModelType() {
        return TreeManagerViewModel.class;
    }
}
