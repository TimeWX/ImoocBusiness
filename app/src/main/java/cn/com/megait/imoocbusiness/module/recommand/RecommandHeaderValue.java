package cn.com.megait.imoocbusiness.module.recommand;

import java.util.ArrayList;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/11/21
 */
public class RecommandHeaderValue extends BaseModel {

    public ArrayList<String> ads;
    public ArrayList<String> middle;
    public ArrayList<RecommandFooterValue> footer;
}
