package uk.co.ribot.androidboilerplate.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import uk.co.ribot.androidboilerplate.injection.component.MainComponent;

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * {@link MainComponent} is a scoped component ({@code @Singleton}, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface InFragment {
}
