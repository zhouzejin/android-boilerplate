package uk.co.ribot.androidboilerplate;

import android.content.Intent;
import android.os.Bundle;

import com.sunny.commonbusiness.base.BaseActivity;
import com.sunny.main.ui.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppActivity extends BaseActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_goto_main)
    void gotoMain() {
        startActivity(new Intent(AppActivity.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

}
