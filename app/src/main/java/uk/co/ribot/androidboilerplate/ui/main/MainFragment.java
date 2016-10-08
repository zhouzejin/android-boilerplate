package uk.co.ribot.androidboilerplate.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

public class MainFragment extends Fragment implements MainMvpView {

    private Unbinder unbinder;

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    SubjectsAdapter mSubjectsAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setAdapter(mSubjectsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainPresenter.attachView(this);
        mMainPresenter.loadSubjects();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mMainPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showSubjects(List<Subject> subjects) {
        mSubjectsAdapter.setSubjects(subjects);
        mSubjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getContext(), getString(R.string.error_loading_subjects))
                .show();
    }

    @Override
    public void showSubjectsEmpty() {
        mSubjectsAdapter.setSubjects(Collections.<Subject>emptyList());
        mSubjectsAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), R.string.empty_subjects, Toast.LENGTH_LONG).show();
    }

}
