package cn.com.megait.imoocbusiness.module.search;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author TimeW
 * @function 搜索实体
 * @date 2018/9/29
 */
public class GoodsModel extends BaseModel {
    public int _id;
    public String goodscode;
    public String abbrev;
    public String spell;
    public String type;
    public String time = "10000";
}
