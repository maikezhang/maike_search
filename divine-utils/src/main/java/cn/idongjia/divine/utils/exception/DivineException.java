package cn.idongjia.divine.utils.exception;

import cn.idongjia.exception.ApiException;
import cn.idongjia.exception.ResultCode;

import javax.ws.rs.core.Response;

public class DivineException extends ApiException {

    public DivineException(Response response,String log) {
        super(response,log);
    }

    public DivineException(String log,String msg,Integer status) {
        super(log,msg,status);
    }

    public DivineException(String msg,Integer status) {
        super(msg,status);
    }

    public DivineException(String msg) {
        super(-12138,msg);
    }

    public DivineException(Integer code,String msg) {
        super(code,msg);
    }


    public DivineException(Integer code,String msg,String message) {
        super(code,msg,message);
    }

    public DivineException(ResultCode resultCode,Object... variables) {
        super(resultCode,variables);
    }

    public DivineException(String log,String msg,Integer status,Integer code) {
        super(log,msg,status,code);
    }

    public static DivineException failure(String msg) {
        return new DivineException(-12138,msg);
    }

    public static DivineException failure(int code,String msg) {
        return new DivineException(code,msg);
    }
}
