package cn.com.megait.commonlib.okhttp.request;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @author TimeW
 * @function 为我们生产Request对象
 * @date 2018/11/7
 */
public class CommonRequest {

    public static Request createPostRequest(String url,RequestParams body){
        return createPostRequest(url,body,null);
    }

    public static Request createPostRequest(String url,RequestParams bodyParams,RequestParams headerParams){
        FormBody.Builder formBodyBuild= new FormBody.Builder();
        Headers.Builder headersBuild= new Headers.Builder();
        return  null;
    }
}
