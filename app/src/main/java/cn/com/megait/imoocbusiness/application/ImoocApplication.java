package cn.com.megait.imoocbusiness.application;

import android.app.Application;

import com.mob.MobSDK;

import cn.sharesdk.framework.ShareSDK;

/**
 * @author lenovo
 * @function
 * @date 2018/10/8
 */
public class ImoocApplication extends Application {

    private static  Application mApplication=null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        initShareSDK();
        initJPush();
        initAdSDK();
    }

    public static Application getInstance(){
        return mApplication;
    }

    public void initShareSDK(){
        MobSDK.init(this);


    }

    private void initAdSDK(){

    }

    private void initJPush(){

    }

}
