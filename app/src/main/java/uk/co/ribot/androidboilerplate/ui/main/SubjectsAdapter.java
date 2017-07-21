package uk.co.ribot.androidboilerplate.ui.main;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.co.ribot.androidboilerplate.BR;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.databinding.ItemSubjectBinding;
import uk.co.ribot.androidboilerplate.ui.base.BindingViewHolder;
import uk.co.ribot.androidboilerplate.ui.base.ViewModel;

public class SubjectsAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private List<Subject> mSubjects;

    @Inject
    public SubjectsAdapter() {
        mSubjects = new ArrayList<>();
    }

    public void setSubjects(List<Subject> subjects) {
        mSubjects.clear();
        mSubjects.addAll(subjects);
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSubjectBinding binding = ItemSubjectBinding.inflate(inflater, parent, false);
        return new BindingViewHolder<ViewDataBinding>(binding);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        ViewModel viewModel = new MainViewModel.SubjectViewModel(mSubjects.get(position));
        holder.getBinding().setVariable(BR.viewmodel, viewModel);
        holder.getBinding().executePendingBindings(); // 强制绑定后立即刷新，避免数据闪烁
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

}
