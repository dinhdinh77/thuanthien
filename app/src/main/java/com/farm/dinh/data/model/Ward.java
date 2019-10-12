package com.farm.dinh.data.model;

import androidx.annotation.Nullable;

public class Ward {
    private String id;
    private String name;
    private String type;

    public Ward(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj != null && obj instanceof Ward && ((Ward) obj).getName().equals(this.getName()));
    }
}
