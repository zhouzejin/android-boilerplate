package uk.co.ribot.androidboilerplate.injection.component;

import com.sunny.common.injection.scope.InActivity;

import dagger.Subcomponent;
import uk.co.ribot.androidboilerplate.BoilerplateApplication;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;

/**
 * This is a Dagger component. Refer to {@link BoilerplateApplication} for the list of Dagger components
 * used in this application.
 */
@InActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

}
