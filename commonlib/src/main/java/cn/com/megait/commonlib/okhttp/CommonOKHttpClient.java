package cn.com.megait.commonlib.okhttp;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.com.megait.commonlib.okhttp.https.HttpsUtils;
import okhttp3.OkHttpClient;

/**
 * @author lenovo
 * @function 请求的发送,请求参数的配置,Https请求
 * @date 2018/11/7
 */
public class CommonOKHttpClient {

    private static final int TIME_OUT_SECOND=30;
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
        //设置超时时间
        builder.connectTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS);
        //设置请求重定向
        builder.followRedirects(true);
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager());

    }
}
