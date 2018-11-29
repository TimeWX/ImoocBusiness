package cn.com.megait.commonlib.core;

import cn.com.megait.commonlib.constant.SDKConstants;

/**
 * @author lenovo
 * @function
 * @date 2018/11/29
 * TODO 了解
 */
public class AdParameters {

    //用来记录可自动播放的条件
    private static SDKConstants.AutoPlaySetting currentSetting = SDKConstants.AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI; //默认都可以自动播放

    public static void setCurrentSetting(SDKConstants.AutoPlaySetting setting) {
        currentSetting = setting;
    }

    public static SDKConstants.AutoPlaySetting getCurrentSetting() {
        return currentSetting;
    }

    /**
     * 获取sdk当前版本号
     */
    public static String getAdSDKVersion() {
        return "1.0.0";
    }
}
