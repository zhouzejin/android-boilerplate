package uk.co.ribot.androidboilerplate.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import uk.co.ribot.androidboilerplate.injection.component.FragmentComponent
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule

/**
 * Abstract fragment that every other Fragment in this application must implement.
 */
abstract class BaseFragment : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mUnbinder: Unbinder
    private lateinit var mFragmentComponent: FragmentComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFragmentComponent = (activity as BaseActivity).configPersistentComponent()
                .fragmentComponent(ActivityModule(activity!!), FragmentModule(this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        mUnbinder = ButterKnife.bind(this, mRootView)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
    }

    override fun onDestroyView() {
        mUnbinder.unbind()
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * 获取布局文件
     * @return 布局文件ID
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化View
     * @param savedInstanceState
     */
    abstract fun initViews(savedInstanceState: Bundle?)

    fun fragmentComponent(): FragmentComponent {
        return mFragmentComponent
    }

}
