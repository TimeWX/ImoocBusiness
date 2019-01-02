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
    private static DisposeDataHandle handle = new DisposeDataHandle(new DisposeDataListener() {
        @Override
        public void onSuccess(Object responseObj) {

        }

        @Override
        public void onFaliure(Object reasonObj) {

        }
    });

    /**
     * send the sus monitor
     *
     * @param monitors
     * @param isAuto
     */
    public static void susReport(ArrayList<Monitor> monitors, boolean isAuto) {

        if (monitors != null && monitors.size() > 0) {
            for (Monitor monitor : monitors
                    ) {
                RequestParams params = new RequestParams();
                if (Utils.containString(monitor.url, HttpConstants.ATM_PRE)) {
                    params.put("ve", "0");
                    if (isAuto) {
                        params.put("auto", "1");
                    }
                }
                CommonOKHttpClient.get(CommonRequest.createMonitorRequest(monitor.url, params), handle);
            }
        }
    }

    /**
     * send the sue report
     *
     * @param monitors
     * @param isFull
     * @param playTime
     */
    public static void sueReport(ArrayList<Monitor> monitors, boolean isFull, long playTime) {

        if (monitors != null && monitors.size() > 0) {
            for (Monitor monitor : monitors
                    ) {
                RequestParams params = new RequestParams();
                if (Utils.containString(monitor.url, HttpConstants.ATM_PRE)) {
                    if (isFull) {
                        params.put("fu", "1");
                    }
                    params.put("ve", String.valueOf(playTime));
                }
                CommonOKHttpClient.get(CommonRequest.createMonitorRequest(monitor.url, params), handle);
            }
        }
    }


    /**
     * send the su reprot
     *
     * @param monitors
     * @param playTime
     */
    public static void suReport(ArrayList<Monitor> monitors, long playTime) {
        if (monitors != null && monitors.size() > 0) {
            for (Monitor monitor : monitors
                    ) {
                RequestParams params = new RequestParams();
                if (monitor.time == playTime) {
                    if (Utils.containString(monitor.url, HttpConstants.ATM_PRE)) {
                        params.put("ve", String.valueOf(playTime));
                    }
                    CommonOKHttpClient.get(CommonRequest.createMonitorRequest(monitor.url, params), handle);
                }

            }
        }

    }

    /**
     * send the click full button monitor
     * @param monitors
     * @param playTime
     */
    public static void fullScreenReport(ArrayList<Monitor> monitors, long playTime) {
        if (monitors != null && monitors.size() > 0) {
            for (Monitor monitor : monitors
                    ) {
                RequestParams params = new RequestParams();
                if (Utils.containString(monitor.url, HttpConstants.ATM_PRE)) {
                    params.put("ve", String.valueOf(playTime));
                }
                CommonOKHttpClient.get(CommonRequest.createMonitorRequest(monitor.url, params), handle);
            }
        }
    }

    /**
     * send the video pause monitor
     * @param monitors
     * @param playTime
     */
    public static void pauseVideoReport(ArrayList<Monitor> monitors,long playTime){
        if(monitors!=null && monitors.size()>0){
            for(Monitor monitor:monitors){
                RequestParams params=new RequestParams();
                if(Utils.containString(monitor.url,HttpConstants.ATM_PRE)){
                    params.put("ve",String.valueOf(playTime));
                }
                CommonOKHttpClient.get(CommonRequest.createMonitorRequest(monitor.url,params),handle);
            }
        }
    }
}
