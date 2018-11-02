package cn.com.megait.imoocbusiness.module.course;

import java.util.ArrayList;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/11/2
 */
public class CourseModel extends BaseModel {
    public CourseHeaderValue header;
    public CourseFooterValue footer;
    public ArrayList<CourseCommentValue> comments;
}
