package cn.com.megait.imoocbusiness.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.base.BaseActivity;
import cn.com.megait.imoocbusiness.view.fragment.home.HomeFragment;
import cn.com.megait.imoocbusiness.view.fragment.home.MessageFragment;
import cn.com.megait.imoocbusiness.view.fragment.home.MineFragment;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;
    @BindView(R.id.home_image_view)
    TextView homeImageView;
    @BindView(R.id.home_layout_view)
    RelativeLayout homeLayoutView;
    @BindView(R.id.fish_image_view)
    TextView fishImageView;
    @BindView(R.id.pond_layout_view)
    RelativeLayout pondLayoutView;
    @BindView(R.id.message_image_view)
    TextView messageImageView;
    @BindView(R.id.message_layout_view)
    RelativeLayout messageLayoutView;
    @BindView(R.id.mine_image_view)
    TextView mineImageView;
    @BindView(R.id.mine_layout_view)
    RelativeLayout mineLayoutView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;


    private FragmentManager fm;
    private HomeFragment mHomeFragment;
    private Fragment mCommonFragmentOne;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrent;
    Unbinder unbinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(R.color.color_fed952);
        setContentView(R.layout.activity_home_layout);
        unbinder=ButterKnife.bind(this);
        startAllService();
        initFragment();
    }

    @Override
    protected void onDestroy() {
        if(unbinder!=null){
            unbinder.unbind();
            unbinder=null;
        }
        super.onDestroy();
    }

    private void initFragment() {
        fm=getSupportFragmentManager();
        mHomeFragment=new HomeFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout,mHomeFragment);
        fragmentTransaction.commit();
    }

    private void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction){
        if(fragment!=null){
            fragmentTransaction.hide(fragment);
        }
    }

    /**
     * 启动后台产品更新服务
     */
    private void startAllService() {
    }


    @OnClick({R.id.home_layout_view, R.id.pond_layout_view, R.id.message_layout_view, R.id.mine_layout_view})
    public void onViewClicked(View view) {
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        switch (view.getId()) {
            case R.id.home_layout_view:
                changeStatusBarColor(R.color.color_fed952);
                homeImageView.setBackgroundResource(R.mipmap.comui_tab_home_selected);
                fishImageView.setBackgroundResource(R.mipmap.comui_tab_pond);
                messageImageView.setBackgroundResource(R.mipmap.comui_tab_message);
                mineImageView.setBackgroundResource(R.mipmap.comui_tab_person);

                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, mHomeFragment);
                } else {
                    mCurrent = mHomeFragment;
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.pond_layout_view:
                break;
            case R.id.message_layout_view:
                changeStatusBarColor(R.color.color_e3e3e3);
                messageImageView.setBackgroundResource(R.mipmap.comui_tab_message_selected);
                homeImageView.setBackgroundResource(R.mipmap.comui_tab_home);
                fishImageView.setBackgroundResource(R.mipmap.comui_tab_pond);
                mineImageView.setBackgroundResource(R.mipmap.comui_tab_person);
                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout, mMessageFragment);
                } else {
                    mCurrent = mMessageFragment;
                    fragmentTransaction.show(mMessageFragment);
                }
                break;
            case R.id.mine_layout_view:
                changeStatusBarColor(R.color.color_ffffff);
                mineImageView.setBackgroundResource(R.mipmap.comui_tab_person_selected);
                homeImageView.setBackgroundResource(R.mipmap.comui_tab_home);
                fishImageView.setBackgroundResource(R.mipmap.comui_tab_pond);
                messageImageView.setBackgroundResource(R.mipmap.comui_tab_message);
                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
