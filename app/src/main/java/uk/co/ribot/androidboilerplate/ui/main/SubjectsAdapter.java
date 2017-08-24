package uk.co.ribot.androidboilerplate.ui.main;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import uk.co.ribot.androidboilerplate.BR;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.databinding.ItemSubjectBinding;
import uk.co.ribot.androidboilerplate.ui.base.BaseAdapter;
import uk.co.ribot.androidboilerplate.ui.base.ViewModel;

public class SubjectsAdapter extends BaseAdapter<Subject> {

    private final MainViewModel mMainViewModel;

    @Inject
    public SubjectsAdapter(MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
    }

    @Override
    public ViewDataBinding getBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemSubjectBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        ViewModel viewModel = mMainViewModel.new SubjectViewModel(mData.get(position));
        holder.getBinding().setVariable(BR.viewmodel, viewModel);
        super.onBindViewHolder(holder, position);
    }

}
