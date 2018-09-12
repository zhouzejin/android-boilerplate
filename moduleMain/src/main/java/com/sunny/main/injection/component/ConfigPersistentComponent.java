package com.sunny.main.injection.component;

import com.sunny.common.injection.scope.ConfigPersistent;
import com.sunny.main.injection.module.ActivityModule;
import com.sunny.main.injection.module.FragmentModule;
import com.sunny.main.ui.base.MainBaseActivity;

import dagger.Component;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link MainBaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
    FragmentComponent fragmentComponent(ActivityModule activityModule, FragmentModule fragmentModule);

}
