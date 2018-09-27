package com.sunny.main.injection.component;

import com.sunny.common.injection.scope.InActivity;
import com.sunny.main.MainApplication;
import com.sunny.main.injection.module.ActivityModule;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link MainApplication} for the list of Dagger components
 * used in this application.
 */
@InActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

}
