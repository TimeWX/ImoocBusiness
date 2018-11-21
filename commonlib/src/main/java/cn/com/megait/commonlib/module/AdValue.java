package cn.com.megait.commonlib.module;

import java.util.ArrayList;

import cn.com.megait.commonlib.module.monitor.Monitor;
import cn.com.megait.commonlib.module.monitor.emevent.EMEvent;

/**
 * @author lenovo
 * @function
 * @date 2018/11/21
 */
public class AdValue {

    public String resourceID;
    public String adid;
    public String resource;
    public String thumb;
    public ArrayList<Monitor> startMonitor;
    public ArrayList<Monitor> middleMonitor;
    public ArrayList<Monitor> endMonitor;
    public String clickUrl;
    public ArrayList<Monitor> clickMonitor;
    public EMEvent event;
    public String type;
}
