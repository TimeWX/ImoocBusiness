package cn.com.megait.commonlib.adutil;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;

/**
 * @author lenovo
 * @function 工具集合类
 * @date 2018/11/28
 * TODO 了解
 */
public class Utils {


    /**
     * DIP转化成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    /**
     * 获取展示尺寸
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return displayMetrics;
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static BitmapDrawable decodeImage(String base64drawable) {
        byte[] rawImageData = Base64.decode(base64drawable, 0);
        return new BitmapDrawable(null, new ByteArrayInputStream(rawImageData));
    }
}
