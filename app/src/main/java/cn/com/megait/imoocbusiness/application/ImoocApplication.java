package cn.com.megait.imoocbusiness.application;

import android.app.Application;

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

    }

    private void initAdSDK(){

    }

    private void initJPush(){

    }

}
