package uk.co.ribot.androidboilerplate.injection.component

import dagger.Component
import uk.co.ribot.androidboilerplate.injection.scope.ConfigPersistent
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [BaseActivity] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = [ApplicationComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent
    fun fragmentComponent(activityModule: ActivityModule, fragmentModule: FragmentModule): FragmentComponent

}
