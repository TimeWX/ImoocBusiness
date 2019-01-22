package cn.com.megait.imoocbusiness.view.fragment.home;

import android.content.Intent;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataListener;
import cn.com.megait.commonlib.zxing.app.CaptureActivity;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.SearchActivity;
import cn.com.megait.imoocbusiness.adapter.CourseAdapter;
import cn.com.megait.imoocbusiness.constant.Constants;
import cn.com.megait.imoocbusiness.module.recommand.BaseRecommandModel;
import cn.com.megait.imoocbusiness.module.recommand.RecommandBodyValue;
import cn.com.megait.imoocbusiness.network.http.RequestCenter;
import cn.com.megait.imoocbusiness.util.Util;
import cn.com.megait.imoocbusiness.view.fragment.BaseFragment;
import cn.com.megait.imoocbusiness.view.home.HomeHeadLayout;

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.qrcode_view)
    TextView qrcodeView;
    @BindView(R.id.category_view)
    TextView categoryView;
    @BindView(R.id.search_view)
    TextView searchView;
    @BindView(R.id.loading_view)
    ImageView loadingView;
    @BindView(R.id.list_view)
    ListView listView;

    Unbinder unbinder;
    private View mContentView;
    private BaseRecommandModel mBaseRecommandModel;
    private CourseAdapter mAdapter;

    private static final int REQUEST_QRCODE = 0X01;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        initView();
        return mContentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommandData();
    }

    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                if (responseObj instanceof BaseRecommandModel) {
                    mBaseRecommandModel = (BaseRecommandModel) responseObj;
                    showSuccessView();
                }
            }

            @Override
            public void onFaliure(Object reasonObj) {
                showFailedView();
            }
        });
    }

    private void showSuccessView() {
        if (mBaseRecommandModel != null && mBaseRecommandModel.data != null
                && mBaseRecommandModel.data.list != null && mBaseRecommandModel.data.list.size() > 0) {
            loadingView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //ListView添加头
            listView.addHeaderView(new HomeHeadLayout(mContext,mBaseRecommandModel.data.head));



        }
        //更新UI
    }

    private void showFailedView() {
        //显示请求失败的View
    }

    @Override
    public void doOpenCamera() {
        Intent intent = new Intent(this.getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_QRCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                break;

        }
    }

    private void initView() {
        listView.setOnItemClickListener(this);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();

    }

    @OnClick({R.id.qrcode_view, R.id.category_view, R.id.search_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫一扫
            case R.id.qrcode_view:
                if (hasPermission(Constants.HARDWARE_CAMERA_PERMISSION)) {
                    doOpenCamera();
                } else {
                    applyForPermission(Constants.HARDWARE_CAMERA_CODE, Constants.HARDWARE_CAMERA_PERMISSION);
                }
                break;
            //目录
            case R.id.category_view:
                //与48038614用户进行QQ对话
                Util.skipToQQChat(mContext, "48038614");
                break;
            //搜索
            case R.id.search_view:
                //跳转到搜索界面
                Intent intent=new Intent(mContext,SearchActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RecommandBodyValue value= (RecommandBodyValue) mAdapter.getItem(position-listView.getHeaderViewsCount());
        if(value.type !=0){

        }




    }
}
