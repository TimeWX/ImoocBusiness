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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.base.BaseActivity;
import cn.com.megait.imoocbusiness.adapter.SearchAdapter;
import cn.com.megait.imoocbusiness.db.database.DBDataHelper;
import cn.com.megait.imoocbusiness.db.database.DBHelper;
import cn.com.megait.imoocbusiness.module.BaseModel;
import cn.com.megait.imoocbusiness.module.search.GoodsModel;

/**
 * @author TimeW
 * @function 搜索界面, 提供商品搜索
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
    private ArrayList<BaseModel> historyListDatas;
    private ArrayList<BaseModel> searchingListDatas;
    private GoodsModel goodsModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);
        initWidget();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String selections = goodsInputView.getText().toString();
            if (selections != null && !selections.trim().equals("")) {

            }

        }
    };



    /**
     * 进入搜索模式
     */
    private void entrySearchMode() {
        goodsHistoryLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        goodsSearchLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 进入空模式,删除历史表
     */
    private void entryEmptyMode() {
        DBDataHelper.getInstance().delete(DBHelper.GOODS_BROWER_TABLE, null, null);
        goodsSearchLayout.setVisibility(View.GONE);
        goodsHistoryLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 进入历史记录模式
     */
    private void entryHistoryMode() {
        goodsHistoryLayout.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        goodsSearchLayout.setVisibility(View.GONE);
        historyListDatas = DBDataHelper.getInstance().select(DBHelper.GOODS_BROWER_TABLE, null, null
                , DBHelper.TIME + DBHelper.DESC, GoodsModel.class);
        if (historyAdapter == null) {
            historyAdapter = new SearchAdapter(this, historyListDatas);
            historyListView.setAdapter(historyAdapter);
        } else {
            historyAdapter.updateData(historyListDatas);
        }
    }

    /**
     * 获取历史记录
     * @return
     */
    private int getHistoryData() {
        return DBDataHelper.getInstance().select(DBHelper.GOODS_BROWER_TABLE, null,
                null, null, GoodsModel.class).size();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        changeStatusBarColor(R.color.color_ffffff);
        goodsListView.setEmptyView(goodsSearchEmptyLayout);
        goodsListView.setOnItemClickListener(this);
        goodsInputView.addTextChangedListener(textWatcher);
        decideWhichMode();
    }

    /**
     * 判断当前视图模式,选择进入空界面/历史浏览界面
     */
    private void decideWhichMode() {

        if(getHistoryData()==0){
            entryEmptyMode();
        }else{
            entryHistoryMode();
        }
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
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
