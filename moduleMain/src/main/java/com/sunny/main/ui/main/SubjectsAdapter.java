package com.sunny.main.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.common.injection.qualifier.FragmentContext;
import com.sunny.common.utils.imageloader.ImageLoader;
import com.sunny.datalayer.model.bean.Subject;
import com.sunny.main.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Subject> mSubjects;

    @Inject
    public SubjectsAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;

        mSubjects = new ArrayList<>();
    }

    public void setSubjects(List<Subject> subjects) {
        mSubjects = subjects;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item_subject, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubjectViewHolder holder, int position) {
        Subject subject = mSubjects.get(position);
        mImageLoader.displayImage(mContext, holder.imageView, subject.images().small(),
                new ImageLoader.DisplayOption.Builder().build());
        holder.titleTextView.setText(subject.title());
        holder.genresTextView.setText(subject.genres().toString());
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView genresTextView;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_pic);
            titleTextView = itemView.findViewById(R.id.text_title);
            genresTextView = itemView.findViewById(R.id.text_genres);
        }
    }
}
