package cn.com.megait.commonlib.core.video;

import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import cn.com.megait.commonlib.adutil.LogUtils;
import cn.com.megait.commonlib.core.AdContextInteface;
import cn.com.megait.commonlib.module.AdValue;
import cn.com.megait.commonlib.okhttp.HttpConstants;
import cn.com.megait.commonlib.report.ReportManager;
import cn.com.megait.commonlib.widget.CustomVideoView;

/**
 * @author TimeW
 * @function 管理Slot, 与外界进行通信
 * @date 2018/12/18
 */
public class VideoAdContext implements VideoAdSlot.AdSDKSlotListener {


    private final String TAG = VideoAdContext.class.getSimpleName();
    private ViewGroup mParentView;
    private VideoAdSlot mVideoAdSlot;
    private AdValue mAdValue;
    private AdContextInteface mListener;
    private CustomVideoView.ADFrameImageLoadListener mADFrameImageLoadListener;

    public VideoAdContext(ViewGroup parentView, String instance, CustomVideoView.ADFrameImageLoadListener ADFrameImageLoadListener) {
        mParentView = parentView;
        try {
            mAdValue = JSON.parseObject(instance, AdValue.class);
        } catch (Exception e) {
            LogUtils.d(TAG, "字符串数据格式存在问题:" + instance);
        }
        mADFrameImageLoadListener = ADFrameImageLoadListener;
        load();
    }

    /**
     * 初始化广告,不初始化,即不加载VideoView
     */
    private void load() {
        if (mAdValue != null && mAdValue.resource != null) {
            mVideoAdSlot = new VideoAdSlot(mAdValue, this, mADFrameImageLoadListener);
            sendAnalizeReport(HttpConstants.Params.ad_analize, HttpConstants.AD_DATA_SUCCESS);
        }else {
            //创建空的VideoAdSlot事件,不响应任何事件
            mVideoAdSlot =new VideoAdSlot(null,this,mADFrameImageLoadListener);
            if(mListener!=null){
                mListener.onAdFailed();
            }
            sendAnalizeReport(HttpConstants.Params.ad_analize,HttpConstants.AD_DATA_FAILED);
        }
    }

    /**
     * 发送解析事件
     *
     * @param ad_analize
     * @param adDataSuccess 发送解析事件码:"200","202"
     */
    private void sendAnalizeReport(HttpConstants.Params ad_analize, String adDataSuccess) {
        ReportManager.
    }

    @Override
    public ViewGroup getAdParent() {
        return null;
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

    @Override
    public void onClickVideo(String url) {

    }
}
