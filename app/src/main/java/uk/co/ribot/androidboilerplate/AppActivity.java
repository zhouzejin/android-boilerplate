package uk.co.ribot.androidboilerplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunny.commonbusiness.base.BaseActivity;

import uk.co.ribot.androidboilerplate.ui.main.MainActivity;

public class AppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        findViewById(R.id.btn_goto_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
