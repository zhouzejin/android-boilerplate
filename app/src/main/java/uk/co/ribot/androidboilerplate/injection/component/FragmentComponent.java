package uk.co.ribot.androidboilerplate.injection.component;

import com.sunny.common.injection.scope.InFragment;

import dagger.Subcomponent;
import uk.co.ribot.androidboilerplate.BoilerplateApplication;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule;
import uk.co.ribot.androidboilerplate.ui.main.MainFragment;

/**
 * This is a Dagger component. Refer to {@link BoilerplateApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent {

    void inject(MainFragment mainFragment);

}
