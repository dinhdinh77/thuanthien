package com.farm.dinh.ui.viewmodel.model;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private int isAgency;

    public LoggedInUserView(String displayName, int isAgency) {
        this.displayName = displayName;
        this.isAgency = isAgency;
    }

    String getDisplayName() {
        return displayName;
    }

    public int getIsAgency() {
        return isAgency;
    }
}
