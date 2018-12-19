package cn.com.megait.commonlib.report;

import java.util.ArrayList;

import cn.com.megait.commonlib.adutil.Utils;
import cn.com.megait.commonlib.module.monitor.Monitor;
import cn.com.megait.commonlib.okhttp.CommonOKHttpClient;
import cn.com.megait.commonlib.okhttp.HttpConstants;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataListener;
import cn.com.megait.commonlib.okhttp.request.CommonRequest;
import cn.com.megait.commonlib.okhttp.request.RequestParams;

/**
 * @author lenovo
 * @function 负责所有检测请求发送
 * @date 2018/12/19
 */
public class ReportManager {

    /**
     * 默认的事件回调处理
     */
    private static DisposeDataHandle handle=new DisposeDataHandle(new DisposeDataListener() {
        @Override
        public void onSuccess(Object responseObj) {

        }

        @Override
        public void onFaliure(Object reasonObj) {

        }
    });

    /**
     * send the sus monitor
     * @param monitors
     * @param isAuto
     */
    public static void susReport(ArrayList<Monitor> monitors,boolean isAuto){

        if(monitors!=null && monitors.size()>0){
            for (Monitor monitor:monitors
                 ) {
                RequestParams params=new RequestParams();
                if(Utils.containString(monitor.url, HttpConstants.ATM_PRE)){
                    params.put("ve","0");
                    if(isAuto){
                        params.put("auto","1");
                    }
                }
                CommonOKHttpClient.get(CommonRequest.createGetRequest(monitor.url,params),handle);
            }
        }
    }

    /**
     * send the sueReport
     * @param monitors
     * @param isFull
     * @param playTime
     */
    public static void sueReport(ArrayList<Monitor> monitors,boolean isFull, long playTime){

        /**
         *  TODO Now-Coding
         */
    }
}
