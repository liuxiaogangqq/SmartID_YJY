package com.dhsr.wx.cp.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: sirelock
 * @Package: com.dhsr.sirelock.config
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-03-21 15:30
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-03-21 15:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class ResponseModelListPage<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    private int status;
    private List<T> result;
    private String message;
    private String code;
    private int total;

    public ResponseModelListPage() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getResponse();
        response.setCharacterEncoding("UTF-8");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResponseModelListPage{" +
            "status=" + status +
            ", result=" + result +
            ", message='" + message + '\'' +
            ", code='" + code + '\'' +
            ", total=" + total +
            '}';
    }
}
