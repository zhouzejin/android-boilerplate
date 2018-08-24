package uk.co.ribot.androidboilerplate.injection.scope

import uk.co.ribot.androidboilerplate.injection.component.ConfigPersistentComponent
import javax.inject.Scope

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * [ConfigPersistentComponent]
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ConfigPersistent
