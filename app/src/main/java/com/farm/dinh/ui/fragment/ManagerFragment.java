package com.farm.dinh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.dinh.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ManagerFragment extends Fragment implements View.OnClickListener {
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
        view.findViewById(R.id.actionCreateFarmer).setOnClickListener(this);
        view.findViewById(R.id.actionCreateOrder).setOnClickListener(this);
        view.findViewById(R.id.actionManagerOrder).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionEditAccount:
                navController.navigate(R.id.action_managerFragment_to_userDetailFragment2);
                break;
            case R.id.actionCreateFarmer:
                navController.navigate(R.id.action_managerFragment_to_createFarmerFragment);
                break;
            case R.id.actionCreateOrder:
                navController.navigate(R.id.action_managerFragment_to_createOrderFragment);
                break;
            case R.id.actionManagerOrder:
                navController.navigate(R.id.action_managerFragment_to_historyOrderFragment);
                break;
        }
    }
}
