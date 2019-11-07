package com.farm.dinh.annotations;

import com.farm.dinh.ui.viewmodel.BaseViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AViewModel {
    Class<? extends BaseViewModel> getValue();
}
