package uk.co.ribot.androidboilerplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunny.common.base.BaseActivity;
import com.sunny.hybrid.HybridActivity;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

    @OnClick({R.id.btn_goto_main, R.id.btn_call_hybrid})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_goto_main:
                ARouter.getInstance().build("/main/MainActivity").navigation();
                break;
            case R.id.btn_call_hybrid:
                startActivity(new Intent(this, HybridActivity.class));
                break;
        }
    }

}
