package cn.com.megait.commonlib.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.com.megait.commonlib.okhttp.callback.CommonFileCallback;
import cn.com.megait.commonlib.okhttp.callback.CommonJsonCallback;
import cn.com.megait.commonlib.okhttp.https.HttpsUtils;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author lenovo
 * @function 请求的发送,请求参数的配置,Https请求
 * @date 2018/11/7
 */
public class CommonOKHttpClient {

    private static final int TIME_OUT_SECOND=30;
    private static final String USER_AGENT_KEY="User-Agent";
    private static final String USER_AGENT_VALUE="Imooc_Mobile";
    private static OkHttpClient mOkHttpClient;

    //配置静态参数
    static {
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        //Https支持
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //支持所有Https请求
                return true;
            }
        });
        /**
         * 为所有请求添加头
         */
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request()
                        .newBuilder()
                        .addHeader(USER_AGENT_KEY,USER_AGENT_VALUE)
                        .build();
                return chain.proceed(request);
            }
        });
        //设置超时时间
        builder.connectTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        //设置请求重定向
        builder.followRedirects(true);
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager());
        mOkHttpClient=builder.build();
    }

    public static OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    /**
     * GET请求
     * @param request
     * @param disposeDataHandle
     * @return
     */
    public static Call get(Request request, DisposeDataHandle disposeDataHandle){
        Call call= mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataHandle));
        return call;
    }

    /**
     * POST请求
     * @param request
     * @param disposeDataHandle
     * @return
     */
    public static Call post(Request request, DisposeDataHandle disposeDataHandle){
        Call call= mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataHandle));
        return call;
    }

    /**
     * 文件下载
     * @param request
     * @param disposeDataHandle
     * @return
     */
    public static Call downloadFile(Request request,DisposeDataHandle disposeDataHandle){
        Call call=mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(disposeDataHandle));
        return call;
    }
}
