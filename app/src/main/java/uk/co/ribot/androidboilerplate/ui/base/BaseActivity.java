package uk.co.ribot.androidboilerplate.ui.base;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicLong;

import uk.co.ribot.androidboilerplate.BoilerplateApplication;
import uk.co.ribot.androidboilerplate.injection.component.ActivityComponent;
import uk.co.ribot.androidboilerplate.injection.component.ConfigPersistentComponent;
import uk.co.ribot.androidboilerplate.injection.component.DaggerConfigPersistentComponent;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.utils.LogUtil;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap
            = new LongSparseArray<>();

    private ConfigPersistentComponent mConfigPersistentComponent;
    private ActivityComponent mActivityComponent;
    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the ConfigPersistentComponent and reuses cached ConfigPersistentComponent
        // if this is being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        mConfigPersistentComponent = sComponentsMap.get(mActivityId, null);
        if (mConfigPersistentComponent == null) {
            LogUtil.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(BoilerplateApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, mConfigPersistentComponent);
        }
        mActivityComponent = mConfigPersistentComponent.activityComponent(new ActivityModule(this));

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            LogUtil.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ConfigPersistentComponent configPersistentComponent() {
        return mConfigPersistentComponent;
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

}
