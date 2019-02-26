package cn.com.megait.imoocbusiness.share;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.megait.imoocbusiness.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * @author lenovo
 * @function
 * @date 2019/1/22
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.rl_download_layout)
    RelativeLayout rlDownloadLayout;

    private Context mContext;
    private boolean isShowDownload;
    private DisplayMetrics mDisplayMetrics;

    /**
     * share relative
     */
    private int mShareType; //指定分享类型
    private String mShareTitle; //指定分享内容标题
    private String mShareText; //指定分享内容文本
    private String mSharePhoto; //指定分享本地图片
    private String mShareTileUrl;
    private String mShareSiteUrl;//分享的来源地址
    private String mShareSite;//分享的来源
    private String mUrl;
    private String mResourceUrl;

    public ShareDialog(Context context, boolean isShowDownload) {
        super(context);
        mContext = context;
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.isShowDownload = isShowDownload;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
        initView();
    }

    private void initView() {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = mDisplayMetrics.widthPixels;//设置宽度
        dialogWindow.setAttributes(lp);
        if (isShowDownload) {
            rlDownloadLayout.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.rl_weixin_layout, R.id.rl_moment_layout, R.id.rl_qq_layout, R.id.rl_qqzone_layout, R.id.rl_download_layout, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_weixin_layout:
                /**
                 * TODO Now-Coding
                 */
                break;
            case R.id.rl_moment_layout:
                break;
            case R.id.rl_qq_layout:
                share(ShareManager.PlatFormType.QQ);
                break;
            case R.id.rl_qqzone_layout:
                break;
            case R.id.rl_download_layout:
                break;
            case R.id.tv_cancel:
                break;
        }
    }



    /**
     * 分享
     * @param platFormType
     */
    private void share(ShareManager.PlatFormType platFormType){

        ShareData shareData=new ShareData();
        Platform.ShareParams shareParams=new Platform.ShareParams();
        shareParams.setShareType(mShareType);
        shareParams.setTitle(mShareTitle);
        shareParams.setTitleUrl(mShareTileUrl);
        shareParams.setText(mShareText);
        shareParams.setImagePath(mSharePhoto);
        shareParams.setSite(mShareSite);
        shareParams.setSiteUrl(mShareSiteUrl);
        shareParams.setUrl(mUrl);
        shareData.params=shareParams;
        shareData.platFormType=platFormType;
        ShareManager.getInstance().shareData(shareData,mListener);
    }

    /**
     * 定义的平台回调接口
     */
    private PlatformActionListener mListener=new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    };

    public void setResourceUrl(String resourceUrl) {
        mResourceUrl = resourceUrl;
    }

    public void setShareTitle(String title) {
        mShareTitle = title;
    }

    public void setImagePhoto(String photo) {
        mSharePhoto = photo;
    }

    public void setShareType(int type) {
        mShareType = type;
    }

    public void setShareSite(String site) {
        mShareSite = site;
    }

    public void setShareTitleUrl(String titleUrl) {
        mShareTileUrl = titleUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setShareSiteUrl(String siteUrl) {
        mShareSiteUrl = siteUrl;
    }

    public void setShareText(String text) {
        mShareText = text;
    }
}