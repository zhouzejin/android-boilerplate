package uk.co.ribot.androidboilerplate;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunny.commonbusiness.base.BaseActivity;

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
        ARouter.getInstance().build("/main/MainActivity").navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

}
