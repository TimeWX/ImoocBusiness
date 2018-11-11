package cn.com.megait.commonlib.okhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.com.megait.commonlib.okhttp.exception.OKHttpException;
import cn.com.megait.commonlib.okhttp.listener.DisposeDataHandle;
import cn.com.megait.commonlib.okhttp.listener.DisposeDownloadListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author TimeW
 * @function 处理文件下载回调
 * @date 2018/11/9
 */
public class CommonFileCallback implements Callback {
    /**
     * 自定义异常码
     */
    protected final int NETWORK_ERROR = -1;
    protected final int IO_ERROR = -2;
    protected final String EMPTY_MSG = "";

    private static final int PROGRESS_MESSAGE = 0X01;

    private DisposeDownloadListener mListener;
    private String mFilePath;
    private Handler mDeliveryHandler;
    private int mProgress;

    public CommonFileCallback(DisposeDataHandle disposeDataHandle) {
        this.mListener = (DisposeDownloadListener) disposeDataHandle.getListener();
        this.mFilePath = disposeDataHandle.getSource();
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        mListener.onPorgress((int) msg.obj);
                        break;
                }
            }
        };

    }

    @Override
    public void onFailure(Call call, final IOException e) {

        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFaliure(new OKHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final File file = handleResponse(response);
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFaliure(new OKHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });

    }

    private File handleResponse(Response response) {
        if (response == null)
            return null;
        InputStream inputStream=null;
        FileOutputStream fileOutputStream=null;
        File file=null;
        byte[] buffer=new byte[1024*2];
        int length=0;
        int currentLength=0;
        long sumLength;
        try {
            checkLocalFile(mFilePath);
            file=new File(mFilePath);
            fileOutputStream=new FileOutputStream(file);
            inputStream=response.body().byteStream();
            sumLength=response.body().contentLength();
            while ((length=inputStream.read(buffer))!=-1){
                currentLength+=length;
                mProgress=(int)((currentLength/sumLength)*100);
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE,mProgress).sendToTarget();
            }
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOutputStream!=null)
                    fileOutputStream.close();
                if(inputStream!=null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;
    }

    /**
     * 检查文件是否存在
     * @param filePath
     */
    private void checkLocalFile(String filePath) {
        File file=new File(filePath);
        String dirPath=filePath.substring(0,filePath.lastIndexOf("/")+1);
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
