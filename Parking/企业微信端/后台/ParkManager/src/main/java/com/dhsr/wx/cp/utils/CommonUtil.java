package com.dhsr.wx.cp.utils;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.config.WxCpConfiguration;
import lombok.extern.log4j.Log4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpMessageSendResult;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Map;

/**
 * @ProjectName: weixin-java-cp-demo
 * @Package: com.dhsr.wx.cp.utils
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-08-20 11:15
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-08-20 11:15
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
@Log4j
public final class CommonUtil {


    public static String sendWxMsg(String UserId, String Msg) {

       // WxCpService wxCpService = WxCpConfiguration.getCpService(1000005);
        WxCpService wxCpService = WxCpConfiguration.getCpService(Constant.WX_CODE);


        WxCpMessage message = WxCpMessage.TEXT().agentId(Constant.WX_CODE).toUser(UserId)
            .toParty("")
            .toTag("")
            .content(Msg)
            .build();
        WxCpMessageSendResult wxCpMessageSendResult = null;
        try {
            wxCpMessageSendResult = wxCpService.messageSend(message);

        } catch (WxErrorException e) {
          //  e.printStackTrace();
            log.trace(CommonUtil.class, e);
            return "";
        }

        return wxCpMessageSendResult.toString();
       // return "";
    }

    public static String getOpenId(String UserId) throws WxErrorException {

        WxCpService wxCpService = WxCpConfiguration.getCpService(Constant.WX_CODE);


        Map<String, String> openidMap = wxCpService.getUserService().userId2Openid(UserId, null);
        System.out.println(wxCpService.getJsapiTicket() + "--------JsapiTicket");
        return openidMap.get("openid");
    }

    /**
     * 取消注解
     * @param paramV
     * @return
     * @throws IOException
     */
    public static JSONObject okHttpGet(String paramV) throws IOException {
      //  JSONObject jsonObjectBack = new JSONObject();
        Request request = new Request.Builder()
            .addHeader("Content-Type", Constant.POST_CONTENT_TYPE_JSON)
            .url(Constant.CREATE_MONEY_API + "?" + paramV)
            //  .post(body)
            .build();

        JSONObject jsonObjectBack = (JSONObject) JSONObject.parseObject(UnsafeOkHttpClient.getUnsafeOkHttpClient().newCall(request)
            .execute().body().string());

        System.out.println(jsonObjectBack.toJSONString());

        return jsonObjectBack;

    }

    public static JSONObject okHttpPost(JSONObject jsonObject) throws IOException {

        JSONObject jsonObjectBack = new JSONObject();
        String jobject = jsonObject != null ? jsonObject.toString() : "";
        RequestBody body = FormBody.create(MediaType.parse(Constant.POST_CONTENT_TYPE_JSON), jobject);
        //     .build();
        Request request = new Request.Builder()
            .addHeader("Content-Type", Constant.POST_CONTENT_TYPE_JSON)
            .url(Constant.PARK_HOST)
            .post(body)
            .build();

         jsonObjectBack = (JSONObject) JSONObject.parseObject(UnsafeOkHttpClient.getUnsafeOkHttpClient().newCall(request)
            .execute().body().string());
        return jsonObjectBack;

    }
}
