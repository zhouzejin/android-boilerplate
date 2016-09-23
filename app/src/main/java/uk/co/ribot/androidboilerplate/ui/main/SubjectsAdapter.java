package uk.co.ribot.androidboilerplate.ui.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private List<Subject> mSubjects;

    @Inject
    public SubjectsAdapter() {
        mSubjects = new ArrayList<>();
    }

    public void setSubjects(List<Subject> subjects) {
        mSubjects = subjects;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubjectViewHolder holder, int position) {
        Subject subject = mSubjects.get(position);
        holder.iamgeView.setBackgroundColor(Color.parseColor("#C0FF3E"));
        holder.titleTextView.setText(subject.title());
        holder.genresTextView.setText(subject.genres().toString());
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_image) View iamgeView;
        @BindView(R.id.text_title) TextView titleTextView;
        @BindView(R.id.text_genres) TextView genresTextView;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
