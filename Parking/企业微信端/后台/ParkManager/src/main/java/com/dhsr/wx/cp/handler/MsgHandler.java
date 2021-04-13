package com.dhsr.wx.cp.handler;

import java.util.Map;

import com.dhsr.wx.cp.builder.TextBuilder;
import com.dhsr.wx.cp.utils.JsonUtils;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * @author liuxiaogang(https : / / github.com / binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //TODO 组装回复消息
        String content = JsonUtils.toJson(wxMessage.getContent());

        return new TextBuilder().build(content, wxMessage, cpService);

    }

}
