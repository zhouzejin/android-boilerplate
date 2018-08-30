package uk.co.ribot.androidboilerplate.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.injection.qualifier.FragmentContext
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader

internal class SubjectsAdapter @Inject constructor(@param:FragmentContext private val mContext: Context, private val mImageLoader: ImageLoader)
    : RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    private var mSubjects: List<Subject>

    init {
        mSubjects = ArrayList()
    }

    fun setSubjects(subjects: List<Subject>) {
        mSubjects = subjects
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = mSubjects[position]
        mImageLoader.displayImage(mContext, holder.imageView, subject.images().small(),
                ImageLoader.DisplayOption.Builder().build())
        holder.titleTextView.text = subject.title()
        holder.genresTextView.text = subject.genres().toString()
    }

    override fun getItemCount(): Int {
        return mSubjects.size
    }

    internal inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_pic)
        lateinit var imageView: ImageView
        @BindView(R.id.text_title)
        lateinit var titleTextView: TextView
        @BindView(R.id.text_genres)
        lateinit var genresTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}
