package uk.co.ribot.androidboilerplate;

import android.os.Bundle;
import android.widget.Toast;

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
        Toast.makeText(this, "使用Plugin方式跳转", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

}
