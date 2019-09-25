package com.farm.dinh.ui.fragment;

import android.os.Build;
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

import com.farm.dinh.R;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.data.model.Ward;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.viewmodel.CreateFarmerViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.CreateFarmerState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class CreateFarmerFragment extends Fragment {
    private CreateFarmerViewModel viewModel;
    private TextInputEditText phone, name, street;
    private MaterialSpinner spinnerCity, spinnerDistrict, spinnerWard;
    private TextInputLayout inputCity, inputDistrict, inputWard, inputPhone, inputName, inputStreet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_farmer, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateFarmerViewModel.class);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);
        street = view.findViewById(R.id.street);
        spinnerCity = view.findViewById(R.id.city);
        spinnerDistrict = view.findViewById(R.id.district);
        spinnerWard = view.findViewById(R.id.ward);
        setupSpinner();
        inputCity = view.findViewById(R.id.inputCity);
        inputDistrict = view.findViewById(R.id.inputDistrict);
        inputWard = view.findViewById(R.id.inputWard);
        inputPhone = view.findViewById(R.id.inputPhone);
        inputName = view.findViewById(R.id.inputName);
        inputStreet = view.findViewById(R.id.inputStreet);

        viewModel.getListAddress().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities == null) return;
                ArrayAdapter<City> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cities);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(adapter);
                inputCity.setVisibility(View.VISIBLE);
                inputDistrict.setVisibility(View.GONE);
                inputDistrict.setError(null);
                inputWard.setVisibility(View.GONE);
                inputWard.setError(null);
            }
        });
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) spinnerCity.getSelectedItem();
                if (city != null) {
                    ArrayAdapter<District> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, city.getDistrict());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDistrict.setAdapter(adapter);
                    inputDistrict.setVisibility(View.VISIBLE);
                    inputWard.setVisibility(View.GONE);
                } else {
                    inputDistrict.setVisibility(View.GONE);
                    inputDistrict.setError(null);
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                }
                viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()));
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
                    ArrayAdapter<Ward> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, district.getWard());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWard.setAdapter(adapter);
                    inputWard.setVisibility(View.VISIBLE);
                } else {
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                }
                viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()));
            }
        };
        phone.addTextChangedListener(afterTextChangedListener);
        name.addTextChangedListener(afterTextChangedListener);
        street.addTextChangedListener(afterTextChangedListener);
        viewModel.getStateLiveData().observe(this, new Observer<CreateFarmerState>() {
            @Override
            public void onChanged(CreateFarmerState createFarmerState) {
                if (createFarmerState == null) return;
                inputPhone.setError(null);
                inputName.setError(null);
                inputCity.setError(null);
                inputDistrict.setError(null);
                inputWard.setError(null);
                inputStreet.setError(null);
                if (createFarmerState.getPhoneError() != null) {
                    inputPhone.setError(getString(createFarmerState.getPhoneError()));
                }
                if (createFarmerState.getNameError() != null) {
                    inputName.setError(getString(createFarmerState.getNameError()));
                }
                if (createFarmerState.getCityError() != null) {
                    inputCity.setError(getString(createFarmerState.getCityError()));
                }
                if (createFarmerState.getDistrictError() != null) {
                    inputDistrict.setError(getString(createFarmerState.getDistrictError()));
                }
                if (createFarmerState.getWardError() != null) {
                    inputWard.setError(getString(createFarmerState.getWardError()));
                }
                if (createFarmerState.getStreetError() != null) {
                    inputStreet.setError(getString(createFarmerState.getStreetError()));
                }
            }
        });
        viewModel.getResult().observe(this, new Observer<ViewResult<FarmerInfo>>() {
            @Override
            public void onChanged(ViewResult<FarmerInfo> farmerInfoViewResult) {
                if (farmerInfoViewResult == null) return;

                if (farmerInfoViewResult.getError() != null) {
                    UIHelper.showMessageDialog(getContext(), farmerInfoViewResult.getError(), getContext().getResources().getString(R.string.title_fail));
                } else if (farmerInfoViewResult.getSuccess() != null) {
                    UIHelper.showMessageDialog(getContext(), getContext().getResources().getString(R.string.create_farmer_success),
                            getContext().getResources().getString(R.string.title_success));
                }
            }
        });
        viewModel.getAddress();
    }

    private void setupSpinner() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UIHelper.hideSoftKeyboard(null, phone);
                UIHelper.hideSoftKeyboard(null, name);
                UIHelper.hideSoftKeyboard(null, street);
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

    private void createFarmer() {
        viewModel.createFarmer(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()));
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
            createFarmer();
        }
        return super.onOptionsItemSelected(item);
    }
}
