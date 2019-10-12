package com.farm.dinh.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextChangeDelayAdapter implements TextWatcher {
    private Runnable notifyChangeTask;
    protected long delayMs = 200;
    protected String latestString;
    protected EditText editText;

    public TextChangeDelayAdapter(final EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.delay(s == null ? "" : s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void delay(String s) {
        if (this.notifyChangeTask != null) {
            this.editText.removeCallbacks(this.notifyChangeTask);
            this.notifyChangeTask = null;
        }
        this.notifyChangeTask = new Runnable() {
            @Override
            public void run() {
                TextChangeDelayAdapter.this.afterDelay(TextChangeDelayAdapter.this.latestString);
                notifyChangeTask = null;
            }
        };
        this.latestString = s;
        this.editText.postDelayed(this.notifyChangeTask, this.getDelayMs());
    }

    protected long getDelayMs() {
        return this.delayMs;
    }

    public void afterDelay(String s) {
    }
}