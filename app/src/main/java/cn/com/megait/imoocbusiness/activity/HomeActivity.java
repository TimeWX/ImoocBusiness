package cn.com.megait.imoocbusiness.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.view.fragment.home.HomeFragment;
import cn.com.megait.imoocbusiness.view.fragment.home.MessageFragment;
import cn.com.megait.imoocbusiness.view.fragment.home.MineFragment;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fm;
    private HomeFragment mHomeFragment;
    private Fragment mCommonFragmentOne;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrent;

    @BindView(R.id.home_layout_view) RelativeLayout mHomeLayout;
    @BindView(R.id.pond_layout_view) RelativeLayout mPondLayout;
    @BindView(R.id.message_layout_view) RelativeLayout mMessageLayout;
    @BindView(R.id.mine_layout_view) RelativeLayout mMineLayout;
    @BindView(R.id.home_image_view) TextView mHomeView;
    @BindView(R.id.fish_image_view) TextView mPondView;
    @BindView(R.id.message_image_view) TextView mMessageView;
    @BindView(R.id.mine_image_view) TextView mMineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        ButterKnife.bind(this);
    }
}
