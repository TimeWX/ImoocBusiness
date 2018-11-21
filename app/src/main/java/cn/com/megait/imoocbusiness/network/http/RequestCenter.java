package cn.com.megait.imoocbusiness.network.http;

import cn.com.megait.commonlib.okhttp.CommonOKHttpClient;
import cn.com.megait.commonlib.okhttp.HttpConstantS;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataListener;
import cn.com.megait.commonlib.okhttp.request.CommonRequest;
import cn.com.megait.commonlib.okhttp.request.RequestParams;
import cn.com.megait.imoocbusiness.module.recommand.BaseRecommandModel;
import cn.com.megait.imoocbusiness.module.user.User;
import okhttp3.Call;

/**
 * @author lenovo
 * @function
 * @date 2018/11/21
 */
public class RequestCenter {

    /**
     * POST请求
     * @param url
     * @param bodyParams
     * @param headerParams
     * @param listener
     * @param clz
     * @return
     */
    private static Call postRequest(String url, RequestParams bodyParams,RequestParams headerParams, DisposeDataListener listener, Class<?> clz){
        return CommonOKHttpClient.post(CommonRequest.createPostRequest(url,bodyParams,headerParams),new DisposeDataHandle(listener,clz));
    }

    public static void login(String username,String pwd,DisposeDataListener listener){
        RequestParams bodyParams=new RequestParams();
        bodyParams.put("username",username);
        bodyParams.put("pwd",pwd);
        postRequest(HttpConstants.LOGIN,bodyParams,null,listener,User.class);
    }

    /**
     * 首页推荐数据请求
     * @param listener
     */
    public static Call requestRecommandData(DisposeDataListener listener){
        return postRequest(HttpConstantS.HOME_RECOMMAND,null,null,listener,BaseRecommandModel.class);
    }
}
