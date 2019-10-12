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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.viewmodel.CreateTreeViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.custom.MaterialSpinner;
import com.farm.dinh.ui.viewmodel.model.CreateTreeState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CreateTreeFragment extends Fragment {
    private ArrayAdapter<Tree> treeAdapter;
    private TextInputEditText age, quantity;
    private MaterialSpinner treeSpinner;
    private CreateTreeViewModel viewModel;

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
            processTree();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processTree() {
        hideSoftKeyboard();
        viewModel.processTree((Tree) treeSpinner.getSelectedItem(), age.getText().toString(), quantity.getText().toString());
    }

    private void hideSoftKeyboard() {
        UIHelper.hideSoftKeyboard(null, age);
        UIHelper.hideSoftKeyboard(null, quantity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_tree, container, false);
        return root;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateTreeViewModel.class);
        final TreeInfo treeInfo = (TreeInfo) getArguments().getSerializable("TreeInfo");
        viewModel.setFarmerId(getArguments().getInt("FarmerId"));
        final TextInputLayout inputTree = view.findViewById(R.id.inputTree);
        final TextInputLayout inputTreeAge = view.findViewById(R.id.inputTreeAge);
        final TextInputLayout inputQuantity = view.findViewById(R.id.inputQuantity);
        treeSpinner = view.findViewById(R.id.tree);
        treeSpinner.setPaddingSafe(0, 0, 0, 0);
        UIHelper.setHeightSpinner(treeSpinner);
        treeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        age = view.findViewById(R.id.age);
        quantity = view.findViewById(R.id.quantity);

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
                viewModel.dataChange((Tree) treeSpinner.getSelectedItem(), age.getText().toString(), quantity.getText().toString());
            }
        };
        age.addTextChangedListener(afterTextChangedListener);
        quantity.addTextChangedListener(afterTextChangedListener);

        treeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.dataChange((Tree) treeSpinner.getSelectedItem(), age.getText().toString(), quantity.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getCreateTreeState().observe(this, new Observer<CreateTreeState>() {
            @Override
            public void onChanged(CreateTreeState createTreeState) {
                if (createTreeState == null) return;
                if (createTreeState.getTreeError() == null) {
                    inputTree.setError(null);
                } else {
                    inputTree.setError(getString(createTreeState.getTreeError()));
                }
                if (createTreeState.getAgeError() == null) {
                    inputTreeAge.setError(null);
                } else {
                    inputTreeAge.setError(getString(createTreeState.getAgeError()));
                }
                if (createTreeState.getQuantityError() == null) {
                    inputQuantity.setError(null);
                } else {
                    inputQuantity.setError(getString(createTreeState.getQuantityError()));
                }
            }
        });

        viewModel.getResult().observe(this, new Observer<ViewResult<List<Tree>>>() {
            @Override
            public void onChanged(ViewResult<List<Tree>> listViewResult) {
                if (listViewResult == null) return;
                treeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listViewResult.getSuccess());
                treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                treeSpinner.setAdapter(treeAdapter);
                viewModel.getTreeInfoLiveData().setValue(treeInfo);
            }
        });

        viewModel.getTreeInfoLiveData().observe(this, new Observer<TreeInfo>() {
            @Override
            public void onChanged(TreeInfo treeInfo) {
                if (treeInfo == null) return;
                age.removeTextChangedListener(afterTextChangedListener);
                quantity.removeTextChangedListener(afterTextChangedListener);
                age.setText(treeInfo.getAge());
                quantity.setText(String.valueOf(treeInfo.getAmount()));
                treeSpinner.setSelection(treeAdapter.getPosition(treeInfo) + 1);
                age.addTextChangedListener(afterTextChangedListener);
                quantity.addTextChangedListener(afterTextChangedListener);
            }
        });

        viewModel.getProcessTreeState().observe(this, new Observer<ViewResult>() {
            @Override
            public void onChanged(final ViewResult viewResult) {
                if (viewResult == null) return;
                UIHelper.showMessageDialog(getContext(), viewResult.getError(), getContext().getResources().getString(viewResult.isSuccess() ? R.string.title_success : R.string.title_fail),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (viewResult.isSuccess())
                                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                            }
                        });
            }
        });
        viewModel.getTreesList();
    }
}
