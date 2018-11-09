package cn.com.megait.commonlib.okhttp.listener;

/**
 * @author TimeW
 * @function 数据处理监听
 * @date 2018/11/9
 */
public interface DisposeDataListener {
    /**
     * 请求成功回调处理
     * @param responseObj
     */
    public void onSuccess(Object responseObj);


    /**
     * 请求失败回调处理
     * @param reasonObj
     */
    public void onFaliure(Object reasonObj);
}
