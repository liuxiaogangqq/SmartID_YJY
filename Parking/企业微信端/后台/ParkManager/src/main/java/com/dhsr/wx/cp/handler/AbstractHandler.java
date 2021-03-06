package com.dhsr.wx.cp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.cp.message.WxCpMessageHandler;

/**
 * @author liuxiaogang(https : / / github.com / binarywang)
 */
public abstract class AbstractHandler implements WxCpMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
