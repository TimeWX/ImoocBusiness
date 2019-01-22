package cn.com.megait.imoocbusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.megait.commonlib.adutil.Utils;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.base.BaseActivity;
import cn.com.megait.imoocbusiness.adapter.PhotoPagerAdapter;
import cn.com.megait.imoocbusiness.util.Util;

/**
 * @author lenovo
 * @function 显示产品大图片页面
 * @date 2019/1/22
 */
public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.tv_indicator_view)
    TextView tvIndicatorView;
    @BindView(R.id.iv_share_view)
    ImageView ivShareView;
    @BindView(R.id.vp_photo_pager)
    ViewPager vpPhotoPager;

    public static final String PHOTO_LIST = "photo_list";

    private PhotoPagerAdapter mPhotoPagerAdapter;
    private ArrayList<String> mPhotoLists;
    private int mLength;
    private int mCurrPos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_layout);
        ButterKnife.bind(this);
        initData();
        initView();

    }


    private void initData() {
        mPhotoLists = getIntent().getStringArrayListExtra(PHOTO_LIST);
        mLength = mPhotoLists.size();
    }

    private void initView() {
        tvIndicatorView.setText("1/" + mLength);
        mPhotoPagerAdapter = new PhotoPagerAdapter(this, mPhotoLists, false);
        vpPhotoPager.setPageMargin(Utils.dip2px(this, 30));
        vpPhotoPager.setAdapter(mPhotoPagerAdapter);
        vpPhotoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tvIndicatorView.setText(String.valueOf((1 + i)).concat("/").concat(String.valueOf(mLength)));
                mCurrPos = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        Util.hideSoftInputMethod(this, tvIndicatorView);

    }


    @OnClick(R.id.iv_share_view)
    public void onViewClicked() {
    }
}
