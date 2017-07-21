package uk.co.ribot.androidboilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.ribot.androidboilerplate.injection.component.FragmentComponent;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule;

/**
 * Abstract fragment that every other Fragment in this application must implement.
 */
public abstract class BaseFragment extends Fragment {

    private FragmentComponent mFragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentComponent = ((BaseActivity) getActivity()).configPersistentComponent()
                .fragmentComponent(new ActivityModule(getActivity()), new FragmentModule(this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }

}
