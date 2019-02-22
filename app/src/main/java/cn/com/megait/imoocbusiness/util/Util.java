package cn.com.megait.imoocbusiness.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;

import cn.com.megait.imoocbusiness.constant.Constants;
import cn.com.megait.imoocbusiness.module.recommand.RecommandBodyValue;

/**
 * @author TimeW
 * @function 工具类
 * @date 2018/9/28
 */
public class Util {


    /**
     * 跳转到QQ对话
     *
     * @param context
     * @param qqNum
     */
    public static void skipToQQChat(Context context, String qqNum) {
        Uri uri = createQQUri(qqNum);
        //检测是否安装QQ
        if (checkAPKExist(context, Constants.TENCENT_QQ_PACKGAE_NAME)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "QQ应用未安装,无法使用该功能", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 创建QQ Uri地址
     *
     * @param qqNum
     * @return
     */
    private static Uri createQQUri(String qqNum) {
        String result = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum + "&version=1";
        Uri uri = Uri.parse(result);
        return uri;
    }


    /**
     * 检查应用是否存在
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    private static boolean checkAPKExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName.trim())) {
            return false;
        }
        try {
            //查找相匹配的包名
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //包名未找到,则报异常
            return false;
        }

    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showSoftInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //为ViewPager结构化数据
    public static ArrayList<RecommandBodyValue> handleData(RecommandBodyValue value) {
        ArrayList<RecommandBodyValue> values = new ArrayList<>();
        String[] titles = value.title.split("@");
        String[] infos = value.info.split("@");
        String[] prices = value.price.split("@");
        String[] texts = value.text.split("@");
        ArrayList<String> urls = value.url;
        int start = 0;
        for (int i = 0; i < titles.length; i++) {
            RecommandBodyValue tempValue = new RecommandBodyValue();
            tempValue.title = titles[i];
            tempValue.info = infos[i];
            tempValue.price = prices[i];
            tempValue.text = texts[i];
            tempValue.url = extractData(urls, start, 3);
            start += 3;

            values.add(tempValue);
        }
        return values;
    }

    private static ArrayList<String> extractData(ArrayList<String> source, int start, int interval) {
        ArrayList<String> tempUrls = new ArrayList<>();
        for (int i = start; i < start + interval; i++) {
            tempUrls.add(source.get(i));
        }
        return tempUrls;
    }
}
