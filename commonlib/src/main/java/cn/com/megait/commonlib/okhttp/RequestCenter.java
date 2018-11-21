package cn.com.megait.commonlib.okhttp;

import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import cn.com.megait.commonlib.okhttp.request.CommonRequest;

/**
 * @author lenovo
 * @function 存放应用中所有的请求
 * @date 2018/11/7
 */
public class RequestCenter {







    /**
     * 发送广告请求
     * @param url
     * @param disposeDataHandle
     */
    public static void sendImageAdRequest(String url, DisposeDataHandle disposeDataHandle){
        CommonOKHttpClient.post(CommonRequest.createPostRequest(url,null),disposeDataHandle);
    }
}
