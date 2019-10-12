package com.farm.dinh.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.data.model.Ward;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.activity.LoginActivity;
import com.farm.dinh.ui.viewmodel.UserViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.UpdateInfoState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.farm.dinh.local.Pref.KEY_LOGOUT;

public class UserDetailFragment extends Fragment {
    private EditText name, phone, street, oldPass, newPass, newPassAgain;
    private CheckBox changePass;
    private UserViewModel userViewModel;
    private MaterialSpinner spinnerCity, spinnerDistrict, spinnerWard;
    private TextInputLayout inputCity, inputDistrict, inputWard, inputName, inputStreet;

    private TextWatcher afterTextChangedListener;
    private ArrayAdapter<City> adapterCity;
    private ArrayAdapter<District> adapterDistrict;
    private ArrayAdapter<Ward> adapterWard;

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
        street = view.findViewById(R.id.street);
        spinnerCity = view.findViewById(R.id.city);
        spinnerDistrict = view.findViewById(R.id.district);
        spinnerWard = view.findViewById(R.id.ward);
        setupSpinner();
        inputCity = view.findViewById(R.id.inputCity);
        inputDistrict = view.findViewById(R.id.inputDistrict);
        inputWard = view.findViewById(R.id.inputWard);
        inputName = view.findViewById(R.id.inputName);
        inputStreet = view.findViewById(R.id.inputStreet);

        oldPass = view.findViewById(R.id.oldPass);
        newPass = view.findViewById(R.id.newPass);
        newPassAgain = view.findViewById(R.id.newPassAgain);
        final TextInputLayout inputOldPass = view.findViewById(R.id.inputOldPass);
        final TextInputLayout inputNewPass = view.findViewById(R.id.inputNewPass);
        final TextInputLayout inputNewPassAgain = view.findViewById(R.id.inputNewPassAgain);
        userViewModel.getListAddress().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities == null) return;
                userViewModel.getUserInfo();
                adapterCity = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cities);
                adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(adapterCity);
                inputCity.setVisibility(View.VISIBLE);
                inputDistrict.setVisibility(View.GONE);
                inputDistrict.setError(null);
                inputWard.setVisibility(View.GONE);
                inputWard.setError(null);
            }
        });
        UIHelper.hideSoftKeyboard(null, phone);
        phone.setKeyListener(null);
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
        btnLogout.setVisibility(userViewModel.isAgency() ? View.GONE : View.VISIBLE);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userViewModel.updateInfoDataChanged(name.getText().toString(), street.getText().toString(), (City) spinnerCity.getSelectedItem(), (District) spinnerDistrict.getSelectedItem(),
                        (Ward) spinnerWard.getSelectedItem(), oldPass.getText().toString(), newPass.getText().toString(), newPassAgain.getText().toString(), changePass.isChecked());
            }
        };

        userViewModel.getUpdateInfoState().observe(this, new Observer<UpdateInfoState>() {
            @Override
            public void onChanged(UpdateInfoState updateInfoState) {
                if (updateInfoState == null) {
                    return;
                }
                if (updateInfoState.getUsernameError() != null) {
                    inputName.setError(getString(updateInfoState.getUsernameError()));
                } else {
                    inputName.setError(null);
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

                inputCity.setError(null);
                inputDistrict.setError(null);
                inputWard.setError(null);
                if (updateInfoState.getCityError() != null) {
                    inputCity.setError(getString(updateInfoState.getCityError()));
                }
                if (updateInfoState.getDistrictError() != null) {
                    inputDistrict.setError(getString(updateInfoState.getDistrictError()));
                }
                if (updateInfoState.getWardError() != null) {
                    inputWard.setError(getString(updateInfoState.getWardError()));
                }
                if (updateInfoState.getStreetError() != null) {
                    inputStreet.setError(getString(updateInfoState.getStreetError()));
                } else {
                    inputStreet.setError(null);
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
                        UIHelper.showMessageDialog(getContext(), userInfoViewResult.getError(), getResources().getString(R.string.title_fail));
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        llDetail.setVisibility(View.GONE);
                    }
                }
                if (userInfoViewResult.getSuccess() != null) {
                    if (userInfoViewResult.isUpdate()) {
                        changePass.setChecked(false);
                        UIHelper.showMessageDialog(getContext(), getString(R.string.prompt_update_success), getResources().getString(R.string.title_success));
                    } else {
                        txtNoData.setVisibility(View.GONE);
                        llDetail.setVisibility(View.VISIBLE);
                    }
                    setDataToView(userInfoViewResult.getSuccess());
                }
            }
        });
        userViewModel.getAddress();
    }

    private void setupSpinner() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UIHelper.hideSoftKeyboard(null, phone);
                UIHelper.hideSoftKeyboard(null, name);
                UIHelper.hideSoftKeyboard(null, street);
                UIHelper.hideSoftKeyboard(null, oldPass);
                UIHelper.hideSoftKeyboard(null, newPass);
                UIHelper.hideSoftKeyboard(null, newPassAgain);
                return false;
            }
        };
        spinnerCity.setPaddingSafe(0, 0, 0, 0);
        spinnerDistrict.setPaddingSafe(0, 0, 0, 0);
        spinnerWard.setPaddingSafe(0, 0, 0, 0);
        UIHelper.setHeightSpinner(spinnerCity);
        UIHelper.setHeightSpinner(spinnerDistrict);
        UIHelper.setHeightSpinner(spinnerWard);
        spinnerCity.setOnTouchListener(touchListener);
        spinnerDistrict.setOnTouchListener(touchListener);
        spinnerWard.setOnTouchListener(touchListener);
    }

    private void logout() {
        userViewModel.logout();
        getActivity().setResult(RESULT_OK);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(KEY_LOGOUT, true);
        startActivity(intent);
        getActivity().finish();
    }

    private void setDataToView(final UserInfo userInfo) {
        name.removeTextChangedListener(afterTextChangedListener);
        street.removeTextChangedListener(afterTextChangedListener);
        oldPass.removeTextChangedListener(afterTextChangedListener);
        newPass.removeTextChangedListener(afterTextChangedListener);
        newPassAgain.removeTextChangedListener(afterTextChangedListener);
        name.setText(userInfo.getName());
        phone.setText(userInfo.getPhone());
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) spinnerCity.getSelectedItem();
                if (city != null) {
                    adapterDistrict = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, city.getDistrict());
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDistrict.setAdapter(adapterDistrict);
                    spinnerDistrict.setSelection(getAdapterPosition(adapterDistrict, new District(userInfo.getDistrict())));
                    inputDistrict.setVisibility(View.VISIBLE);
                    inputWard.setVisibility(View.GONE);
                } else {
                    inputDistrict.setVisibility(View.GONE);
                    inputDistrict.setError(null);
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                    userViewModel.updateInfoDataChanged(name.getText().toString(), street.getText().toString(), (City) spinnerCity.getSelectedItem(), (District) spinnerDistrict.getSelectedItem(),
                            (Ward) spinnerWard.getSelectedItem(), oldPass.getText().toString(), newPass.getText().toString(), newPassAgain.getText().toString(), changePass.isChecked());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                District district = (District) spinnerDistrict.getSelectedItem();
                if (district != null) {
                    adapterWard = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, district.getWard());
                    adapterWard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWard.setAdapter(adapterWard);
                    spinnerWard.setSelection(getAdapterPosition(adapterWard, new Ward(userInfo.getWard())));
                    inputWard.setVisibility(View.VISIBLE);
                } else {
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                    userViewModel.updateInfoDataChanged(name.getText().toString(), street.getText().toString(), (City) spinnerCity.getSelectedItem(), (District) spinnerDistrict.getSelectedItem(),
                            (Ward) spinnerWard.getSelectedItem(), oldPass.getText().toString(), newPass.getText().toString(), newPassAgain.getText().toString(), changePass.isChecked());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userViewModel.updateInfoDataChanged(name.getText().toString(), street.getText().toString(), (City) spinnerCity.getSelectedItem(), (District) spinnerDistrict.getSelectedItem(),
                        (Ward) spinnerWard.getSelectedItem(), oldPass.getText().toString(), newPass.getText().toString(), newPassAgain.getText().toString(), changePass.isChecked());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCity.setSelection(getAdapterPosition(adapterCity, new City(userInfo.getCity())));
        street.setText(userInfo.getStreet());
        name.addTextChangedListener(afterTextChangedListener);
        street.addTextChangedListener(afterTextChangedListener);
        oldPass.addTextChangedListener(afterTextChangedListener);
        newPass.addTextChangedListener(afterTextChangedListener);
        newPassAgain.addTextChangedListener(afterTextChangedListener);
    }

    private <T> int getAdapterPosition(ArrayAdapter<T> arrayAdapter, T data) {
        return arrayAdapter.getPosition(data) + 1;
    }

    private void updateUserInfo() {
        UIHelper.hideSoftKeyboard(null, name);
        UIHelper.hideSoftKeyboard(null, phone);
        UIHelper.hideSoftKeyboard(null, oldPass);
        UIHelper.hideSoftKeyboard(null, newPass);
        UIHelper.hideSoftKeyboard(null, newPassAgain);
        userViewModel.updateUserInfo(name.getText().toString(), ((District) spinnerDistrict.getSelectedItem()).getName(),
                street.getText().toString(), ((Ward) spinnerWard.getSelectedItem()).getName(), ((City) spinnerCity.getSelectedItem()).getName(), null,
                oldPass.getText().toString(), newPass.getText().toString(), changePass.isChecked());
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
