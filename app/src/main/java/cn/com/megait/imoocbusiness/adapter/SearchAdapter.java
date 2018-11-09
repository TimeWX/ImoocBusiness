package cn.com.megait.imoocbusiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.megait.imoocbusiness.R;
import cn.com.megait.imoocbusiness.module.BaseModel;
import cn.com.megait.imoocbusiness.module.search.GoodsModel;

/**
 * @author lenovo
 * @function
 * @date 2018/9/29
 */
public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BaseModel> mListData;
    private ViewHolder holder;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, ArrayList<BaseModel> listData) {
        this.mContext = context;
        this.mListData = listData;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_search_layout, null);
            holder.goodsNameView = (TextView) convertView.findViewById(R.id.goods_name_view);
            holder.goodsCodeView = (TextView) convertView.findViewById(R.id.goods_code_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 根据数据初始化item
        GoodsModel fundSearch = (GoodsModel) getItem(position);
        holder.goodsNameView.setText(fundSearch.abbrev);
        holder.goodsCodeView.setText(fundSearch.goodscode);
        return convertView;
    }

    public void updateData(ArrayList<BaseModel> listData) {
        this.mListData = listData;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView goodsNameView;
        private TextView goodsCodeView;
    }
}
