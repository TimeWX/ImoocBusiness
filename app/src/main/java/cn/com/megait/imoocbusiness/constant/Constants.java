package cn.com.megait.imoocbusiness.constant;

import android.Manifest;
import android.os.Environment;

/**
 * @author TimeW
 * @function 权限常量
 * @date 2018/9/21
 */
public class Constants {
    public static final int WRITE_READ_EXTERNAL_CODE=0X01;
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final int HARDWARE_CAMERA_CODE=0X02;
    public static final String[] HARDWARE_CAMERA_PERMISSION=new String[]{Manifest.permission.CAMERA};

    public static final String APP_PHOTO_DIR= Environment.getExternalStorageDirectory().getAbsolutePath()
            .concat("/imooc_business/photo");
    public static final String APP_FILE_DIR=Environment.getExternalStorageDirectory().getAbsolutePath()
            .concat("/imooc_business/file");


    //腾讯QQ应用包名
    public static final String TENCENT_QQ_PACKGAE_NAME="com.tencent.mobileqq";
}
