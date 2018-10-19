package uk.co.ribot.androidboilerplate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.sunny.commonbusiness.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppActivity extends BaseActivity {

    private final String pluginPath = Environment.getExternalStorageDirectory().getPath() + "/com.sunny.plugin/pluginMain.apk";
    private final String pluginName = "PluginMain";
    private final String pluginLaunchActivity = ".ui.main.MainActivity";

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        setContentView(R.layout.activity_app);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_install_main, R.id.btn_goto_main, R.id.btn_uninstall_main})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install_main:
                installPluginMain();
                break;
            case R.id.btn_goto_main:
                gotoPluginMain();
                break;
            case R.id.btn_uninstall_main:
                unInstallPluginMain();
                break;
        }
    }

    private void installPluginMain() {
        if (RePlugin.isPluginInstalled(pluginName)) {
            Toast.makeText(this, "主插件已安装", Toast.LENGTH_LONG).show();
        } else {
            PluginInfo pluginInfo = RePlugin.install(pluginPath);
            if (pluginInfo != null) {
                if (RePlugin.preload(pluginInfo)) {
                    Toast.makeText(this, "主插件预加载成功", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "主插件安装失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void gotoPluginMain() {
        if (RePlugin.isPluginInstalled(pluginName)) {
            PluginInfo pluginInfo = RePlugin.getPluginInfo(pluginName);
            String pluginPackageName = pluginInfo.getPackageName();
            RePlugin.startActivity(this, RePlugin.createIntent(pluginPackageName, pluginPackageName + pluginLaunchActivity));
        } else {
            Toast.makeText(this, "请先安装主插件", Toast.LENGTH_LONG).show();
        }
    }

    private void unInstallPluginMain() {
        if (RePlugin.isPluginInstalled(pluginName)) {
            Boolean isUninstallSuccess = RePlugin.uninstall(pluginName);
            if (isUninstallSuccess) {
                Toast.makeText(this, "主插件卸载成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "主插件卸载失败", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "主插件没有被安装", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

}
