package uk.co.ribot.androidboilerplate.injection.component

import dagger.Subcomponent
import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.injection.scope.InFragment
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule
import uk.co.ribot.androidboilerplate.ui.main.MainFragment

/**
 * This is a Dagger component. Refer to [BoilerplateApplication] for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = [ActivityModule::class, FragmentModule::class])
interface FragmentComponent {

    fun inject(mainFragment: MainFragment)

}
