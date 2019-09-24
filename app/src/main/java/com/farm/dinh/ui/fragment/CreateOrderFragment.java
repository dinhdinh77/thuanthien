package com.farm.dinh.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.viewmodel.CreateOrderViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.CreateOrderState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CreateOrderFragment extends Fragment {
    private TextInputEditText phone;
    private TextInputEditText name;
    private TextInputEditText quantity;
    private MaterialSpinner spinnerProduct;
    private CreateOrderViewModel model;
    private boolean firstSetListProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_order, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateOrderViewModel.class);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);
        quantity = view.findViewById(R.id.quantity);
        spinnerProduct = view.findViewById(R.id.spinner);
        UIHelper.setHeightSpinner(spinnerProduct);
        final TextInputLayout inputPhone = view.findViewById(R.id.inputPhone);
        final TextInputLayout inputName = view.findViewById(R.id.inputName);
        final TextInputLayout inputProduct = view.findViewById(R.id.inputProduct);
        final TextInputLayout inputQuantity = view.findViewById(R.id.inputQuantity);
        firstSetListProduct = true;

        spinnerProduct.setPaddingSafe(0, 0, 0, 0);
        spinnerProduct.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstSetListProduct) {
                    firstSetListProduct = false;
                    return;
                }
                model.createOrderDataChange(phone.getText().toString(), quantity.getText().toString(), ((Product) spinnerProduct.getSelectedItem()));
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
                model.createOrderDataChange(phone.getText().toString(), quantity.getText().toString(), ((Product) spinnerProduct.getSelectedItem()));
            }
        };

        model.getResult().observe(this, new Observer<ViewResult<List<Product>>>() {
            @Override
            public void onChanged(ViewResult<List<Product>> viewResult) {
                if (viewResult == null) {
                    return;
                }
                if (viewResult.getError() != null) {
                    Toast.makeText(getContext(), viewResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if (viewResult.getSuccess() != null) {
                    ArrayAdapter<Product> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, viewResult.getSuccess());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProduct.setAdapter(adapter);
                }
            }
        });

        name.setKeyListener(null);
        phone.addTextChangedListener(afterTextChangedListener);
        quantity.addTextChangedListener(afterTextChangedListener);
        model.getLiveDataViewState().observe(this, new Observer<CreateOrderState>() {
            @Override
            public void onChanged(CreateOrderState createOrderState) {
                if (createOrderState == null) return;
                if (createOrderState.getPhoneError() != null) {
                    inputPhone.setError(getString(createOrderState.getPhoneError()));
                } else {
                    inputPhone.setError(null);
                }
                if (createOrderState.getName() != null) {
                    inputName.setVisibility(View.VISIBLE);
                    inputName.setError(null);
                    name.setText(createOrderState.getName());
                } else {
                    inputName.setVisibility(View.GONE);
                }
                if (createOrderState.getProductError() != null) {
                    inputProduct.setError(getString(createOrderState.getProductError()));
                } else {
                    inputProduct.setError(null);
                }
                if (createOrderState.getQuantityError() != null) {
                    inputQuantity.setError(getString(createOrderState.getQuantityError()));
                } else {
                    inputQuantity.setError(null);
                }
            }
        });
        model.getLiveDataResultCreateOrder().observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> booleanStringPair) {
                UIHelper.showMessageDialog(getContext(), booleanStringPair.second, getContext().getResources().getString(booleanStringPair.first ?
                        R.string.title_success : R.string.title_fail));
            }
        });
        model.getProductsList();
    }

    private void hideSoftKeyboard() {
        UIHelper.hideSoftKeyboard(null, phone);
        UIHelper.hideSoftKeyboard(null, name);
        UIHelper.hideSoftKeyboard(null, quantity);
    }

    private void createOrder() {
        hideSoftKeyboard();
        model.createOrder(phone.getText().toString(), quantity.getText().toString(), ((Product) spinnerProduct.getSelectedItem()));
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
            createOrder();
        }
        return super.onOptionsItemSelected(item);
    }
}
