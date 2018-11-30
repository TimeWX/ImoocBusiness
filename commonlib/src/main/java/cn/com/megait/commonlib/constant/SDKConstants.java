package cn.com.megait.commonlib.constant;

/**
 * @author lenovo
 * @function SDK中存放的常量
 * @date 2018/11/29
 */
public class SDKConstants {

    //毫秒单位
    public static int MILLION_UNIT = 1000;

    //自动播放阈值
    public static int VIDEO_SCREEN_PERCENT = 50;//屏占比

    public static float VIDEO_HEIGHT_PERCENT = 9 / 16.0f;

    //自动播放条件
    public enum AutoPlaySetting {
        AUTO_PLAY_ONLY_WIFI,
        AUTO_PLAY_3G_4G_WIFI,
        AUTO_PLAY_NEVER
    }


    //素材类型
    public static final String MATERIAL_IMAGE = "image";
    public static final String MATERIAL_HTML = "html";
}