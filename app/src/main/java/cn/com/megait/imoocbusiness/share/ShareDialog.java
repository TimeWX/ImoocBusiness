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

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.megait.imoocbusiness.R;

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
                break;
            case R.id.rl_qqzone_layout:
                break;
            case R.id.rl_download_layout:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
