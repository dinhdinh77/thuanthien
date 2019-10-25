package com.farm.dinh.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Product;
import com.farm.dinh.helper.InputFilterMinMax;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.iinterface.OnItemClick;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products = new ArrayList<>();
    private ArrayAdapter<Product> arrayAdapter;
    private OnItemClick<Product> productOnItemClick;

    public ProductAdapter() {
        this.products.add(createDefaultProduct());
    }

    public void setProductOnItemClick(OnItemClick<Product> productOnItemClick) {
        this.productOnItemClick = productOnItemClick;
    }

    public void setProductList(List<Product> products) {
        if (products == null) {
            products = new ArrayList<>();
            products.add(createDefaultProduct());
        }
        this.products = products;
        notifyDataSetChanged();
    }

    public void setArrayAdapter(ArrayAdapter<Product> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
        notifyDataSetChanged();
    }

    public List<Product> getProductList() {
        return this.products;
    }

    private Product createDefaultProduct() {
        Product product = new Product();
        product.setId(Integer.MAX_VALUE - products.size());
        product.setQuantity(1);
        return product;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.initData(products.get(position), position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    protected class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
        private ImageButton btnProduct;
        private Spinner spinner;
        private TextInputEditText quantity;
        private Context context;
        private long currentTimeMillis;
        private int index;
        private Product product;

        public ProductViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            btnProduct = itemView.findViewById(R.id.btnProduct);
            spinner = itemView.findViewById(R.id.spinner);
            quantity = itemView.findViewById(R.id.quantity);
            UIHelper.setHeightSpinner(spinner);
            spinner.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    UIHelper.hideSoftKeyboard(null, quantity);
                    return false;
                }
            });
            quantity.setFilters(new InputFilter[]{new InputFilterMinMax(1, Integer.MAX_VALUE)});
            quantity.addTextChangedListener(this);
            spinner.setOnItemSelectedListener(this);
            btnProduct.setOnClickListener(this);
        }

        public void initData(Product product, int index) {
            this.product = product;
            this.index = index;
            btnProduct.setBackgroundResource(index == 0 ? R.drawable.ic_add_product : R.drawable.ic_remove_product);
            if (arrayAdapter != null) {
                spinner.setAdapter(arrayAdapter);
                spinner.setSelection(arrayAdapter.getPosition(product));
                setQuantity(product.getQuantity());
            }
        }

        private void setQuantity(int quantity) {
            this.quantity.removeTextChangedListener(this);
            this.quantity.setText(String.valueOf(quantity));
            this.quantity.setSelection(this.quantity.getText().length());
            if (quantity > 0) this.quantity.setError(null);
            else
                this.quantity.setError(context.getResources().getString(R.string.invalid_quantity));
            this.quantity.addTextChangedListener(this);
        }

        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() - currentTimeMillis < 500) return;
            currentTimeMillis = System.currentTimeMillis();
            if (productOnItemClick != null) productOnItemClick.onClick(this.product);
            if (index == 0) {
                products.add(createDefaultProduct());
                notifyItemInserted(products.size() - 1);
            } else if (index > 0) {
                products.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, products.size() - index + 1);
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Product product = (Product) spinner.getSelectedItem();
            if (!product.equals(this.product)) {
                this.product.setId(product.getId());
                this.product.setName(product.getName());
                notifyItemChanged(index);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int quantity = 0;
            try {
                quantity = Integer.valueOf(s.toString());
            } catch (NumberFormatException e) {
            } finally {
                setQuantity(quantity);
            }
            product.setQuantity(quantity);
        }
    }
}
