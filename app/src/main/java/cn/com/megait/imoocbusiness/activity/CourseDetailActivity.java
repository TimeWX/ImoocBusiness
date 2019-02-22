package cn.com.megait.imoocbusiness.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.activity.base.BaseActivity;
import cn.com.megait.imoocbusiness.module.course.BaseCourseModel;
import cn.com.megait.imoocbusiness.util.Util;

/**
 * @author lenovo
 * @function 展示商品详情, 此启动模式为SingleTop
 * @date 2018/11/2
 */
public class CourseDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String COURSE_ID = "course_id";
    @BindView(R.id.back_view)
    ImageView backView;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.loading_view)
    ImageView loadingView;
    @BindView(R.id.jianpan_view)
    ImageView jianpanView;
    @BindView(R.id.comment_edit_view)
    EditText commentEditView;
    @BindView(R.id.send_view)
    TextView sendView;
    @BindView(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @BindView(R.id.comment_list_view)
    ListView commentListView;

    private String courseId;
    private String tempHint;
    private BaseCourseModel baseCourseModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_layout);
        ButterKnife.bind(this);
        initData();
        initWidget();
        requestDetail();
    }

    private void initWidget() {
        commentListView.setVisibility(View.GONE);
        commentListView.setOnItemClickListener(this);
        loadingView.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.start();
        bottomLayout.setVisibility(View.GONE);
        entryEmptyMode();

    }

    /**
     * 进入空状态
     */
    private void entryEmptyMode() {
        tempHint="";
        commentEditView.setText("");
        commentEditView.setHint(getString(R.string.input_comment));
        Util.hideSoftInputMethod(this,commentEditView);
    }

    /**
     * 进入编辑状态
     */
    private void entryEditMode(String hint) {
        commentEditView.requestFocus();
        commentEditView.setHint(hint);
        Util.showSoftInputMethod(this, commentEditView);
    }


    private void initData() {
        Intent intent = getIntent();
        courseId = intent.getStringExtra(COURSE_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        initWidget();
        requestDetail();

    }

    private void requestDetail() {
        
    }

    @OnClick({R.id.back_view, R.id.jianpan_view, R.id.send_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_view:
                break;
            case R.id.jianpan_view:
                break;
            case R.id.send_view:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
