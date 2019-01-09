package cn.com.megait.imoocbusiness.module.recommand;

import java.util.ArrayList;

import cn.com.megait.commonlib.module.monitor.Monitor;
import cn.com.megait.commonlib.module.monitor.emevent.EMEvent;
import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author TimeW
 * @function
 * @date 2018/11/21
 */
public class RecommandBodyValue extends BaseModel {

    public int type;
    public String logo;
    public String title;
    public String info;
    public String price;
    public String text;
    public String site;
    public String from;
    public String zan;
    public ArrayList<String> url;

    //视频专用
    public String thumb;
    public String resource;
    public String resourceID;
    public String adid;
    public ArrayList<Monitor> startMonitor;
    public ArrayList<Monitor> middleMonitor;
    public ArrayList<Monitor> endMonitor;
    public String clickUrl;
    public ArrayList<Monitor> clickMonitor;
    public EMEvent event;

}
