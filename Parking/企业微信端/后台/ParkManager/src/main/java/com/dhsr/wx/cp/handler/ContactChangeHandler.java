package com.dhsr.wx.cp.handler;

import java.util.Map;

import com.dhsr.wx.cp.builder.TextBuilder;
import com.dhsr.wx.cp.utils.JsonUtils;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * 通讯录变更事件处理器.
 *
 * @author liuxiaogang(https : / / github.com / binarywang)
 */
@Component
public class ContactChangeHandler extends AbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {
        String content = "收到通讯录变更事件，内容：" + JsonUtils.toJson(wxMessage);
        this.logger.info(content);

        return new TextBuilder().build(content, wxMessage, cpService);
    }

}
