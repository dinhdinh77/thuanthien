package com.farm.dinh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.farm.dinh.R;

public class ManagerFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.actionEditAccount).setOnClickListener(this);
        view.findViewById(R.id.actionCreateFarmer).setOnClickListener(this);
        view.findViewById(R.id.actionCreateOrder).setOnClickListener(this);
        view.findViewById(R.id.actionManagerOrder).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionEditAccount:
                break;
            case R.id.actionCreateFarmer:
                break;
            case R.id.actionCreateOrder:
                break;
            case R.id.actionManagerOrder:
                break;
        }
    }
}
