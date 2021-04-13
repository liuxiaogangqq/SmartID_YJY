package com.dhsr.wx.cp.handler;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * <pre>
 *
 * Created by liuxiaogang on 2018/8/27.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">liuxiaogang</a>
 */
@Slf4j
public class EnterAgentHandler extends AbstractHandler {
    private static final int TEST_AGENT = 1000028;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {
        // do something
        return null;
    }
}
