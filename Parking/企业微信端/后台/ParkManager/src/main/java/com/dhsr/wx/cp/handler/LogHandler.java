package com.dhsr.wx.cp.handler;

import java.util.Map;

import com.dhsr.wx.cp.utils.JsonUtils;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * @author liuxiaogang(https : / / github.com / binarywang)
 */
@Component
public class LogHandler extends AbstractHandler {
    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {
        //this.logger.info("\n接收到请求消息，内容：{}", JsonUtils.toJson(wxMessage));
        return null;
    }

}
