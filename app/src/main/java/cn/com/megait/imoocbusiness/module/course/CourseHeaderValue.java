package cn.com.megait.imoocbusiness.module.course;

import java.util.ArrayList;

import cn.com.megait.commonlib.module.AdValue;
import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/11/2
 */
public class CourseHeaderValue extends BaseModel {
    public ArrayList<String> photoUrls;
    public String text;
    public String name;
    public String logo;
    public String oldPrice;
    public String newPrice;
    public String zan;
    public String scan;
    public String hotComment;
    public String from;
    public String dayTime;
    public AdValue video;
}
