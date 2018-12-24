package cn.com.megait.commonlib.core.video;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.com.megait.commonlib.R;
import cn.com.megait.commonlib.adutil.Utils;
import cn.com.megait.commonlib.constant.SDKConstants;
import cn.com.megait.commonlib.module.AdValue;
import cn.com.megait.commonlib.report.ReportManager;
import cn.com.megait.commonlib.widget.CustomVideoView;

/**
 * @author TimeW
 * @function 广告业务逻辑层
 * @date 2018/12/19
 */
public class VideoAdSlot implements CustomVideoView.ADVideoPlayerListener {
    private Context mContext;
    private CustomVideoView mCustomVideoView;
    private ViewGroup mParentView;
    private AdValue mAdValue;
    private AdSDKSlotListener mAdSDKSlotListener;
    private boolean isCanAutoPause;//是否可自动暂停标志位
    private int lastArea;//防止将要划入划出的播放器状态改变

    public VideoAdSlot(AdValue adValue, AdSDKSlotListener adSDKSlotListener, CustomVideoView.ADFrameImageLoadListener adFrameImageLoadListener) {
        mAdValue = adValue;
        mAdSDKSlotListener = adSDKSlotListener;
        mParentView = adSDKSlotListener.getAdParent();
        mContext = mParentView.getContext();
        initVideoView(adFrameImageLoadListener);

    }

    /**
     * 初始化VideoView
     * @param adFrameImageLoadListener AFFrameImageLoadListener对象
     */
    private void initVideoView(CustomVideoView.ADFrameImageLoadListener adFrameImageLoadListener) {
        //初始化VideoView
        mCustomVideoView = new CustomVideoView(mContext, mParentView);
        //添加数据源
        if (mAdValue != null) {
            mCustomVideoView.setDataSource(mAdValue.resource);
            mCustomVideoView.setFrameURI(mAdValue.thumb);
            mCustomVideoView.setFrameLoadListener(adFrameImageLoadListener);
            mCustomVideoView.setListener(this);
        }
        //添加VideoView自定义容器RelativeLayout
        RelativeLayout paddingView = new RelativeLayout(mContext);
        paddingView.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        paddingView.setLayoutParams(mCustomVideoView.getLayoutParams());
        //父容器添加Child
        mParentView.addView(paddingView);
        mParentView.addView(mCustomVideoView);

    }

    /**
     * 获取当前播放的时间(秒单位)
     * @return
     */
    private long getCurrentPlayTime() {
        // getCurrentPosition是以毫秒为单位,故需除去1000
        return  mCustomVideoView.getCurrentPosition()/SDKConstants.MILLION_UNIT;
    }

    @Override
    public void onBufferUpdate(int time) {
        try {
            ReportManager.suReport(mAdValue.middleMonitor,time/SDKConstants.MILLION_UNIT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 实现Play层接口
     */
    @Override
    public void onClickFullScreenBtn() {

        try {
            ReportManager.fullScreenReport(mAdValue.event.full.content,getCurrentPlayTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle bundle= Utils.getViewProperty(mParentView);
        mParentView.removeView(mCustomVideoView);

        /**
         * TODO Now-Coding
         */
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

    public interface AdSDKSlotListener {
        /**
         * 获取父组件
         *
         * @return 返回父容器
         */
        ViewGroup getAdParent();

        /**
         * 广告媒体加载成功
         */
        void onAdVideoLoadSuccess();

        /**
         * 广告媒体加载失败
         */
        void onAdVideoLoadFailed();

        /**
         * 广告媒体加载完成
         */
        void onAdVideoLoadComplete();

        /**
         * 点击打开广告Video
         *
         * @param url 链接地址
         */
        void onClickVideo(String url);
    }
}
