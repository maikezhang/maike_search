package cn.idongjia.divine.lib.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 响应
 *
 * @author lc
 * @create at 2018/7/6.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response  implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isSuccess;

    private Integer errCode;

    private String errMessage;

    public boolean isSuccess() {
        return isSuccess;
    }


    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public Integer getErrCode() {
        return errCode;
    }


    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }


    public String getErrMessage() {
        return errMessage;
    }


    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }


    @Override
    public String toString() {
        return "Response [isSuccess=" + isSuccess + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    public static Response buildFailure(int errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static Response buildSuccess(){
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }
}
