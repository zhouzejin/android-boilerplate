package uk.co.ribot.androidboilerplate.injection.scope

import uk.co.ribot.androidboilerplate.injection.component.FragmentComponent
import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * [FragmentComponent] is a scoped component (`@Singleton`, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@kotlin.annotation.MustBeDocumented
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class InFragment
