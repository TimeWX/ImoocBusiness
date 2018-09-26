package cn.com.megait.imoocbusiness.view.fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import cn.com.megait.imoocbusiness.constant.Constant;


/**
 * @author TimeW
 * @function 所有Fragment基类, 权限的申请, 判断
 * @date 2018/9/25
 */
public class BaseFragment extends Fragment {

    protected Activity mContext;

    /**
     * 申请指定权限
     *
     * @param code
     * @param permissions
     */
    public void requestPermission(int code, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
        }
    }

    /**
     * 判断是否有指定权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            //判断权限是否被授权
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限授权与否的回调结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //请求读写内存
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    doWriteSDCard();
                }
                break;
            //请求打开照相机
            case Constant.HARDWARE_CAMERA_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    doOpenCamera();
                }
                break;
        }
    }

    public void doOpenCamera() {
    }

    public void doWriteSDCard() {
    }
}
