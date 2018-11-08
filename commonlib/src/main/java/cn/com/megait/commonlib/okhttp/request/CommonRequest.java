package cn.com.megait.commonlib.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author TimeW
 * @function 为我们产生Request对象
 * @date 2018/11/7
 */
public class CommonRequest {

    public static Request createPostRequest(String url, RequestParams bodyParams) {
        return createPostRequest(url, bodyParams, null);
    }

    /**
     * Post请求
     * @param url
     * @param bodyParams
     * @param headerParams
     * @return
     */
    public static Request createPostRequest(String url, RequestParams bodyParams, RequestParams headerParams) {
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        //添加BodyParam
        if (bodyParams != null) {
            for (Map.Entry<String, String> entry : bodyParams.urlParams.entrySet()) {
                formBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加Header
        Headers.Builder headersBuild = new Headers.Builder();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                headersBuild.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = formBodyBuild.build();
        Headers headers = headersBuild.build();
        return new Request.Builder().url(url)
                .headers(headers)
                .post(formBody)
                .build();
    }

    public static Request createGetRequest(String url, RequestParams bodyParams) {
        return createGetRequest(url, bodyParams, null);
    }

    /**
     * Get请求
     * @param url
     * @param bodyParams
     * @param headerParams
     * @return
     */
    public static Request createGetRequest(String url, RequestParams bodyParams, RequestParams headerParams) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        //拼接URL
        if (bodyParams != null) {
            for (Map.Entry<String, String> entry : bodyParams.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        //添加Header
        Headers.Builder headersBuild = new Headers.Builder();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                headersBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers headers = headersBuild.build();
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .headers(headers)
                .get()
                .build();
    }

    /**
     * 八位字节流
     */
    private static final MediaType FILE_TYPE=MediaType.parse("application/octet-steam");

    /**
     * 表格提交请求
     * @param url
     * @param requestParams
     * @return
     */
    public static Request createMultiPostRequset(String url,RequestParams requestParams){
        MultipartBody.Builder requestBody=new MultipartBody.Builder();
        //注明请求头类型为:multipart/form-data
        requestBody.setType(MultipartBody.FORM);
        if(requestParams !=null){
            for(Map.Entry<String ,Object> entry: requestParams.fileParams.entrySet()){
                if(entry.getValue() instanceof File){
                    requestBody.addPart(Headers.of("Content-Disposition","form-data; name=\""+entry.getKey()+"\"")
                            ,RequestBody.create(FILE_TYPE,(File) entry.getValue()));
                }else if(entry.getValue() instanceof  String){
                    requestBody.addPart(Headers.of("Content-Disposition","form-data; name=\""+entry.getKey()+"\"")
                            ,RequestBody.create(null,(String)entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(requestBody.build())
                .build();
    }
}
