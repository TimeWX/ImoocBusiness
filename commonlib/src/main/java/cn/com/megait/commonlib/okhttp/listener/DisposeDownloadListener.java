package cn.com.megait.commonlib.okhttp.listener;

/**
 * @author lenovo
 * @function
 * @date 2018/11/11
 */
public interface DisposeDownloadListener extends DisposeDataListener{

    /**
     * 下载进度
     * @param progress 0-100 进度
     */
    public void onPorgress(int progress);
}
