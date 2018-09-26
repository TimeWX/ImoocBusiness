package cn.com.megait.imoocbusiness.view.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.view.fragment.BaseFragment;

public class HomeFragment extends BaseFragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder=null;
        }
        super.onDestroyView();

    }

    @OnClick({R.id.qrcode_view, R.id.category_view, R.id.search_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qrcode_view:
                break;
            case R.id.category_view:
                break;
            case R.id.search_view:
                break;
        }
    }
}
