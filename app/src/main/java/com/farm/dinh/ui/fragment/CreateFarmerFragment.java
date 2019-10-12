package com.farm.dinh.ui.fragment;

import android.content.DialogInterface;
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

import com.farm.dinh.R;
import com.farm.dinh.data.model.City;
import com.farm.dinh.data.model.District;
import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.data.model.FarmerInfo;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

public class CreateFarmerFragment extends Fragment {
    private CreateFarmerViewModel viewModel;
    private TextInputEditText phone, name, street, area;
    private MaterialSpinner spinnerCity, spinnerDistrict, spinnerWard;
    private TextInputLayout inputCity, inputDistrict, inputWard, inputPhone, inputName, inputStreet, inputArea;
    private ArrayAdapter<City> adapterCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_farmer, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FarmerInfo farmerInfo = (FarmerInfo) getArguments().getSerializable("FarmerInfo");
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateFarmerViewModel.class);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);
        street = view.findViewById(R.id.street);
        area = view.findViewById(R.id.area);
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
        inputArea = view.findViewById(R.id.inputArea);
        final Button managerTree = view.findViewById(R.id.managerTree);
        managerTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (farmerInfo == null) return;
                Bundle bundle = new Bundle();
                bundle.putInt("FarmerId", farmerInfo.getId());
                navController.navigate(R.id.action_createFarmerFragment_to_treeManagerFragment, bundle);
            }
        });
        final TextWatcher afterTextChangedListener = new TextWatcher() {
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
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()), area.getText().toString());
            }
        };

        viewModel.getListAddress().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities == null) return;
                adapterCity = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cities);
                adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(adapterCity);
                viewModel.getFarmerInfoLiveData().setValue(farmerInfo);
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
                    if (farmerInfo != null) {
                        spinnerDistrict.setSelection(getAdapterPosition(adapter, new District(farmerInfo.getDistrict())));
                    }
                    inputDistrict.setVisibility(View.VISIBLE);
                    inputWard.setVisibility(View.GONE);
                } else {
                    inputDistrict.setVisibility(View.GONE);
                    inputDistrict.setError(null);
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                    viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                            ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()), area.getText().toString());
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
                    ArrayAdapter<Ward> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, district.getWard());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWard.setAdapter(adapter);
                    if (farmerInfo != null) {
                        spinnerWard.setSelection(getAdapterPosition(adapter, new Ward(farmerInfo.getWard())));
                    }
                    inputWard.setVisibility(View.VISIBLE);
                } else {
                    inputWard.setVisibility(View.GONE);
                    inputWard.setError(null);
                    viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                            ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()), area.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.checkDataChange(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                        ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()), area.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getStateLiveData().observe(this, new Observer<CreateFarmerState>() {
            @Override
            public void onChanged(CreateFarmerState createFarmerState) {
                if (createFarmerState == null) return;
                inputCity.setError(null);
                inputDistrict.setError(null);
                inputWard.setError(null);
                if (createFarmerState.getPhoneError() != null) {
                    inputPhone.setError(getString(createFarmerState.getPhoneError()));
                } else {
                    inputPhone.setError(null);
                }
                if (createFarmerState.getNameError() != null) {
                    inputName.setError(getString(createFarmerState.getNameError()));
                } else {
                    inputName.setError(null);
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
                } else {
                    inputStreet.setError(null);
                }
                if (createFarmerState.getAreaError() != null) {
                    inputArea.setError(getString(createFarmerState.getAreaError()));
                } else {
                    inputArea.setError(null);
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
                    UIHelper.showMessageDialog(getContext(), getContext().getResources().getString(farmerInfoViewResult.isUpdate() ? R.string.edit_farmer_success : R.string.create_farmer_success),
                            getContext().getResources().getString(R.string.title_success), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                                }
                            });
                }
            }
        });
        viewModel.getFarmerInfoLiveData().observe(this, new Observer<FarmerInfo>() {
            @Override
            public void onChanged(FarmerInfo info) {
                managerTree.setVisibility(info == null ? View.GONE : View.VISIBLE);
                if (info == null) return;
                phone.removeTextChangedListener(afterTextChangedListener);
                name.removeTextChangedListener(afterTextChangedListener);
                street.removeTextChangedListener(afterTextChangedListener);
                area.removeTextChangedListener(afterTextChangedListener);
                phone.setText(farmerInfo.getPhone());
                name.setText(farmerInfo.getName());
                street.setText(farmerInfo.getStreet());
                area.setText(String.valueOf(farmerInfo.getArea()));
                spinnerCity.setSelection(getAdapterPosition(adapterCity, new City(farmerInfo.getCity())));
                phone.addTextChangedListener(afterTextChangedListener);
                name.addTextChangedListener(afterTextChangedListener);
                street.addTextChangedListener(afterTextChangedListener);
                area.addTextChangedListener(afterTextChangedListener);
            }
        });
        viewModel.getAddress();
    }

    private <T> int getAdapterPosition(ArrayAdapter<T> arrayAdapter, T data) {
        return arrayAdapter.getPosition(data) + 1;
    }

    private void setupSpinner() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
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

    private void hideSoftKeyboard() {
        UIHelper.hideSoftKeyboard(null, phone);
        UIHelper.hideSoftKeyboard(null, name);
        UIHelper.hideSoftKeyboard(null, street);
        UIHelper.hideSoftKeyboard(null, area);
    }

    private void processFarmer() {
        hideSoftKeyboard();
        viewModel.processFarmer(phone.getText().toString(), name.getText().toString(), street.getText().toString(),
                ((City) spinnerCity.getSelectedItem()), ((District) spinnerDistrict.getSelectedItem()), ((Ward) spinnerWard.getSelectedItem()), area.getText().toString());
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
            processFarmer();
        }
        return super.onOptionsItemSelected(item);
    }
}
