package cn.com.megait.commonlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.com.megait.commonlib.module.AdValue;

/**
 * @author lenovo
 * @function
 * @date 2018/12/24
 */
public class VideoFullDialog extends Dialog implements CustomVideoView.ADVideoPlayerListener {




    private static final String TAG=VideoFullDialog.class.getSimpleName();
    private CustomVideoView mCustomVideoView;
    private Context mContext;
    private RelativeLayout mRootView;
    private ViewGroup mParentView;
    private ImageView mBackButton;
    private AdValue mAdValue;
    private int mPosition;
    private FullToSmallListener mListener;
    private Bundle mStartBundle;
    private Bundle mEndBundle;//用于Dialog出入场动画


    public VideoFullDialog(Context context, CustomVideoView customVideoView, AdValue adValue, int position) {
        super(context);
        mCustomVideoView = customVideoView;
        mAdValue = adValue;
        mPosition = position;
        /**
         * TODO Now-Coding
         */
    }

    @Override
    public void onBufferUpdate(int time) {

    }

    @Override
    public void onClickFullScreenBtn() {

    }

    @Override
    public void onClickVideo() {

    }

    @Override
    public void onClickBackBtn() {

    }

    @Override
    public void onClickPlay() {

    }

    @Override
    public void onAdVideoLoadSuccess() {

    }

    @Override
    public void onAdVideoLoadFailed() {

    }

    @Override
    public void onAdVideoLoadComplete() {

    }

    public interface FullToSmallListener{
        /**
         * 获取当前播放位置
         * @param position 当前播放位置
         */
        void getCurrentPlayPosition(int position);

        /**
         * 播放完成
         */
        void playComplete();
    }
}
