package cn.com.megait.imoocbusiness.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;

/**
 * @author TimeW
 * @function 用于管理手机状态栏操作, 主要是Window类的使用
 * @date 2018/10/29
 */
public class StatusBarUtil {

    public static final int SYSTEM_UNDEFINED = 0;//未知
    public static final int SYSTEM_MIUI = 1;//小米系统
    public static final int SYSTEM_FLYME = 2;//魅族系统
    public static final int SYSTEM_ANDROID_6 = 3;//Android 6.0


    /**
     * 使状态变为全透明,该方法必须写在setContentView之前
     *
     * @param activity
     */
    @TargetApi(19)
    public static void onTransparencyBar(Activity activity) {
        //系统版本大于等于21,即5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |  //该参数指布局能延伸到navigationbar
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加状态栏为可更改背景色状态
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT); //设置navigation为透明
            //系统版本大于等于19,即4.4
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //设置窗体透明
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色
     *
     * @param activity
     * @param resColor
     */
    @TargetApi(19)
    public static void setStatusBarColor(Activity activity, @ColorRes int resColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(resColor));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //在Android4.4系统中要使状态栏着色,必须先使状态栏透明化
            onTransparencyBar(activity);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintColor(resColor);
        }
    }

    /**
     * 设置状态栏黑色字体图标
     *
     * @param activity
     * @return
     */
    @TargetApi(19)
    public static int setStatusBarLightMode(Activity activity) {
        int result = SYSTEM_UNDEFINED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (setStatusBarLightModeToMIUI(activity.getWindow(), true)) {
                result = SYSTEM_MIUI;
            } else if (setStatusBarLightModeToFlyme(activity.getWindow(), true)) {
                result = SYSTEM_FLYME;
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = SYSTEM_ANDROID_6;
            }
        }
        return result;
    }

    /**
     * 根据设备类型设置黑色图标
     *
     * @param activity
     * @param type     MIUI,FLYME,ANDROID6
     * @return
     */
    public static void setStatusBarLightMode(Activity activity, int type) {
        switch (type) {
            case SYSTEM_MIUI:
                setStatusBarLightModeToMIUI(activity.getWindow(), true);
                break;
            case SYSTEM_FLYME:
                setStatusBarLightModeToFlyme(activity.getWindow(), true);
                break;
            case SYSTEM_ANDROID_6:
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
            default:
                break;
        }
    }

    /**
     * 清楚状态栏黑色字体
     *
     * @param activity
     * @param type
     * @return
     */
    public static void setStatusBarDarkMode(Activity activity, int type) {
        switch (type) {
            case SYSTEM_MIUI:
                setStatusBarLightModeToMIUI(activity.getWindow(),false);
                break;
            case SYSTEM_FLYME:
                setStatusBarLightModeToFlyme(activity.getWindow(),false);
                break;
            case SYSTEM_ANDROID_6:
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                break;
            default:
                break;
        }

    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * TODO 了解魅族相关状态栏设置
     * @param window
     * @param dark
     * @return
     */
    public static boolean setStatusBarLightModeToFlyme(Window window, boolean dark) {
        boolean result = false;
        if(window!=null){
            try {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                Field darkFlags = WindowManager.LayoutParams.class.getField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getField("meizuFlags");
                darkFlags.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlags.getInt(null);
                int value = meizuFlags.getInt(layoutParams);
                if(dark){
                    value |= bit;
                }else{
                    value &= ~bit;
                }
                meizuFlags.setInt(layoutParams,value);
                window.setAttributes(layoutParams);
                result=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色,需要MIUI Version6以上
     * TODO 了解小米状态栏相关设置
     * @param window
     * @param dark
     * @return
     */
    public static boolean setStatusBarLightModeToMIUI(Window window, boolean dark) {
        boolean result = false;
        return result;
    }
}
