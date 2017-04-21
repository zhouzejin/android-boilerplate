package uk.co.ribot.androidboilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.ribot.androidboilerplate.injection.component.FragmentComponent;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule;

/**
 * Abstract fragment that every other Fragment in this application must implement.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;
    private Unbinder unbinder;
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
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initViews(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }

}
