package cn.com.megait.imoocbusiness.view.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lenovo
 * @function
 * @date 2018/11/21
 */
public class CommonFragment extends Fragment {
    int index=1;

    public CommonFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getContentView();
    }

    private View getContentView() {
        RelativeLayout rootLayout=new RelativeLayout(this.getActivity());
        rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        TextView textView=new TextView(this.getActivity());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        RelativeLayout.LayoutParams textViewParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if(index==1){
            textView.setText("鱼塘页");
        }else {
            textView.setText("我的页");
        }
        textView.setLayoutParams(textViewParams);
        rootLayout.addView(textView);
        return rootLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
