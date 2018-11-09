package cn.com.megait.commonlib.okhttp.exception;

/**
 * @author TimeW
 * @function 自定义异常类,返回ecode,emsg到业务层
 * @date 2018/11/9
 */
public class OKHttpException extends Exception {

    private int ecode;
    private Object emsg;

    public OKHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
