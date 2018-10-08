package cn.com.megait.imoocbusiness.db;

import android.content.Context;
import android.content.SharedPreferences;

import cn.com.megait.imoocbusiness.application.ImoocApplication;

/**
 * @author lenovo
 * @function SharedPreferences配置界面
 * @date 2018/10/8
 */
public class SPManager {
    private static SharedPreferences sp=null;
    private static SPManager spManager=null;
    private static SharedPreferences.Editor editor=null;

    //Preferences文件名
    private static final String PREFERENCES_FILE_NAME="imooc_pre";

    //上次商品更新时间
    public static final String LAST_UPADTE_GOODS="last_update_goods";
    //播放器设置
    public static final String VIDEO_PLAY_SETTING="video_play_setting";
    //显示引导
    public static final String IS_SHOW_GUIDE="is_show_guide";

    private SPManager(){
        sp=ImoocApplication.getInstance().getSharedPreferences(PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    public static SPManager getInstance(){
        if(spManager==null || sp==null || editor==null){
            spManager=new SPManager();
        }
        return spManager;
    }

    public void putInt(String key ,int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key,int defValue){
        return sp.getInt(key, defValue);
    }

    public void putLong(String key ,long value){
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key,long defValue){
        return sp.getLong(key, defValue);
    }

    public void putString(String key ,String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key,String defValue){
        return sp.getString(key, defValue);
    }


    public void putFloat(String key ,Float value){
        editor.putFloat(key, value);
        editor.commit();
    }

    public Float getFloat(String key,Float defValue){
        return sp.getFloat(key, defValue);
    }

    public void putBoolean(String key ,boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key,boolean defValue){
        return sp.getBoolean(key, defValue);
    }

    public boolean isExist(String key){
        return sp.contains(key);
    }

    public void remove(String key){
        editor.remove(key);
        editor.commit();
    }




}
