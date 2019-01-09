package cn.com.megait.commonlib.core;

/**
 * @author lenovo
 * @function
 * @date 2019/1/9
 */
public interface AdContextInteface {
    /**
     * 广告加载成功
     */
    void onAdSuccess();

    /**
     * 广告加载失败
     */
    void onAdFailed();

    /**
     * 点击VideoView
     * @param url
     */
    void onClickVideo(String url);
}
