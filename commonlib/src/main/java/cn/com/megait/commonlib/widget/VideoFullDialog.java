package cn.com.megait.commonlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.com.megait.commonlib.R;
import cn.com.megait.commonlib.activity.AdBrowserActivity;
import cn.com.megait.commonlib.adutil.LogUtils;
import cn.com.megait.commonlib.adutil.Utils;
import cn.com.megait.commonlib.constant.SDKConstants;
import cn.com.megait.commonlib.core.video.VideoAdSlot;
import cn.com.megait.commonlib.module.AdValue;
import cn.com.megait.commonlib.report.ReportManager;

/**
 * @author lenovo
 * @function
 * @date 2018/12/24
 */
public class VideoFullDialog extends Dialog implements CustomVideoView.ADVideoPlayerListener {


    private static final String TAG = VideoFullDialog.class.getSimpleName();
    private CustomVideoView mCustomVideoView;
    private Context mContext;
    private RelativeLayout mRootView;
    private ViewGroup mParentView;
    private ImageView mBackButton;
    private AdValue mAdValue;
    private int mPosition;
    private FullToSmallListener mListener;
    private VideoAdSlot.AdSDKSlotListener mAdSDKSlotListener;
    private Bundle mStartBundle;
    private Bundle mEndBundle;//用于Dialog出入场动画
    private int deltaY;
    private boolean isFirst = true;


    public VideoFullDialog(Context context, CustomVideoView customVideoView, AdValue adValue, int position) {
        super(context);
        mContext = context;
        mCustomVideoView = customVideoView;
        mAdValue = adValue;
        mPosition = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xadsdk_dialog_video_layout);
        initVideoView();
    }

    @Override
    public void dismiss() {
        LogUtils.i(TAG,"dismiss");
        mParentView.removeView(mParentView);
        super.dismiss();
    }

    public void setViewBundle(Bundle bundle) {
        mStartBundle = bundle;
    }

    public void setListener(FullToSmallListener listener) {
        mListener = listener;
    }

    public void setAdSDKSlotListener(VideoAdSlot.AdSDKSlotListener adSDKSlotListener) {
        mAdSDKSlotListener = adSDKSlotListener;
    }

    private void initVideoView() {
        mParentView = findViewById(R.id.content_layout);
        mBackButton = findViewById(R.id.xadsdk_player_close_btn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackBtn();
            }
        });
        mRootView = findViewById(R.id.root_view);
        mRootView.setVisibility(View.INVISIBLE);
        mCustomVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickVideo();
            }
        });
        mCustomVideoView.mute(false);
        mParentView.addView(mCustomVideoView);
        mParentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mParentView.getViewTreeObserver().removeOnPreDrawListener(this);
                prepareSceneData();
                runEnterAnimation();
                return true;
            }
        });

    }

    /**
     * 准备动画所需数据
     */
    private void prepareSceneData() {
        mEndBundle = Utils.getViewProperty(mCustomVideoView);
        //将destationview 移动到 originalview
        deltaY = mStartBundle.getInt(Utils.PROPNAME_SCREENLOCATION_TOP) - mEndBundle.getInt(Utils.PROPNAME_SCREENLOCATION_TOP);
        mCustomVideoView.setTranslationY(deltaY);
    }

    /**
     * 入场动画
     */
    private void runEnterAnimation() {
        mCustomVideoView.animate()
                .setDuration(200)
                .setInterpolator(new LinearInterpolator())
                .translationY(0)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        mRootView.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    /**
     * 退出动画
     */
    private void runExitAnimation(){
        mCustomVideoView.animate()
                .setDuration(200)
                .setInterpolator(new LinearInterpolator())
                .translationY(deltaY)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                        try {
                            ReportManager.exitFullScreenReport(mAdValue.event.exitFull.content,
                                    mCustomVideoView.getCurrentPosition()/SDKConstants.MILLION_UNIT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(mListener!=null){
                            mListener.getCurrentPlayPosition(mCustomVideoView.getCurrentPosition());
                        }
                    }
                }).start();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        LogUtils.i(TAG, "onWindowFocusChanged");
        mCustomVideoView.isShowFullBtn(false);//防止第一次,有些手机仍然显示全屏按钮
        if (!hasFocus) {
            mPosition = mCustomVideoView.getCurrentPosition();
            mCustomVideoView.pauseForFullScreen();//准备全屏
        } else {
            if (isFirst) {//为了适配某些手机不执行seekAndResume中的播放方法
                mCustomVideoView.seekAndResume(mPosition);
            } else {
                mCustomVideoView.resume();
            }
        }
        isFirst = false;
    }

    @Override
    public void onBufferUpdate(int time) {
        try {
            if (mAdValue != null) {
                ReportManager.suReport(mAdValue.middleMonitor, time / SDKConstants.MILLION_UNIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickFullScreenBtn() {
        onClickVideo();
    }

    @Override
    public void onClickVideo() {
        String destinationUrl = mAdValue.clickUrl;
        if (mAdSDKSlotListener != null) {
            if (mCustomVideoView.isFrameHidden() && !TextUtils.isEmpty(destinationUrl)) {
                mAdSDKSlotListener.onClickVideo(destinationUrl);
            }
            try {
                ReportManager.pauseVideoReport(mAdValue.clickMonitor, mCustomVideoView.getCurrentPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (mCustomVideoView.isFrameHidden() && !TextUtils.isEmpty(destinationUrl)) {
                Intent intent = new Intent(mContext, AdBrowserActivity.class);
                intent.putExtra(AdBrowserActivity.KEY_URL, destinationUrl);
                mContext.startActivity(intent);
                try {
                    ReportManager.pauseVideoReport(mAdValue.clickMonitor,
                            mCustomVideoView.getCurrentPosition() / SDKConstants.MILLION_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onClickBackBtn() {
        runExitAnimation();
    }

    @Override
    public void onClickPlay() {


    }

    @Override
    public void onAdVideoLoadSuccess() {

        if(mCustomVideoView!=null)
            mCustomVideoView.resume();
    }

    @Override
    public void onAdVideoLoadFailed() {
    }

    @Override
    public void onAdVideoLoadComplete() {
        try {
            //获取当前播放进度
            int position=mCustomVideoView.getDuration()/SDKConstants.MILLION_UNIT;
            ReportManager.sueReport(mAdValue.endMonitor,true,position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
        if(mListener!=null){
            mListener.playComplete();
        }
    }


    public interface FullToSmallListener {
        /**
         * 获取当前播放位置
         *
         * @param position 当前播放位置
         */
        void getCurrentPlayPosition(int position);

        /**
         * 播放完成
         */
        void playComplete();
    }
}
