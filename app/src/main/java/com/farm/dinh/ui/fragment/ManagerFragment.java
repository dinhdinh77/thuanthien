package com.farm.dinh.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.farm.dinh.R;
import com.farm.dinh.ui.activity.LoginActivity;
import com.farm.dinh.ui.viewmodel.ManagerViewModel;

import static android.app.Activity.RESULT_OK;
import static com.farm.dinh.local.Pref.KEY_LOGOUT;

public class ManagerFragment extends BaseFragment<ManagerViewModel> implements View.OnClickListener {
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        view.findViewById(R.id.actionEditAccount).setOnClickListener(this);
        view.findViewById(R.id.actionManagerFarmer).setOnClickListener(this);
        view.findViewById(R.id.actionLogout).setOnClickListener(this);
        view.findViewById(R.id.actionManagerOrder).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionEditAccount:
                navController.navigate(R.id.action_managerFragment_to_userDetailFragment2);
                break;
            case R.id.actionManagerFarmer:
                navController.navigate(R.id.action_managerFragment_to_farmerManagerFragment);
                break;
            case R.id.actionLogout:
                logout();
                break;
            case R.id.actionManagerOrder:
                navController.navigate(R.id.action_managerFragment_to_orderManagerFragment);
                break;
        }
    }

    private void logout() {
        getViewModel().logout();
        getActivity().setResult(RESULT_OK);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(KEY_LOGOUT, true);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public Class<ManagerViewModel> getViewModelType() {
        return ManagerViewModel.class;
    }
}
