package com.dhsr.wx.cp.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.annotation.CurrentUser;
import com.dhsr.wx.cp.annotation.Log;
import com.dhsr.wx.cp.annotation.Pass;
import com.dhsr.wx.cp.annotation.ValidationParam;
import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.config.*;
import com.dhsr.wx.cp.entity.ChargeRecord;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.entity.OrderRecord;
import com.dhsr.wx.cp.service.IOperatorService;
import com.dhsr.wx.cp.service.IParkInfoService;
import com.dhsr.wx.cp.utils.CommonUtil;
import com.dhsr.wx.cp.utils.PubFun;
import com.dhsr.wx.cp.utils.UnsafeOkHttpClient;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.cp.api.WxCpOAuth2Service;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpOAuth2ServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: weixin-java-cp-demo
 * @Package: com.github.binarywang.demo.wx.cp.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-07-23 11:16
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-07-23 11:16
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
@Api("停车场接口")
@Controller
@RequestMapping("/ParkInfo")
public class ParkController {

    @Resource
    private IOperatorService operatorService;
    @Resource
    private IParkInfoService parkInfoService;
    @Resource
    private WxPayService wxPayService;

    @ApiOperation(value = "微信认证", notes = "根据code获取人员信息")
    @PostMapping(value = "/signIn")
    @ApiParam(value = "code", required = true)
    @Log(action = "SignIn", modelName = "signIn", description = "根据code获取人员信息")
    @Pass
    @ResponseBody
    public ResponseModel<Map<String, Object>> getUserInfo(@RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModel(operatorService.checkUserIdAndPasswd(requestJson));
    }

    @ApiOperation(value = "OA接口页面认证", notes = "根据OA接口页面认证")
    @PostMapping(value = "/signInOA")
    @ApiParam(value = "code", required = true)
    @Log(action = "signInOA", modelName = "signInOA", description = "根据OA接口页面认证")
    @Pass
    @ResponseBody
    public ResponseModel<Map<String, Object>> getUserInfoOA(@RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModel(operatorService.checkUserIdAndPasswdOA(requestJson));
    }

    @ApiOperation(value = "进出场推送", notes = "进出场推送信息")
    @PostMapping(value = "/sendInOrOutRec")
    @Log(action = "sendInOrOutRec", modelName = "sendInOrOutRec", description = "进出场推送")
    @Pass
    @ResponseBody
    public ResponseModel<Map<String, Object>> sendInOrOutRec( @ValidationParam("inOrOut,userByD,recTime,carCode") @RequestBody JSONObject requestJson) throws Exception {
        //进出标识1进2出  用户标识   时间
        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModel(parkInfoService.sendMessage(null, requestJson));
    }


    @ApiOperation(value = "车位和访客数量", notes = "获取车位和访客车辆信息")
    @PostMapping(value = "/getParkInfo")
    @Log(action = "getParkInfo", modelName = "getParkInfo", description = "获取车位和访客车辆信息")
    @ResponseBody
    public ResponseModel<Map<String, Object>> getParkInfo(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModel(parkInfoService.searchParkInfo(operator, requestJson));
    }

    @ApiOperation(value = "公司和个人预约信息", notes = "获取公司和个人预约记录信息")
    @PostMapping(value = "/getCompanyVistor")
    @Log(action = "getCompanyVistor", modelName = "getCompanyVistor", description = "获取公司和个人预约记录信息")
/*    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "数量", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "visType", value = "访客类型，1代表公司，2代表个人", required = true, dataType = "int", paramType = "query")
        @ApiImplicitParam(name = "visType", value = "访客类型，1代表公司，2代表个人", required = true, dataType = "int", paramType = "query")
    })*/
    @ResponseBody
    public ResponseModelListPage<OrderRecord> getCompanyVistor(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModelPageList(parkInfoService.searchCompanyVistor(operator, requestJson), parkInfoService.searchCompanyVistorCount(operator, requestJson));
    }

    @ApiOperation(value = "公司预约登记", notes = "公司预约登记信息")
    @PostMapping(value = "/insertCompanyVistor")
    @Log(action = "insertCompanyVistor", modelName = "insertCompanyVistor", description = "公司预约登记信息")
    @ResponseBody
    public ResponseModel<Map<String, Object>> insertCompanyVistor(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");
        parkInfoService.insertCompanyVistor(operator, requestJson);
        return ResponseHelper.buildResponseModelInsert(null);
    }

    @ApiOperation(value = "获取历史车牌信息", notes = "获取历史车牌信息")
    @PostMapping(value = "/getHisCarInfo")
    @Log(action = "getHisCarInfo", modelName = "getHisCarInfo", description = "获取历史车牌信息")
    @ResponseBody
    public ResponseModel<Map<String, Object>> getHisCarInfo(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======历史车牌");

        return ResponseHelper.buildResponseModelList(parkInfoService.searchHisCarInfo(operator, requestJson));
    }

    @ApiOperation(value = "个人预约登记", notes = "个人预约登记信息")
    @PostMapping(value = "/insertStafferVistor")
    @Log(action = "insertStafferVistor", modelName = "insertStafferVistor", description = "个人预约登记信息")
    @ResponseBody
    public ResponseModel<Map<String, Object>> insertStafferVistor(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {
        System.out.println(requestJson + "=======");

        parkInfoService.insertStafferVistor(operator, requestJson);
        return ResponseHelper.buildResponseModelInsert(null);
    }

    @ApiOperation(value = "预约详情", notes = "获取预约详情信息,请求参数为VisRecordInnerId必填")
    @PostMapping(value = "/getVistorDetails")
    @Log(action = "getVistorDetails", modelName = "getVistorDetails", description = "获取预约详情信息")
    @ResponseBody
    public ResponseModel<ChargeRecord> getVistorDetails(@CurrentUser Operator operator, @ValidationParam("VisRecordInnerId") @RequestBody JSONObject requestJson) throws Exception {

        System.out.println(requestJson + "=======");
        return ResponseHelper.buildResponseModel(parkInfoService.searchVistorDetails(operator, requestJson));
    }

    @ApiOperation(value = "取消预约", notes = "查询预约详情信息,取消预约接口")
    @PostMapping(value = "/updateVistorDetailsCancel")
    @Log(action = "updateVistorDetailsCancel", modelName = "updateVistorDetailsCancel", description = "查询预约详情信息,取消预约接口记录id.RecordInnerId")
    @ResponseBody
    public ResponseModel<String> updateVistorDetailsCancel(@CurrentUser Operator operator, @ValidationParam("RecordInnerId") @RequestBody JSONObject requestJson) throws Exception {

        System.out.println(requestJson + "=======");
        Map<String, Object> map = parkInfoService.updateVistorDetailsCancel(operator, requestJson);
        return ResponseHelper.buildResponseModel(map.get("msg").toString());
    }

    @ApiOperation(value = "提前结束", notes = "提前结束接口")
    @PostMapping(value = "/updateVistorDetailsOver")
    @Log(action = "updateVistorDetailsOver", modelName = "updateVistorDetailsOver", description = "查询预约详情信息,提前结束接口")
    @ResponseBody
    public ResponseModel<Map<String, Object>> updateVistorDetailsOver(@CurrentUser Operator operator, @ValidationParam("RecordInnerId") @RequestBody JSONObject requestJson) throws Exception {

        System.out.println(requestJson + "=======");
        parkInfoService.updateVistorDetailsOver(operator, requestJson);
        return ResponseHelper.buildResponseModel(null);
    }

    @ApiOperation(value = "查询部门账户余额", notes = "查询部门账户余额信息")
    @PostMapping(value = "/searchDepartMoney")
    @Log(action = "searchDepartMoney", modelName = "searchDepartMoney", description = "查询部门账户余额信息")
    @ResponseBody
    public ResponseModel<Map<String, Object>> searchDepartMoney(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {

        return ResponseHelper.buildResponseModel(parkInfoService.searchDepartmentMoney(operator, requestJson));
    }

    @ApiOperation(value = "车辆类型接口", notes = "获取车辆类型接口")
    @PostMapping(value = "/searchCarType")
    @Log(action = "searchCarType", modelName = "searchCarType", description = "获取车辆类型接口")
    @ResponseBody
    public ResponseModel<List<Map<String, Object>>> searchCarType(@CurrentUser Operator operator) throws Exception {

        return ResponseHelper.buildResponseModel(parkInfoService.searchCarType(operator));
    }

    @ApiOperation(value = "获取Openid以及消费费用", notes = "获取Openid")
    @PostMapping(value = "/getVistorOpenId")
    @Log(action = "getVistorOpenId", modelName = "getVistorOpenId", description = "获取Openid")
    @ResponseBody
    public ResponseModel<Map<String, Object>> getVistorOpenId(@CurrentUser Operator operator, @RequestBody JSONObject requestJson, HttpServletRequest request) throws Exception {
        requestJson.put("spbillCreateIp", request.getRemoteAddr());

        return ResponseHelper.buildResponseModel(parkInfoService.searchCarConsume(operator, requestJson));
    }

    @ApiOperation(value = "获取消费费用", notes = "获取消费费用")
    @PostMapping(value = "/getMoneyFee")
    @Log(action = "getMoneyFee", modelName = "getMoneyFee", description = "获取消费费用")
    @ResponseBody
    public ResponseModel<Map<String, Object>> getMoneyFee(@CurrentUser Operator operator, @RequestBody JSONObject requestJson) throws Exception {


        return ResponseHelper.buildResponseModel(parkInfoService.searchCarFee(operator, requestJson));
    }

    @ApiOperation(value = "支付", notes = "支付")
    @PostMapping(value = "/getPayInfo")
    @ResponseBody
    public ResponseModel<Map<String, String>> getPayInfo(@CurrentUser Operator operator, @RequestBody JSONObject requestJson, HttpServletRequest request) throws Exception {

        // System.out.println(requestJson+"=======");
        Map<String, String> outJson = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomStringUtils.randomAlphanumeric(32);
        map.put("appid", "wx8a1e3929ab4c44a7");
        map.put("mch_id", "1383223202");
     /*   map.put("appid", "wwb6a623d6926a4f4c");
        map.put("mch_id", "1335548101");*/
        map.put("nonce_str", nonceStr);
        map.put("body", "微信充值");
        map.put("out_trade_no", PubFun.orderNum());
        map.put("total_fee", String.valueOf(10));
        map.put("spbill_create_ip", request.getRemoteAddr());
        map.put("notify_url", "");
        map.put("trade_type", "JSAPI");
        String xml = PubFun.ArrayToXml(map);


        return ResponseHelper.buildResponseModel(outJson);
    }

    @ApiOperation(value = "查询车位剩余列表", notes = "查询车位剩余列表")
    @PostMapping(value = "/searchParkNumByDate")
    @Log(action = "searchParkNumByDate", modelName = "searchParkNumByDate", description = "查询车位剩余列表")
    @ResponseBody
    public ResponseModel<List<Map<String, Object>>> searchParkNumByDate(@CurrentUser Operator operator,@RequestBody JSONObject requestJson) throws Exception {

        return ResponseHelper.buildResponseModel(parkInfoService.searchParkNumByDate(requestJson));
    }

}
