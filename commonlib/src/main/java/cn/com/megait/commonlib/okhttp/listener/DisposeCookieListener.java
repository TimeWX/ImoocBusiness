package cn.com.megait.commonlib.okhttp.listener;

import java.util.ArrayList;

/**
 * @author TimeW
 * @function 处理创建Cookie时的回调接口
 * @date 2018/11/11
 */
public interface DisposeCookieListener extends DisposeDataListener {

    public void onCookie(ArrayList<String> cookieStrLists);
}
