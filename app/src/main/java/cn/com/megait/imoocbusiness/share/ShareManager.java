package cn.com.megait.imoocbusiness.share;

import android.content.Context;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author TimeW
 * @function 分享管理
 * @date 2019/1/25
 */
public class ShareManager {
    private static ShareManager mShareManager = null;
    private Platform mCurrentPlatform;//当前平台内容

    private ShareManager() {
    }

    public static ShareManager getInstance() {
        if (mShareManager == null) {
            synchronized (ShareManager.class) {
                if (mShareManager == null) {
                    mShareManager = new ShareManager();
                }
            }
        }
        return mShareManager;
    }


    /**
     * 初始化ShareSDK
     *
     * @param context
     */
    public static void init(Context context) {
        MobSDK.init(context);
    }

    /**
     * 分享数据
     *
     * @param shareData
     * @param listener
     */
    public void shareData(ShareData shareData, PlatformActionListener listener) {
        switch (shareData.platFormType) {
            case QQ:
                mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZone:
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case Wechat:
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WechatMoments:
                mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            default:
                break;
        }
        mCurrentPlatform.setPlatformActionListener(listener);//由应用层处理回调
        mCurrentPlatform.share(shareData.params);
    }

    /**
     * 第三方用户授权信息获取
     *
     *                                             将获取到的用户信息发送到服务器                               是
     * 登陆流程：通过Aouth认证拿到第三方平台用户信息--------------------------------> 服务器判断是否绑定过本地帐号------->  返回本地系统帐号信息登陆应用
     *                                                                                                      不是
     *                                                                                                    ------->直接用第三方信息登陆应用(应用内可以提供绑定功能)
     * @param platFormType
     * @param listener
     */
    public void getUserInfo(PlatFormType platFormType, PlatformActionListener listener) {
        switch (platFormType) {
            case QQ:
                mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZone:
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case Wechat:
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WechatMoments:
                mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            default:
                break;
        }
        mCurrentPlatform.setPlatformActionListener(listener);//由应用层处理回调
        mCurrentPlatform.showUser(null);//为null表示仅显示自己资料
    }

    /**
     * 删除授权
     */
    public void removeAccount(){
        if(mCurrentPlatform!=null){
            mCurrentPlatform.getDb().removeAccount();
        }
    }

    /**
     * 应用所需要的平台类型
     */
    public enum PlatFormType {
        QQ,//QQ
        QZone,//QQ空间
        Wechat,//微信
        WechatMoments;//微信朋友圈
    }


}
