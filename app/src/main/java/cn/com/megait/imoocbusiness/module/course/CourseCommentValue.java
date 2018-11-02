package cn.com.megait.imoocbusiness.module.course;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/11/2
 */
public class CourseCommentValue extends BaseModel {
    public String text;
    public String name;
    public String logo;
    public int type;
    public String userId; //评论所属用户ID
}
