package com.farm.dinh.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.farm.dinh.TTApplication;

public class Pref {
    public static final String KEY_USER_ID = "CurrentUserId";
    public static final String KEY_PHONE = "CurrentPhone";
    public static final String KEY_PASS = "CurrentPass";
    public static final String KEY_FARMERS = "CurrentFarmers";
    public static final String KEY_ADDRESS = "CurrentAddress";
    public static final String KEY_LOGOUT = "Logout";
    private static String PREF_FILE_NAME = "pref.xml";

    private static Pref instance;

    public static Pref getInstance() {
        if (instance == null) {
            instance = new Pref();
        }
        return instance;
    }

    public boolean set(String key, Object value) {
        SharedPreferences pref = getPref();
        if (pref == null)
            return false;
        SharedPreferences.Editor edit = pref.edit();
        if (value == null) {
            edit.remove(key);
            return edit.commit();
        }
        if (value instanceof String) {
            return edit.putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            return edit.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            return edit.putInt(key, (Integer) value).commit();
        } else if (value instanceof Float) {
            return edit.putFloat(key, (Float) value).commit();
        } else if (value instanceof Long) {
            return edit.putLong(key, (Long) value).commit();
        }
        return false;
        // System.out.println("Put " + key + "value: " + value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        SharedPreferences pref = getPref();
        if (pref == null)
            return defaultValue;
        if (!pref.contains(key))
            return defaultValue;
        if (defaultValue instanceof String) {
            return (T) pref.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            Boolean b = pref.getBoolean(key, (Boolean) defaultValue);
            return (T) b;
        } else if (defaultValue instanceof Integer) {
            Integer i = pref.getInt(key, (Integer) defaultValue);
            return (T) i;
        } else if (defaultValue instanceof Float) {
            Float f = pref.getFloat(key, (Float) defaultValue);
            return (T) f;
        } else if (defaultValue instanceof Long) {
            Long f = pref.getLong(key, (Long) defaultValue);
            return (T) f;
        } else if (defaultValue.getClass().isEnum()) {
            String v = pref.getString(key, null);
            if (v == null) return defaultValue;
            return (T) Enum.valueOf(defaultValue.getClass().asSubclass(Enum.class), v);
        }
        return defaultValue;
    }

    public void clearPreferences() {
        SharedPreferences pref = getPref();
        pref.edit().clear().commit();
    }

    private static SharedPreferences getPref() {
        return TTApplication.getInstance().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }
}
