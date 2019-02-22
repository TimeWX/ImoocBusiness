package cn.com.megait.imoocbusiness.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
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
    @BindView(R.id.goods_input_view)
    EditText goodsInputView;

    /**
     * 历史相关UI
     */
    @BindView(R.id.history_list_view)
    ListView historyListView;
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
            String input = goodsInputView.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                entrySearchMode();
                String selections = DBHelper.GOODS_CODE + " like ? or " + DBHelper.ABBREV + " like ? or " + DBHelper.SPELL + " like ? ";
                String[] selectionArgs = new String[]{"%" + input + "%", "%" + input + "%", "%" + input + "%"};
                ArrayList<BaseModel> searchingListDatas = DBDataHelper.getInstance().select(DBHelper.GOODS_LIST_TABLE, selections, selectionArgs, null, GoodsModel.class);
                if (searchAdapter == null) {
                    searchAdapter = new SearchAdapter(SearchActivity.this, searchingListDatas);
                    goodsListView.setAdapter(searchAdapter);
                } else {
                    searchAdapter.updateData(searchingListDatas);
                }
                goodsListView.smoothScrollToPosition(0);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    //FROM_HTML_MODE_LEGACY HTML块元素之间两个换行符
                    seachNoGoodsInfoView.setText(Html.fromHtml(getNoFundInfo(input), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    seachNoGoodsInfoView.setText(Html.fromHtml(getNoFundInfo(input)));
                }
            } else {
                decideWhichMode();
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
        ArrayList<BaseModel> historyListDatas = DBDataHelper.getInstance().select(DBHelper.GOODS_BROWER_TABLE, null, null
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
        historyListView.setOnItemClickListener(this);
        goodsInputView.addTextChangedListener(textWatcher);
        decideWhichMode();
    }

    /**
     * 判断当前视图模式,选择进入空界面/历史浏览界面
     */
    private void decideWhichMode() {
        if (getHistoryData() == 0) {
            entryEmptyMode();
        } else {
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


    @OnClick({R.id.cancel_view, R.id.delete_history_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_view:
                this.finish();
                break;
            case R.id.delete_history_view:
                DBDataHelper.getInstance().delete(DBHelper.GOODS_BROWER_TABLE,null,null);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        switch (parent.getId()) {
            case R.id.goods_list_view:
                goodsModel = (GoodsModel) searchAdapter.getItem(position);
                intent.putExtra(CourseDetailActivity.COURSE_ID, goodsModel.goodscode);
                startActivity(intent);
                break;
            case R.id.history_list_view:
                goodsModel = (GoodsModel) historyAdapter.getItem(position);
                intent.putExtra(CourseDetailActivity.COURSE_ID, goodsModel.goodscode);
                insertHistoryTable(goodsModel.goodscode);
                startActivity(intent);
                break;
        }

    }

    /**
     * 插入历史浏览记录表
     * @param goodscode 商品编码
     */
    private void insertHistoryTable(String goodscode) {
        final int MAX_HISTORY_BROWER_COUNT=10;//历史记录最大存储数
        //查询历史记录表是否有相关历史记录
        int count = DBDataHelper.getInstance().select(DBHelper.GOODS_BROWER_TABLE, DBHelper.GOODS_CODE + " = ? ", new String[]{goodscode}, null, GoodsModel.class).size();
        //没有相关历史记录,插入数据
        goodsModel.time=String.valueOf(System.currentTimeMillis());
        if(count==0){
            //历史记录没有超过存储值,则添加
            if(getHistoryData()<MAX_HISTORY_BROWER_COUNT){
                DBDataHelper.getInstance().insert(DBHelper.GOODS_BROWER_TABLE,goodsModel);
            //超过,则删除最早的历史记录
            }else {
                String whereClause= DBHelper.TIME+" = ?";
                String[] whereArgs=new String[]{"(select min(time) from "+DBHelper.GOODS_BROWER_TABLE+" )"};
                DBDataHelper.getInstance().delete(DBHelper.GOODS_BROWER_TABLE,whereClause,whereArgs);
                DBDataHelper.getInstance().insert(DBHelper.GOODS_BROWER_TABLE,goodsModel);
            }
            //有相关历史记录,更新历史记录查询的时间
        }else{
            String whereClause= DBHelper.GOODS_CODE+" = ?";
            String[] whereArgs=new String[]{goodscode};
            DBDataHelper.getInstance().update(DBHelper.GOODS_BROWER_TABLE,whereClause,whereArgs,goodsModel);
        }
    }

    private String getNoFundInfo(String info) {
        return  "<font color= #666666>"
                + getString(R.string.search_no_title) + "</font>"
                + "<font color= #ff3b3b>" + info + "</font>"
                + "<font color= #666666>" + getString(R.string.search_no_end)
                + "</font>";
    }
}
