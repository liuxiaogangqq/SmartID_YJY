package com.dhsr.wx.cp.exception;

/**
 * 身份认证异常
 *
 * @author liuxiaogang
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
