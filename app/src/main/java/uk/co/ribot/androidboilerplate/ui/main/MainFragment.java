package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.databinding.FragmentMainBinding;
import uk.co.ribot.androidboilerplate.injection.qualifier.FragmentContext;
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment;
import uk.co.ribot.androidboilerplate.utils.factory.DialogFactory;

public class MainFragment extends BaseFragment implements MainMvvmView {

    @Inject
    MainViewModel mMainViewModel;
    @Inject
    SubjectsAdapter mSubjectsAdapter;
    @Inject
    @FragmentContext
    Context mContext;

    private FragmentMainBinding mMainBinding;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject instance for fragment
        fragmentComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mMainBinding = FragmentMainBinding.inflate(inflater, container, false);

        mMainBinding.recyclerView.setAdapter(mSubjectsAdapter);
        mMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        return mMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMainViewModel.attachView(this);
        mMainBinding.setViewmodel(mMainViewModel);
        mMainViewModel.loadSubjects();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mMainViewModel.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*****
     * MVVM View methods implementation
     *****/

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(mContext, getString(R.string.error_loading_subjects))
                .show();
    }

    @Override
    public void showSubjectsEmpty() {
        Toast.makeText(mContext, R.string.empty_subjects, Toast.LENGTH_LONG).show();
    }

}
