package com.farm.dinh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.viewmodel.CreateOrderViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CreateOrderFragment extends Fragment {
    private static final String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_order, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreateOrderViewModel model = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateOrderViewModel.class);
        final TextInputEditText phone = view.findViewById(R.id.phone);
        final TextInputEditText name = view.findViewById(R.id.name);
        final TextInputEditText quantity = view.findViewById(R.id.quantity);
        final MaterialSpinner spinner = view.findViewById(R.id.spinner);
        spinner.setPaddingSafe(0, 0, 0, 0);

        spinner.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UIHelper.hideSoftKeyboard(null, phone);
                UIHelper.hideSoftKeyboard(null, name);
                UIHelper.hideSoftKeyboard(null, quantity);
                return false;
            }
        });

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
                    spinner.setAdapter(adapter);
                }
            }
        });
        model.getProductsList();
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
