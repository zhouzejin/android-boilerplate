package uk.co.ribot.androidboilerplate.injection.component

import dagger.Subcomponent
import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule
import uk.co.ribot.androidboilerplate.injection.scope.InActivity

/**
 * This is a Dagger component. Refer to [BoilerplateApplication] for the list of Dagger components
 * used in this application.
 */
@InActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent
