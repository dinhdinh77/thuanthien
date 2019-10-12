package com.farm.dinh.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.farm.dinh.data.model.Order;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.adapter.ProductAdapter;
import com.farm.dinh.ui.iinterface.OnItemClick;
import com.farm.dinh.ui.viewmodel.CreateOrderViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.CreateOrderState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateOrderFragment extends Fragment {
    private TextInputEditText phone;
    private TextInputEditText name;
    private RecyclerView lvProduct;
    private ProductAdapter productAdapter;
    private CreateOrderViewModel model;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_order, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Order order = (Order) getArguments().getSerializable("Order");
        model = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateOrderViewModel.class);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);
        lvProduct = view.findViewById(R.id.lv_product);
        lvProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        lvProduct.setHasFixedSize(true);
        final TextInputLayout inputPhone = view.findViewById(R.id.inputPhone);
        final TextInputLayout inputName = view.findViewById(R.id.inputName);
        productAdapter = new ProductAdapter();
        productAdapter.setProductOnItemClick(new OnItemClick<Product>() {
            @Override
            public void onClick(Product data) {
                hideSoftKeyboard();
            }
        });
        lvProduct.setAdapter(productAdapter);

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
                if (s == phone.getEditableText()) {
                    if (TextUtils.isEmpty(s.toString())) {
                        inputPhone.setError(getString(R.string.invalid_username));
                    } else {
                        inputPhone.setError(null);
                    }
                } else if (s == name.getEditableText()) {
                    if (TextUtils.isEmpty(s.toString())) {
                        inputName.setError(getString(R.string.invalid_name));
                    } else {
                        inputName.setError(null);
                    }
                }
            }
        };

        phone.addTextChangedListener(afterTextChangedListener);
        name.addTextChangedListener(afterTextChangedListener);

        model.getResult().observe(this, new Observer<ViewResult<List<Product>>>() {
            @Override
            public void onChanged(ViewResult<List<Product>> viewResult) {
                if (dialog != null) dialog.hide();
                if (viewResult == null) {
                    return;
                }
                model.setOrderData(order);
                if (viewResult.getError() != null) {
                    Toast.makeText(getContext(), viewResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if (viewResult.getSuccess() != null) {
                    ArrayAdapter<Product> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, viewResult.getSuccess());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    productAdapter.setArrayAdapter(adapter);
                }
            }
        });
        model.getOrderMutableLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                if (order == null) return;
                if (TextUtils.isEmpty(order.getPhone())) {
                    inputPhone.setError(getString(R.string.invalid_username));
                } else {
                    phone.setText(order.getPhone());
                    inputPhone.setError(null);
                }
                if (TextUtils.isEmpty(order.getName())) {
                    inputName.setError(getString(R.string.invalid_name));
                } else {
                    name.setText(order.getName());
                    inputName.setError(null);
                }

                productAdapter.setProductList(order.getProducts());
            }
        });
        model.getLiveDataViewState().observe(this, new Observer<CreateOrderState>() {
            @Override
            public void onChanged(CreateOrderState createOrderState) {
                if (createOrderState == null) return;
                if (createOrderState.getPhoneError() != null) {
                    inputPhone.setError(getString(createOrderState.getPhoneError()));
                }
                if (createOrderState.getNameError() != null) {
                    inputName.setError(getString(R.string.invalid_name));
                }
                if (createOrderState.getQuantityIndexError() != null) {
                    productAdapter.notifyItemChanged(createOrderState.getQuantityIndexError());
                }
            }
        });
        model.getLiveDataResultCreateOrder().observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(final Pair<Boolean, String> booleanStringPair) {
                UIHelper.showMessageDialog(getContext(), booleanStringPair.second, getContext().getResources().getString(booleanStringPair.first ?
                        R.string.title_success : R.string.title_fail), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (booleanStringPair.first)
                            requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    }
                });
            }
        });
        dialog = ProgressDialog.show(getActivity(), "", getContext().getResources().getString(R.string.message_loading), true);
        model.getProductsList();
    }

    private void hideSoftKeyboard() {
        UIHelper.hideSoftKeyboard(null, phone);
        UIHelper.hideSoftKeyboard(null, name);
    }

    private void processOrder() {
        hideSoftKeyboard();
        model.processOrder(phone.getText().toString(), name.getText().toString(), productAdapter.getProductList());
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
            processOrder();
        }
        return super.onOptionsItemSelected(item);
    }

}
