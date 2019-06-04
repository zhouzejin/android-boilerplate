package com.sunny.main.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunny.common.base.BaseFragment;
import com.sunny.main.injection.component.FragmentComponent;
import com.sunny.main.injection.module.ActivityModule;
import com.sunny.main.injection.module.FragmentModule;

/**
 * Abstract fragment that every other Fragment in this application must implement.
 */
public abstract class MainBaseFragment extends BaseFragment {

    private FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentComponent = ((MainBaseActivity) getActivity()).configPersistentComponent()
                .fragmentComponent(new ActivityModule(getActivity()), new FragmentModule(this));
    }

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }

}
