package cn.com.megait.imoocbusiness.view.fragment;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import cn.com.megait.imoocbusiness.constant.Constant;

/**
 * @author TimeW
 * @function 所有Fragment基类,权限的申请,判断
 *
 */
public class BaseFragment extends Fragment {

    /**
     * 申请指定权限
     * @param code
     * @param permissions
     */
    public void requestPermission(int code,String... permissions){
        if(Build.VERSION.SDK_INT>=23){
            requestPermissions(permissions,code);
        }
    }

    /**
     * 判断是否有指定权限
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions){
        for(String permission:permissions){
            //判断权限是否被授权
            if(ContextCompat.checkSelfPermission(getActivity(),permission)!= PackageManager.PERMISSION_GRANTED){
                return  false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case Constant.WRITE_READ_EXTERNAL_CODE:
                break;
                case Constant.HARDWARE_CAMERA_CODE:
                    break;
        }
    }

    public void doOpenCamera(){}

    public void doWriteSDCard(){}
}
