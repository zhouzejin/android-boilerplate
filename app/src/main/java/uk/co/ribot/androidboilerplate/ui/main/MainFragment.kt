package uk.co.ribot.androidboilerplate.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import butterknife.BindView
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment
import uk.co.ribot.androidboilerplate.utils.factory.createGenericErrorDialog
import javax.inject.Inject

class MainFragment : BaseFragment(), MainMvpView {

    @Inject
    internal lateinit var mMainPresenter: MainPresenter
    @Inject
    internal lateinit var mSubjectsAdapter: SubjectsAdapter

    @BindView(R.id.recycler_view)
    internal lateinit var mRecyclerView: RecyclerView

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inject instance for fragment
        fragmentComponent().inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initViews(savedInstanceState: Bundle?) {
        mRecyclerView.adapter = mSubjectsAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mMainPresenter.attachView(this)
        mMainPresenter.loadSubjects()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mMainPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /*****
     * MVP View methods implementation
     *****/

    override fun showSubjects(subjects: List<Subject>) {
        mSubjectsAdapter.setSubjects(subjects)
        mSubjectsAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        createGenericErrorDialog(context!!, getString(R.string.error_loading_subjects))
                .show()
    }

    override fun showSubjectsEmpty() {
        mSubjectsAdapter.setSubjects(emptyList())
        mSubjectsAdapter.notifyDataSetChanged()
        Toast.makeText(context, R.string.empty_subjects, Toast.LENGTH_LONG).show()
    }

}
