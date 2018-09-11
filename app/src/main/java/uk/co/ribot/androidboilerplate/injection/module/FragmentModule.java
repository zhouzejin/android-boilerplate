package uk.co.ribot.androidboilerplate.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.sunny.common.injection.qualifier.FragmentContext;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @FragmentContext
    Context providesContext() {
        return mFragment.getContext();
    }
}
