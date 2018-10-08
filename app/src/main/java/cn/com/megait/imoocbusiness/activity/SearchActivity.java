package cn.com.megait.imoocbusiness.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.base.BaseActivity;
import cn.com.megait.imoocbusiness.adapter.SearchAdapter;
import cn.com.megait.imoocbusiness.module.BaseModel;
import cn.com.megait.imoocbusiness.module.search.GoodsModel;

/**
 * @author TimeW
 * @function 搜索界面,提供商品搜索
 * @date 2018/9/29
 */
public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    /**
     * 公共UI
     */
    @BindView(R.id.cancel_view)
    TextView cancelView;
    @BindView(R.id.goods_input_view)
    EditText goodsInputView;

    /**
     * 历史相关UI
     */
    @BindView(R.id.history_list_view)
    ListView historyListView;
    @BindView(R.id.delect_histroy_view)
    TextView delectHistroyView;
    @BindView(R.id.goods_history_layout)
    LinearLayout goodsHistoryLayout;

    /**
     * 既没有历史数据,也没有搜索数据,空界面
     */
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    /**
     * 正在搜索界面
     */
    @BindView(R.id.goods_list_view)
    ListView goodsListView;
    @BindView(R.id.seach_no_goods_info_view)
    TextView seachNoGoodsInfoView;
    @BindView(R.id.goods_search_empty_layout)
    LinearLayout goodsSearchEmptyLayout;
    @BindView(R.id.goods_search_layout)
    LinearLayout goodsSearchLayout;

    /**
     * data
     */
    private SearchAdapter historyAdapter;
    private SearchAdapter searchAdapter;
    private List<BaseModel> historyListDatas;
    private List<BaseModel> searchingListDatas;
    private GoodsModel goodsModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);
        initWidget();
    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String selections=goodsInputView.getText().toString();
            if(selections!=null && !selections.trim().equals("")){

            }

        }
    };

    private int getHistoryData(){

        return 0;
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        goodsListView.setEmptyView(goodsSearchEmptyLayout);
        goodsListView.setOnItemClickListener(this);
        goodsInputView.addTextChangedListener(textWatcher);
        decideWhichMode();
    }

    private void decideWhichMode() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @OnClick({R.id.cancel_view, R.id.delect_histroy_view, R.id.goods_list_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_view:
                break;
            case R.id.delect_histroy_view:
                break;
            case R.id.goods_list_view:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
