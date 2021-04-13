package com.dhsr.wx.cp.utils;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.Constant;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OKHttpUtils {
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
        .readTimeout(50000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .build();

    public static void main(String[] args) throws IOException {
        String paramV = "inTime=2019-09-07 10:00:00&outTime=2019-09-07 15:00:00&carType=4";
        //   JSONObject jsonObject =   CommonUtil.okHttpGet(paramV);
        // JSONObject jobject = new JSONObject();
        // jobject.put("ChargeRecordInnerId",4);
        //  RequestBody body =  FormBody.create(MediaType.parse(Constant.POST_CONTENT_TYPE_JSON), jobject.toJSONString());
        //     .build();
        Request request = new Request.Builder()
            .addHeader("Content-Type", Constant.POST_CONTENT_TYPE_JSON)
            .url(Constant.CREATE_MONEY_API + "?" + paramV)
            //  .post(body)
            .build();

        JSONObject jsonObjectBack = (JSONObject) JSONObject.parseObject(UnsafeOkHttpClient.getUnsafeOkHttpClient().newCall(request)
            .execute().body().string());

        System.out.println(jsonObjectBack.toJSONString());

    }

}
