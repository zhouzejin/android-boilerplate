package uk.co.ribot.androidboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunny.commonbusiness.base.BaseFragment;

import uk.co.ribot.androidboilerplate.injection.component.FragmentComponent;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule;

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
