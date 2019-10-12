package com.farm.dinh.data.model;

import androidx.annotation.Nullable;

public class TreeInfo extends Tree {
    private String age;
    private int amount;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj != null && obj instanceof Tree && ((Tree) obj).getName().equals(this.getName()));
    }
}
