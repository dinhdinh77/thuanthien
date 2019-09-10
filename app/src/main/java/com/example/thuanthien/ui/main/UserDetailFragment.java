package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.UserInfo;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;

public class UserDetailFragment extends Fragment {
    private EditText name, phone, address, oldPass, newPass, newPassAgain;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity(), new ViewModelFactory()).get(MainViewModel.class);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        oldPass = view.findViewById(R.id.oldPass);
        newPass = view.findViewById(R.id.newPass);
        newPassAgain = view.findViewById(R.id.newPassAgain);
        CheckBox changePass = view.findViewById(R.id.changePass);
        final LinearLayout llChangePass = view.findViewById(R.id.llChangePass);
        final TextView txtNoData = view.findViewById(R.id.txt_NoData);
        final LinearLayout llDetail = view.findViewById(R.id.llDetail);
        changePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llChangePass.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        mainViewModel.getCurrUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if (userInfo != null) {
                    txtNoData.setVisibility(View.GONE);
                    llDetail.setVisibility(View.VISIBLE);
                    setDataToView(userInfo);
                } else {
                    txtNoData.setVisibility(View.VISIBLE);
                    llDetail.setVisibility(View.GONE);
                }
            }
        });
        mainViewModel.getUserInfo();
    }

    private void setDataToView(UserInfo userInfo){
        name.setText(userInfo.getName());
        phone.setText(userInfo.getPhone());
        address.setText(userInfo.getAddress());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (inflater == null) return;
        menu.clear();
        inflater.inflate(R.menu.save_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveInfo) {

        }
        return super.onOptionsItemSelected(item);
    }
}
