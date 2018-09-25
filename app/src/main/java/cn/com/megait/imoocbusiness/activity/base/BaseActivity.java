package cn.com.megait.imoocbusiness.activity.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import cn.com.megait.imoocbusiness.constant.Constant;

/**
 * @author TimeW
 * @function
 */
public class BaseActivity extends AppCompatActivity {


    public String TAG;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TAG=getComponentName().getShortClassName();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void requestPermissions(int code,String... permissions){
        ActivityCompat.requestPermissions(this,permissions,code);
    }

    /**
     * 判断是否有指定权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            //判断权限是否被授权
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    doSDCardPermission();
                }
                break;
        }
    }

    /**
     * 处理整个SDCard业务
     */
    public void doSDCardPermission(){

    }

    /**
     * 隐藏状态栏
     */
    public void hiddenStatusBar(){}

    /**
     * 改变状态栏颜色
     * @param color
     */
    public void changeStatusBarColor(@ColorRes  int color){}

    /**
     * 调整状态栏为亮模式,这样状态栏文字颜色就会变为深模式
     */
    public void reverseStatusBarColor(){}
}
