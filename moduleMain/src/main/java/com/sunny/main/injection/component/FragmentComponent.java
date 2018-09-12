package com.sunny.main.injection.component;

import com.sunny.common.injection.scope.InFragment;
import com.sunny.main.MainApplication;
import com.sunny.main.injection.module.ActivityModule;
import com.sunny.main.injection.module.FragmentModule;
import com.sunny.main.ui.main.MainFragment;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link MainApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment mainFragment);

}
