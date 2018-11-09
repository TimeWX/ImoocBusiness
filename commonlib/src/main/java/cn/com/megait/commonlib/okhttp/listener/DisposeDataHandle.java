package cn.com.megait.commonlib.okhttp.listener;

/**
 * @author lenovo
 * @function 数据处理控制器封装
 * @date 2018/11/9
 */
public class DisposeDataHandle {
    private DisposeDataListener disposeDataListener=null;
    private Class<?> mClass=null;
    private String mSource=null;

    public DisposeDataHandle(DisposeDataListener listener){
        this.disposeDataListener=listener;
    }

    public DisposeDataHandle(DisposeDataListener listener,Class<?> cls){
        this.disposeDataListener=listener;
        this.mClass=cls;
    }

    public DisposeDataHandle(DisposeDataListener listener,String source){
        this.disposeDataListener=listener;
        this.mSource=source;
    }

    public DisposeDataListener getListener() {
        return disposeDataListener;
    }

    public Class<?> getmClass() {
        return mClass;
    }

    public String getSource() {
        return mSource;
    }
}
