package cn.com.megait.commonlib.okhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;

import cn.com.megait.commonlib.okhttp.exception.OKHttpException;
import cn.com.megait.commonlib.okhttp.listener.DisposeCookieListener;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author TimeW
 * @function
 * @date 2018/11/9
 */
public class CommonJsonCallback implements Callback {

    /**
     * 与服务器返回的对应关系
     */
    protected final String ECODE = "ecode";
    protected final String EMSG = "emsg";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String EMPTY_MSG = "";

    protected final String COOKIES="Set-Cookie";

    /**
     * 自定义异常码
     */
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle disposeDataHandle) {
        this.mListener = disposeDataHandle.getListener();
        this.mClass = disposeDataHandle.getmClass();
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        /**
         * 此处还在非UI线程,所以要转发
         */
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFaliure(new OKHttpException(NETWORK_ERROR, e));
            }
        });

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {

        final String result = response.body().string();
        final ArrayList<String> cookiesStrLists=handleCookie(response.headers());
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
                //处理Cookie
                if(mListener instanceof DisposeCookieListener){
                    ((DisposeCookieListener)mListener).onCookie(cookiesStrLists);
                }
            }
        });
    }

    private ArrayList<String> handleCookie(Headers headers) {
        ArrayList<String> arrs=new ArrayList<>();
        for (int i = 0; i <headers.size() ; i++) {
            if(headers.name(i).equalsIgnoreCase(COOKIES)){
                arrs.add(headers.value(i));
            }
        }
        return arrs;
    }

    private void handleResponse(String result) {
        if (TextUtils.isEmpty(result.trim())) {
            mListener.onFaliure(new OKHttpException(JSON_ERROR, EMPTY_MSG));
            return;
        }
        try {
            if (mClass != null) {
                mListener.onSuccess(result);
            } else {
                Object object = JSON.parseObject(result, mClass);
                if (object == null) {
                    mListener.onFaliure(new OKHttpException(JSON_ERROR, EMPTY_MSG));
                } else {
                    mListener.onSuccess(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
