package cn.com.megait.imoocbusiness.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import cn.com.megait.imoocbusiness.constant.Constant;

/**
 * @author TimeW
 * @function 工具类
 * @date 2018/9/28
 */
public class Util {


    /**
     * 跳转到QQ对话
     * @param context
     * @param qqNum
     */
    public static void skipToQQChat(Context context,String qqNum) {
        Uri uri = createQQUri(qqNum);
        //检测是否安装QQ
        if(checkAPKExist(context,Constant.TENCENT_QQ_PACKGAE_NAME)){
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"QQ应用未安装,无法使用该功能",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        String versionName="1.0.0";
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        int versionCode=1;
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  versionCode;
    }

    /**
     * 创建QQ Uri地址
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
}
