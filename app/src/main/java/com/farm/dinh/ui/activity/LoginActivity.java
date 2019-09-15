package com.farm.dinh.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.helper.UIHelper;
import com.farm.dinh.ui.viewmodel.model.LoggedInUserView;
import com.farm.dinh.ui.viewmodel.LoginViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.LoginFormState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.farm.dinh.local.Pref.KEY_LOGOUT;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextInputLayout inputPassword = findViewById(R.id.inputPassword);
        final TextInputLayout inputUsername = findViewById(R.id.inputUsername);

        loginViewModel.getPreviousUser().observe(this, new Observer<Pair<String, String>>() {
            @Override
            public void onChanged(Pair<String, String> previousUser) {
                if (previousUser == null) {
                    return;
                }

                if (!TextUtils.isEmpty(previousUser.first)) {
                    usernameEditText.setText(previousUser.first);
                }
                if (!TextUtils.isEmpty(previousUser.second)) {
                    passwordEditText.setText(previousUser.second);
                }

                boolean isActionLogout = getIntent().getBooleanExtra(KEY_LOGOUT, false);
                if (!TextUtils.isEmpty(previousUser.first) && !TextUtils.isEmpty(previousUser.second) && !isActionLogout) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(previousUser.first, previousUser.second);
                }
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    inputUsername.setError(getString(loginFormState.getUsernameError()));
                } else {
                    inputUsername.setError(null);
                }

                if (loginFormState.getPasswordError() != null) {
                    inputPassword.setError(getString(loginFormState.getPasswordError()));
                } else {
                    inputPassword.setError(null);
                }
            }
        });

        loginViewModel.getResult().observe(this, new Observer<ViewResult<LoggedInUserView>>() {
            @Override
            public void onChanged(@Nullable ViewResult<LoggedInUserView> loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.hideSoftKeyboard(null, usernameEditText);
                UIHelper.hideSoftKeyboard(null, passwordEditText);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        loginViewModel.getAutoFillUser();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        setResult(RESULT_OK);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
