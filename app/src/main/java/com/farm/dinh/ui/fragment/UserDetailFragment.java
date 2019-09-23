package com.farm.dinh.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.activity.LoginActivity;
import com.farm.dinh.ui.viewmodel.UserViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.UpdateInfoState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static android.app.Activity.RESULT_OK;
import static com.farm.dinh.local.Pref.KEY_LOGOUT;

public class UserDetailFragment extends Fragment {
    private EditText name, phone, address, oldPass, newPass, newPassAgain;
    private CheckBox changePass;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(UserViewModel.class);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        oldPass = view.findViewById(R.id.oldPass);
        newPass = view.findViewById(R.id.newPass);
        newPassAgain = view.findViewById(R.id.newPassAgain);
        final TextInputLayout inputOldPass = view.findViewById(R.id.inputOldPass);
        final TextInputLayout inputNewPass = view.findViewById(R.id.inputNewPass);
        final TextInputLayout inputNewPassAgain = view.findViewById(R.id.inputNewPassAgain);
        UIHelper.hideSoftKeyboard(null, phone);
        phone.setKeyListener(null);
        UIHelper.hideSoftKeyboard(null, address);
        address.setKeyListener(null);
        changePass = view.findViewById(R.id.changePass);
        final LinearLayout llChangePass = view.findViewById(R.id.llChangePass);
        final TextView txtNoData = view.findViewById(R.id.txt_NoData);
        final View llDetail = view.findViewById(R.id.llDetail);
        changePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llChangePass.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        Button btnLogout = view.findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                userViewModel.updateInfoDataChanged(name.getText().toString(), oldPass.getText().toString(),
                        newPass.getText().toString(), newPassAgain.getText().toString(), changePass.isChecked());
            }
        };
        name.addTextChangedListener(afterTextChangedListener);
        oldPass.addTextChangedListener(afterTextChangedListener);
        newPass.addTextChangedListener(afterTextChangedListener);
        newPassAgain.addTextChangedListener(afterTextChangedListener);
        userViewModel.getUpdateInfoState().observe(this, new Observer<UpdateInfoState>() {
            @Override
            public void onChanged(UpdateInfoState updateInfoState) {
                if (updateInfoState == null) {
                    return;
                }
                if (updateInfoState.getUsernameError() != null) {
                    name.setError(getString(updateInfoState.getUsernameError()));
                }
                if (updateInfoState.getOldPasswordError() != null) {
                    inputOldPass.setError(getString(updateInfoState.getOldPasswordError()));
                } else {
                    inputOldPass.setError(null);
                }
                if (updateInfoState.getNewPasswordError() != null) {
                    inputNewPass.setError(getString(updateInfoState.getNewPasswordError()));
                } else {
                    inputNewPass.setError(null);
                }
                if (updateInfoState.getNewPasswordAgainError() != null) {
                    inputNewPassAgain.setError(getString(updateInfoState.getNewPasswordAgainError()));
                } else {
                    inputNewPassAgain.setError(null);
                }
            }
        });
        userViewModel.getResult().observe(this, new Observer<ViewResult<UserInfo>>() {
            @Override
            public void onChanged(ViewResult<UserInfo> userInfoViewResult) {
                if (userInfoViewResult == null) {
                    return;
                }
                if (userInfoViewResult.getError() != null) {
                    if (userInfoViewResult.isUpdate()) {
                        Toast.makeText(getContext(), userInfoViewResult.getError(), Toast.LENGTH_SHORT).show();
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        llDetail.setVisibility(View.GONE);
                    }
                }
                if (userInfoViewResult.getSuccess() != null) {
                    if (userInfoViewResult.isUpdate()) {
                        changePass.setChecked(false);
                        UIHelper.showMessageDialog(getContext(), getString(R.string.prompt_update_success));
                    } else {
                        txtNoData.setVisibility(View.GONE);
                        llDetail.setVisibility(View.VISIBLE);
                    }
                    setDataToView(userInfoViewResult.getSuccess());
                }
            }
        });
        userViewModel.getUserInfo();
    }

    private void logout() {
        userViewModel.logout();
        getActivity().setResult(RESULT_OK);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(KEY_LOGOUT, true);
        startActivity(intent);
        getActivity().finish();
    }

    private void setDataToView(UserInfo userInfo) {
        name.setText(userInfo.getName());
        phone.setText(userInfo.getPhone());
        address.setText(userInfo.getAddress());
    }

    private void updateUserInfo() {
        UIHelper.hideSoftKeyboard(null, name);
        UIHelper.hideSoftKeyboard(null, phone);
        UIHelper.hideSoftKeyboard(null, address);
        UIHelper.hideSoftKeyboard(null, oldPass);
        UIHelper.hideSoftKeyboard(null, newPass);
        UIHelper.hideSoftKeyboard(null, newPassAgain);
        userViewModel.updateUserInfo(name.getText().toString(), oldPass.getText().toString(), newPass.getText().toString(), changePass.isChecked());
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
            updateUserInfo();
        }
        return super.onOptionsItemSelected(item);
    }
}
