package uk.co.ribot.androidboilerplate.injection.module

import android.content.Context
import android.support.v4.app.Fragment

import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.qualifier.FragmentContext

@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment {
        return mFragment
    }

    @Provides
    @FragmentContext
    internal fun providesContext(): Context {
        return mFragment.context!!
    }
}
