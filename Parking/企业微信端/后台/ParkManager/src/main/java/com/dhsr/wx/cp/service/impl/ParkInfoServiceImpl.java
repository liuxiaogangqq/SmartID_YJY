package com.dhsr.wx.cp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.BusinessException;
import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.base.PublicResultConstant;
import com.dhsr.wx.cp.config.WxCpConfiguration;
import com.dhsr.wx.cp.entity.*;
import com.dhsr.wx.cp.mapper.LogMapper;
import com.dhsr.wx.cp.mapper.OperatorMapper;
import com.dhsr.wx.cp.mapper.ParkInfoMapper;
import com.dhsr.wx.cp.service.ILogService;
import com.dhsr.wx.cp.service.IParkInfoService;
import com.dhsr.wx.cp.utils.CommonUtil;
import com.dhsr.wx.cp.utils.DateTimeUtil;
import com.dhsr.wx.cp.utils.GenerationSequenceUtil;
import com.dhsr.wx.cp.utils.PubFun;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
@Service
@Slf4j
@Transactional
public class ParkInfoServiceImpl implements IParkInfoService {
    @Resource
    private ParkInfoMapper parkInfoMapper;
    @Resource
    private OperatorMapper operatorMapper;


    @Override
    public Map<String, Object> searchParkInfo(Operator operator, JSONObject jsonObject) {

        Map<String, Object> mso = new HashMap<>();
        jsonObject.put("UserInnerId", operator.getUserInnerId());
        Map<String, Object> parkNum = parkInfoMapper.searchParkNumByDate(jsonObject); //停车位数量
        Integer visNum = parkInfoMapper.searchVisitorNum(jsonObject);//访客数量

        String[]  parkResidue =   parkNum.get("ParkResidue").toString().split(",");

        mso.put("staffCarNum", parkResidue[1]);
        mso.put("parkCarNum", parkResidue[0]);
        mso.put("visNum", visNum);

        return mso;
    }

    /**
     * 获取公司和个人预约记录信息
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    @Override
    public List<OrderRecord> searchCompanyVistor(Operator operator, JSONObject jsonObject) {
        int page = jsonObject.getInteger("page");
        int size = jsonObject.getInteger("size");
        PageHelper.startPage(page, size);
        jsonObject.put("UserInnerId", operator.getUserInnerId());
        return parkInfoMapper.searchOrderRecord(jsonObject);
    }

    /**
     * 获取公司和个人预约记录信息总数
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    @Override
    public Integer searchCompanyVistorCount(Operator operator, JSONObject jsonObject) {
        // int page = jsonObject.getInteger("page");
        //  int size = jsonObject.getInteger("size");
        // PageHelper.startPage(page, size);
        jsonObject.put("UserInnerId", operator.getUserInnerId());
        return parkInfoMapper.searchOrderRecordCount(jsonObject);
    }

    /**
     * 公司预约登记
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    @Override
    public Integer insertCompanyVistor(Operator operator, JSONObject jsonObject) throws Exception {

        jsonObject.put("UserInnerId", operator.getUserInnerId());

        String startTime = jsonObject.getString("VisitorBeginTime");
        String endTime = jsonObject.getString("VisitorEndTime");

        try {
            Date dStart = DateTimeUtil.parseToDate(startTime, DateTimeUtil.FMT_yyyyMMddHHmmss);

            if (dStart.getTime() < new Date().getTime()) {
                throw new BusinessException(PublicResultConstant.DATE_VIS_ERROR);
            }

        } catch (ParseException e) {
            log.error("日期异常==", e);
            throw new BusinessException(PublicResultConstant.DATE_CON_ERROR);
        }

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("VisitorCarCode",jsonObject.getString("VisitorCarCode"));
        jsonObj.put("VisitorState","1,4,5,7");
        jsonObj.put("VisitorBeginTime",jsonObject.getString("VisitorBeginTime"));
        jsonObj.put("VisitorEndTime",jsonObject.getString("VisitorEndTime"));

        int orderSize =  parkInfoMapper.searchOrderTime(jsonObj);

        if(orderSize>0){
            throw new BusinessException(PublicResultConstant.DATE_REPEAT_ERROR);
        }

       //判断车在场内预约的情况
        Map<String,Object> msoRecord =  parkInfoMapper.selectCarRecord(jsonObj);

        if(msoRecord!=null){
            Integer InOrOut = (Integer) msoRecord.get("InOrOut");
            if(InOrOut!=null&&InOrOut==0){

               // try {
                  String orderTime =   DateTimeUtil.formatDatetoString(jsonObject.getString("VisitorBeginTime"));
                  String theTime =   DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_yyyyMMdd);

                  if(orderTime.equals(theTime)){

                      throw new BusinessException(PublicResultConstant.DATE_THE_ERROR);

                  }
               /* } catch (Exception e) {
                    log.error("错误消息：{}",e.getMessage(),e);
                    throw new BusinessException(PublicResultConstant.DATE_PRE_ERROR);
                }*/
            }

        }


        jsonObject.put("ParkType","1");
        searchParkNumVali(jsonObject);


        Account depLeve = searchDep(operator);
        if(depLeve!=null) {
            Integer moneyYE = depLeve.getMoney();
            if (operator.getUState() != null && operator.getUState() == 3) {

                ParkConfig parkConfig = parkInfoMapper.searchConfigInfo();

                Integer minMoney = parkConfig.getMinMoney();

                if (minMoney >= moneyYE) {
                    throw new BusinessException(PublicResultConstant.MONEY_NO);
                }


            }
        }

        //查询访客信息是否之前注册过
        List<VisitorUserInfo> visitorUserInfoList = parkInfoMapper.searchVisitorUserInfo(jsonObject);

        if (visitorUserInfoList.size() <= 0) {

            VisitorUserInfo visitorUserInfo = new VisitorUserInfo();
            visitorUserInfo.setVisitorCar(jsonObject.getString("VisitorCarCode"));
            visitorUserInfo.setVisitorName(jsonObject.getString("VisitorName"));
            visitorUserInfo.setVisitorCompany(jsonObject.getString("VisitorCompany"));
            visitorUserInfo.setVisitorState(0);
            visitorUserInfo.setVisitorLock(0);
            parkInfoMapper.insertVisitorUserInfo(visitorUserInfo);

            jsonObject.put("VisitorInfoInnerId", visitorUserInfo.getVisitorInfoInnerId());
        } else {
            jsonObject.put("VisitorInfoInnerId", visitorUserInfoList.get(0).getVisitorInfoInnerId());
        }

        jsonObject.put("OrderType", 1);
        jsonObject.put("CarTypeInnerId", 3);
        jsonObject.put("VisitorState", 1);

        OrderRecord orderRecord = jsonObject.toJavaObject(OrderRecord.class);

        int insertOR = parkInfoMapper.insertOrderRecord(orderRecord);

        if (insertOR > 0) {


            jsonObject.put("ParkResidue", "ParkResidue");
            parkInfoMapper.updateParkNumDel(jsonObject);

            //立方发送接口开始
            try {

                //立方停车场接口
                JSONObject jsonObject1 = new JSONObject();

                Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                Date visDateEnd = DateTimeUtil.parseToDate(orderRecord.getVisitorEndTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);

                if (jsonObject.getInteger("requestType") != 2) {



                    String  msgVs =  DateTimeUtil.formatDateTimetoString(visDateStart,DateTimeUtil.FMT_yyyyMMddHHmm);
                    Date visDateStartVa = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 2);
                    String  msgVsVali =  DateTimeUtil.formatDateTimetoString(visDateStartVa,DateTimeUtil.FMT_HHmm);

                    //发送推送消息
                    String msg = "预约成功，预约时间为"+startTime+"至"+endTime+"。\n" +
                        "请于"+msgVs+"至"+msgVsVali+"时间段内进入院内，"+startTime+"前可免费取消预约，超过"+msgVsVali+"将自动变更为预约逾期状态，车辆将不能入场并自动扣除2小时停车费用。\n" +
                        "预约时间内，车辆可以多次进出。车辆出场后可选择提前结束此预约。\n";

                    CommonUtil.sendWxMsg(operator.getStandbyD(), msg);
                }


            } catch (Exception e) {
                e.printStackTrace();
                log.error("停车场接口异常", e);
            }
            //立方发送接口结束

        }

        return null;
    }

    /**
     * 员工预约登记
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    @Override
    public Integer insertStafferVistor(Operator operator, JSONObject jsonObject) throws Exception {

        jsonObject.put("VisitorStateIn", "1,4,5,7");
        jsonObject.put("UserInnerId", operator.getUserInnerId());

        String startTime = jsonObject.getString("VisitorBeginTime");

        try {
            Date dStart = DateTimeUtil.parseToDate(startTime, DateTimeUtil.FMT_yyyyMMddHHmmss);

            if (dStart.getTime() < new Date().getTime()) {
                throw new BusinessException(PublicResultConstant.DATE_VIS_ERROR);
            }

        } catch (ParseException e) {
            log.error("insertStafferVistor日期异常==", e);
            throw new BusinessException(PublicResultConstant.DATE_CON_ERROR);
        }

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("VisitorCarCode",jsonObject.getString("VisitorCarCode"));
        jsonObj.put("VisitorState",jsonObject.getString("VisitorStateIn"));
        jsonObj.put("VisitorBeginTime",jsonObject.getString("VisitorBeginTime"));
        jsonObj.put("VisitorEndTime",jsonObject.getString("VisitorEndTime"));

        int orderSize =  parkInfoMapper.searchOrderTime(jsonObj);

        if(orderSize>0){
            throw new BusinessException(PublicResultConstant.DATE_REPEAT_ERROR);
        }
        //判断车在场内预约的情况
        Map<String,Object> msoRecord =  parkInfoMapper.selectCarRecord(jsonObj);

        if(msoRecord!=null){
            Integer InOrOut = (Integer) msoRecord.get("InOrOut");
            if(InOrOut!=null&&InOrOut==0){

               // try {
                    String orderTime =   DateTimeUtil.formatDatetoString(jsonObject.getString("VisitorBeginTime"));
                    String theTime =   DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_yyyyMMdd);

                    if(orderTime.equals(theTime)){

                        throw new BusinessException(PublicResultConstant.DATE_THE_ERROR);

                    }
               /* } catch (Exception e) {
                    log.error("错误消息：{}",e.getMessage(),e);
                    throw new BusinessException(PublicResultConstant.DATE_PRE_ERROR);
                }*/
            }

        }

        jsonObject.put("ParkType","2");
        searchParkNumVali(jsonObject);


        jsonObj.clear();
       // jsonObject.put("OrderType", 2);
        jsonObj.put("VisitorCarCode",jsonObject.getString("VisitorCarCode"));
        jsonObj.put("VisitorState","8");
        //获取记录innerid  // 首先查询当前预约的记录
        int orderRecordList = parkInfoMapper.searchOrderTime(jsonObj);

        if (orderRecordList > 0) {
            throw new BusinessException(PublicResultConstant.TIME_MONEY);
        }

        jsonObject.put("UserInnerId", operator.getUserInnerId());
        jsonObject.put("OrderType", 2);
        jsonObject.put("VisitorState", 1);
        jsonObject.put("CarTypeInnerId", 1);

        OrderRecord orderRecord = jsonObject.toJavaObject(OrderRecord.class);
        orderRecord.setVisitorName(operator.getUserName());
        orderRecord.setVisitorCompany(operator.getCompanyName());
        //  orderRecord.setUserName("");
        //  orderRecord.setDepartMentName("");
        int insertOR = parkInfoMapper.insertOrderRecord(orderRecord);
        if (insertOR > 0) {

            jsonObject.put("ParkResidue", "ParkResidue");
          int i =   parkInfoMapper.updateParkNumDel(jsonObject);
            System.out.println(i+"==="+jsonObject.toJSONString());
            //立方发送接口开始
            try {
                Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                Date visDateEnd = DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 30);

                if (jsonObject.getInteger("requestType") != 2) {



                    String  msgVs =  DateTimeUtil.formatDateTimetoString(visDateStart,DateTimeUtil.FMT_yyyyMMddHHmm);

                    String  msgVsVali =  DateTimeUtil.formatDateTimetoString(visDateEnd,DateTimeUtil.FMT_HHmm);

                    //发送推送消息
                    // String msg = "预约成功，请于" + jsonObject.getString("VisitorBeginTime") + "前到达！";
                    String msg = "预约成功，预约时间为"+startTime+"至"+orderRecord.getVisitorEndTime()+"。\n" +
                        "请于"+msgVs+"至"+msgVsVali+"时间段内进入院内，"+startTime+"前可免费取消预约，超过"+msgVsVali+"将自动变更为预约逾期状态，车辆将不能入场并自动扣除30分钟停车费用。\n" +
                        "车辆出场后自动结算此次预约。\n";

                    CommonUtil.sendWxMsg(operator.getStandbyD(), msg);
                }



            } catch (ParseException e) {
                e.printStackTrace();
                log.error("预约登记异常", e);
            }
            //立方发送接口结束
        }

        return null;
    }

    /**
     * 获取预约记录详情信息
     *
     * @param operator
     * @param jsonObject
     * @return
     */
    @Override
    public ChargeRecord searchVistorDetails(Operator operator, JSONObject jsonObject) {

        ChargeRecord chargeRecord = parkInfoMapper.searchRecordDetails(jsonObject);
        return chargeRecord;
    }

    /**
     * 取消预约接口
     *
     * @param operator
     * @param jsonObject
     */
    @Override
    public Map<String, Object> updateVistorDetailsCancel(Operator operator, JSONObject jsonObject) throws BusinessException {

        Map<String, Object> mso = new HashMap<>();


        //获取记录innerid  // 首先查询当前预约的记录
        List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(jsonObject);


        try {
            Date nowDate = null;

            nowDate = DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);


            // 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
            int i = DateTimeUtil.compareTwoDate(nowDate, new Date());

            if (i == 1) {
                //   throw new BusinessException(PublicResultConstant.TIME_VALIDITY);

                if (orderRecordList.get(0).getOrderType() == 1) {
                    throw new BusinessException("当前时间已经超过预约时间，系统2个小时后自动结算，请及时查看！");
                } else if (orderRecordList.get(0).getOrderType() == 2) {
                    throw new BusinessException("当前时间已经超过预约时间，系统30分钟后自动结算，请及时查看！");
                }


            } else {


                if (orderRecordList.get(0).getVisitorState() == 3) {
                    throw new BusinessException(PublicResultConstant.CANCLE_VIS_ERROR);
                }


                mso.put("cancelState", 1);//可以取消预约
                jsonObject.put("VisitorState", 3);


                //查询当前预约状态，判断车辆预约时间

                //当前时间在车辆预约开始之前可以取消预约


                parkInfoMapper.updateRecordStates(jsonObject);

                Map<String, Object> msoI = new HashMap<>();

                int visType = orderRecordList.get(0).getOrderType();


                msoI.put("ParkType",visType);
                msoI.put("ParkResidue", "ParkResidue");
                msoI.put("VisitorBeginTime", orderRecordList.get(0).getVisitorBeginTime());
                msoI.put("VisitorEndTime", orderRecordList.get(0).getVisitorEndTime());
                parkInfoMapper.updateParkNumAdd(msoI);


                String msg = "取消预约成功,期待您的下次预约。如有疑问请联系管理员！";





                if (jsonObject.getInteger("requestType") != 2) {
                    //发送推送消息
                    CommonUtil.sendWxMsg(operator.getStandbyD(), msg);
                }
                /**
                 * 立方停车场操作
                 */
                JSONObject jsonObject1 = new JSONObject();
                //   if(orderRecordList.get(0).getCarTypeInnerId()==3){

                Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                Date visDateEnd = DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorEndTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);

                jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));

           /*  }else if(orderRecordList.get(0).getCarTypeInnerId()==1){
                 Date  visDateStart= DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(),DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                 Date  visDateEnd= DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorEndTime(),DateTimeUtil.FMT_yyyyMMddHHmmss);

                 jsonObject1.put("beginTime",DateTimeUtil.formatDateTimetoString(visDateStart));
                 jsonObject1.put("endTime",DateTimeUtil.formatDateTimetoString(visDateEnd));

             }*/

                jsonObject1.put("carCode", orderRecordList.get(0).getVisitorCarCode());
                jsonObject1.put("type", 9);
                jsonObject1.put("act", 0);//1:添加/修改，0:删除

                JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                log.info("取消预约==" + jsonO.toJSONString());
                mso.put("msg", msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("取消预约接口异常==", e);
            throw new BusinessException(PublicResultConstant.TIME_VALIDITY);
        }


        return mso;
    }


    /**
     * 提前结束操作
     *
     * @param operator
     * @param jsonObject
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> updateVistorDetailsOver(Operator operator, JSONObject jsonObject) throws BusinessException {

        Map<String, Object> mso = new HashMap<>();


        //获取记录innerid  // 首先查询当前预约的记录
        List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(jsonObject);


        try {

            if (orderRecordList.size() > 0) {

                int res = orderRecordList.get(0).getVisitorState();

                if (res == 7) {
                    mso.put("recordInnerId", orderRecordList.get(0).getRecordInnerId());//记录id
                    //  mso.put("OutTime","time");//出场时间

                    //  parkInfoMapper.updateRecordMoney(mso);

                    List<ChargeRecord> chargeRecord = parkInfoMapper.searchChargeRecord(mso);

                    Date visDateE = new Date();


                    int CarSize = parkInfoMapper.searchGwCarInfo(chargeRecord.get(0).getCarNumber());

                    Date visDateQ = null;
                    String paramV = "";
                    if (CarSize > 0) {
                        visDateQ = DateTimeUtil.addHours(DateTimeUtil.parseToDate(chargeRecord.get(0).getInTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 2);
                        Date visDateW = DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(chargeRecord.get(0).getInTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 10);
                        paramV = "inTime=" + DateTimeUtil.formatDateTimetoString(visDateQ) + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDateE) + "&carType=3&overdueFlag=1";
                        if (visDateQ.getTime() > visDateE.getTime()) {
                            paramV = "inTime=" + chargeRecord.get(0).getInTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDateW) + "&carType=3&overdueFlag=1";
                        }
                    } else {
                        visDateQ = DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(chargeRecord.get(0).getInTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 15);
                        paramV = "inTime=" + chargeRecord.get(0).getInTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDateE) + "&carType=3&overdueFlag=1";
                        if (visDateQ.getTime() > visDateE.getTime()) {
                            paramV = "inTime=" + chargeRecord.get(0).getInTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDateE) + "&carType=3&overdueFlag=1";
                        }
                    }

                    log.info("提前结束paramV：" + paramV);

                    JSONObject jsonObjectMon = CommonUtil.okHttpGet(paramV);
                    log.info("提前结束请求后台金额:：" + jsonObjectMon.toJSONString());
                    boolean bState = jsonObjectMon.containsKey("state");

                    if (bState && jsonObjectMon.getInteger("state") == 200) {

                        Integer moneyF = jsonObjectMon.getInteger("money");


                        mso.put("recordInnerId", orderRecordList.get(0).getRecordInnerId());
                        mso.put("Money", moneyF * 100);


                        List<ChargeRecord> chargeRecordList = parkInfoMapper.searchChargeRecord(mso);

                        if (chargeRecordList.get(0).getOrderNo() == null || "".equals(chargeRecordList.get(0).getOrderNo())) {


                            Account depLeve = searchDep(operator);

                            jsonObjectMon.put("UserInnerId", operator.getUserInnerId());
                            jsonObjectMon.put("DepartmentInnerId", depLeve);
                            jsonObjectMon.put("AccountTypeInnerId", 1);

                            //更新金额
                            int i = updateUserFee(operator, depLeve,moneyF);


                            if (i > 0) {
                                //完成支付
                                mso.put("chargeState", 3);
                                //支付方式微信支付
                                mso.put("ChargeType", 3);
                                mso.put("OutTime", "当前时间");
                                mso.put("OrderNo", PubFun.orderNum());
                                //更新订单
                                parkInfoMapper.updateRecordMoney(mso);
                                Map<String, Object> msoI = new HashMap<>();

                                int visType = orderRecordList.get(0).getOrderType();

                                msoI.put("ParkType",visType);
                                msoI.put("ParkResidue", "ParkResidue");
                                msoI.put("VisitorBeginTime", DateTimeUtil.formatDateTimetoString(new Date()));
                                msoI.put("VisitorEndTime", orderRecordList.get(0).getVisitorEndTime());
                                parkInfoMapper.updateParkNumAdd(msoI);



                                msoI.clear();
                                String msg = "提前结束成功，此次预约总共消费[" + jsonObjectMon.getInteger("money") + "]元,如有疑问请联系管理员！";
                                log.info("提前结束消费信息===" + jsonObjectMon.toJSONString());
                                if (jsonObject.getInteger("requestType") != 2) {
                                    //发送推送消息
                                    CommonUtil.sendWxMsg(operator.getStandbyD(), msg);
                                }

                            }
                        }
                        mso.put("RecordInnerId", orderRecordList.get(0).getRecordInnerId());
                        mso.put("VisitorState", 6);
                        parkInfoMapper.updateRecordStates(mso);

                        //立方停车场接口
                        JSONObject jsonObject1 = new JSONObject();
                        //    if(orderRecordList.get(0).getCarTypeInnerId()==2){

                        Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                        Date visDateEnd = DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorEndTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);

                        jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                        jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));

                      /* }else if(orderRecordList.get(0).getCarTypeInnerId()==3||orderRecordList.get(0).getCarTypeInnerId()==4){
                           Date  visDateStart= DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(),DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                           Date  visDateEnd= DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(),DateTimeUtil.FMT_yyyyMMddHHmmss), 30);

                           jsonObject1.put("beginTime",DateTimeUtil.formatDateTimetoString(visDateStart));
                           jsonObject1.put("endTime",DateTimeUtil.formatDateTimetoString(visDateEnd));

                       }*/

                        jsonObject1.put("carCode", orderRecordList.get(0).getVisitorCarCode());
                        jsonObject1.put("type", 9);
                        jsonObject1.put("act", 0);//1:添加/修改，0:删除
                        JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                        log.info("提前结束===" + jsonO.toJSONString());
                    } else {
                        throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);
                    }


                } else {
                    throw new BusinessException(PublicResultConstant.STATE_ERROR);
                }


            }

            //   orderRecordList.get(0)

        } catch (Exception e) {
            e.printStackTrace();
            log.error("提前结束操作===", e);
            throw new BusinessException(PublicResultConstant.TIME_VALIDITY);
        }


        return null;
    }

    /**
     * 查询公司余额
     *
     * @param operator
     */
    @Override
    public Map<String, Object> searchDepartmentMoney(Operator operator, JSONObject requestJson) {

        Map<String, Object> mso = new HashMap<>();

        int moneyYE = 0;

        if (operator.getUState() != null && operator.getUState() == 3) {



            Account moneyAcc = searchDep(operator);
            if(moneyAcc!=null) {
                moneyYE = moneyAcc.getMoney();
                System.out.println(moneyAcc.toString() + "===========");

                // moneyYE = parkInfoMapper.searchDepartmentMoney(depLeve);
            }
        }

        mso.put("moneyYE", moneyYE);
        return mso;
    }

    /**
     * 查询历史车牌信息
     *
     * @param operator
     * @param requestJson
     */
    @Override
    public List<Map<String, Object>> searchHisCarInfo(Operator operator, JSONObject requestJson) {


       List<Map<String, Object>> recordList =  parkInfoMapper.searchOrderRecordHis(requestJson);


        return recordList;
    }

    /**
     * 查询公司余额
     *
     * @param operator
     */
    @Override
    public List<Map<String, Object>> searchCarType(Operator operator) {


        List<ParkCartype> parkCartypes = parkInfoMapper.searchCarType(null);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        for (ParkCartype parkCartype : parkCartypes) {

            Map<String, Object> row = new HashMap<>();
            row.put("name", parkCartype.getCarTypeName());
            row.put("id", parkCartype.getCarTypeInnerId());
            rows.add(row);

        }
        return rows;
    }

    /**
     * 查询车辆订单信息
     *
     * @param operator
     * @param requestJson
     */
    @Override
    public Map<String, Object> searchCarConsume(Operator operator, JSONObject requestJson) throws BusinessException {

      //  WxCpService wxCpService = WxCpConfiguration.getCpService(1000005);
        WxCpService wxCpService = WxCpConfiguration.getCpService(1000028);
        try {
            requestJson.put("openid", CommonUtil.getOpenId(operator.getStandbyD()));

            //    requestJson.put("OutTime","time");

            //获取记录innerid  // 首先查询当前预约的记录
            List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(requestJson);
            requestJson.put("VisRecordInnerId", requestJson.getString("RecordInnerId"));

            ChargeRecord chargeRecord = parkInfoMapper.searchRecordDetails(requestJson);
            if (orderRecordList.get(0).getVisitorState() == 8) {


                requestJson.put("totalFee", chargeRecord.getMoney());

                if (chargeRecord.getMoney() == 0) {
                    throw new BusinessException(PublicResultConstant.MONEY_URL_NO);
                } else if (chargeRecord.getMoney() < 0) {

                    throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);

                }
            } else {
                String paramV = "inTime=" + chargeRecord.getInTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(new Date()) + "&carType=" + orderRecordList.get(0).getCarTypeInnerId() + "&overdueFlag=1";

                if (orderRecordList.get(0).getVisitorState() == 8) {

                    Date visDateEnd = DateTimeUtil.addMinutes(new Date(), 30);

                    paramV = "inTime=" + orderRecordList.get(0).getVisitorBeginTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDateEnd) + "&carType=" + orderRecordList.get(0).getCarTypeInnerId() + "&overdueFlag=2";


                }

                JSONObject jsonObjectMon = CommonUtil.okHttpGet(paramV);

                boolean bState = jsonObjectMon.containsKey("state");
                int moneyFee = jsonObjectMon.getInteger("money") * 100;
                //  int moneyFee = 1;
                if (bState && jsonObjectMon.getInteger("state") == 200) {
                    requestJson.put("totalFee", moneyFee);

                } else {
                    throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);
                }

                if (moneyFee == 0) {
                    throw new BusinessException(PublicResultConstant.MONEY_URL_NO);
                } else if (moneyFee < 0) {

                    throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);

                }
            }


            WxJsapiSignature wxJsapiSignature = wxCpService.createJsapiSignature(requestJson.getString("pageUrl"));
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(wxJsapiSignature);
            requestJson.put("signInfo", jsonObject);
            requestJson.put("tradeType", "JSAPI");
            requestJson.put("notifyUrl", Constant.NOTIFY_URL);
            requestJson.put("body", "停车场支付");
            requestJson.put("outTradeNo", PubFun.orderNum());

            int updateRec = parkInfoMapper.updateRecordInfo(requestJson);

            if (updateRec <= 0) {
                throw new BusinessException(PublicResultConstant.MONEY_CON);

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付异常：",e);
            throw new BusinessException(PublicResultConstant.MONEY_ERROR);
        }

        return requestJson;
    }

    @Override
    public Map<String, Object> searchCarFee(Operator operator, JSONObject jsonObject) throws BusinessException {

        //    requestJson.put("OutTime","time");

        //获取记录innerid  // 首先查询当前预约的记录
        List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(jsonObject);

        jsonObject.put("VisRecordInnerId", jsonObject.getString("RecordInnerId"));

        ChargeRecord chargeRecord = parkInfoMapper.searchRecordDetails(jsonObject);

        String paramV = "inTime=" + chargeRecord.getInTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(new Date()) + "&carType=" + orderRecordList.get(0).getCarTypeInnerId() + "&overdueFlag=1";


        if (orderRecordList.get(0).getVisitorState() == 8) {


            jsonObject.put("totalFee", chargeRecord.getMoney());
            if (chargeRecord.getMoney() == 0) {
                throw new BusinessException(PublicResultConstant.MONEY_URL_NO);
            } else if (chargeRecord.getMoney() < 0) {

                throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);

            }
        } else {


            JSONObject jsonObjectMon = null;
            try {
                jsonObjectMon = CommonUtil.okHttpGet(paramV);
            } catch (IOException e) {
                throw new BusinessException(PublicResultConstant.NET_CON_ERROR);
            }

            boolean bState = jsonObjectMon.containsKey("state");
            int moneyFee = jsonObjectMon.getInteger("money") * 100;
            //int moneyFee = 100;
            if (bState && jsonObjectMon.getInteger("state") == 200) {
                jsonObject.put("totalFee", moneyFee);

            } else {
                throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);
            }
            if (moneyFee == 0) {
                throw new BusinessException(PublicResultConstant.MONEY_URL_NO);
            } else if (moneyFee < 0) {

                throw new BusinessException(PublicResultConstant.MONEY_URL_ERROR);

            }
        }
        return jsonObject;
    }

    /**
     * 微信回调支付
     *
     * @param notifyResult
     */
    @Override
    public Map<String, Object> updateWXRecordInfo(WxPayOrderNotifyResult notifyResult) throws BusinessException {

        Map<String, Object> mso = new HashMap<>();

        System.out.println(notifyResult.toString());
        mso.put("outTradeNo", notifyResult.getOutTradeNo());
        mso.put("chargeState", 3);
        mso.put("chargeType", 2);

        mso.put("totalFee", notifyResult.getTotalFee());
        //  mso.put("RecordInnerId",  notifyResult.getAttach());
        mso.put("transactionId", notifyResult.getTransactionId());
        log.info("支付信息查询===" + mso.toString());
        List<ChargeRecord> chargeRecordList = parkInfoMapper.searchChargeRecord(mso);
        log.info("支付信息查询=chargeRecordList==" + chargeRecordList.size());
        if (chargeRecordList.size() > 0) {
            int updateRec = parkInfoMapper.updateRecordInfoByNo(mso);

            if (updateRec > 0) {

                mso.put("RecordInnerId", chargeRecordList.get(0).getVisRecordInnerId());
                List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecordList(mso);
                mso.put("VisitorState", 5);
                if(orderRecordList.get(0).getVisitorState()==8){
                    mso.put("VisitorState", 6);
                }
                mso.put("RecordInnerId", chargeRecordList.get(0).getVisRecordInnerId());
                parkInfoMapper.updateRecordStates(mso);
                mso.clear();

               /* mso.put("RecordInnerId", chargeRecordList.get(0).getVisRecordInnerId());
                List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecordList(mso);*/
                log.info("支付信息查询=orderRecordList==" + orderRecordList.size());
                //立方发送接口开始
                try {

                    //  Date visDateEnd = DateTimeUtil.addMinutes(new Date(), 15);
                    //立方停车场接口
                    JSONObject jsonObject1 = new JSONObject();
                    Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecordList.get(0).getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                    jsonObject1.put("beginTime", chargeRecordList.get(0).getInTime());

                    jsonObject1.put("carCode", chargeRecordList.get(0).getCarNumber());

                    jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(new Date()));
                    jsonObject1.put("type", 9);
                    jsonObject1.put("act", 1);//1:添加/修改，0:删除


                    JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                    log.info("支付成功===" + jsonO.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("微信回调支付异常==", e);
                }
                //立方发送接口结束


            } else {

                log.info("更新支付订单信息失败");

                // throw new BusinessException(PublicResultConstant.MONEY_CON);
            }
        } else {
            log.info("支付信息查询为空");
            // throw new BusinessException(PublicResultConstant.MONEY_ORDER);

        }
        mso.clear();
        return null;
    }


    public int updateUserFee(Operator operator, Account account, int money) {


        money = money * 100;

        ConLog conLog = new ConLog();
        conLog.setDiscountMoney(money);
        conLog.setProportion(10000);
        conLog.setAllowanceMoney(0);
        conLog.setNumberMoney(0);
        conLog.setPersonMoney(money);
        conLog.setPersonAFMoney(account.getMoney() - money);
        conLog.setPersonBFMoney(account.getMoney());
        conLog.setMoney(money);
        conLog.setAllowanceBFMoney(0);
        conLog.setAllowanceAFMoney(0);
        conLog.setNumberBFMoney(0);
        conLog.setNumberAFMoney(0);
        conLog.setMarkInnerId(0);
        conLog.setMarkId("1");
        conLog.setMarkTypeInnerId(0);
        conLog.setUserInnerId(account.getUserInnerId());
        conLog.setCompanyInnerId(operator.getCompanyInnerId());
        conLog.setConTerminalInnerId(0);
        conLog.setTerminalTypeInnerId(0);
        conLog.setMerchantInnerId(1);
        conLog.setAppInnerId(1);
        conLog.setAreaInnerId(1);
        conLog.setConTypeInnerId(1);
        conLog.setConPattern(5);
        conLog.setConWay(5);
        conLog.setLimitTimes(0);
        conLog.setOffLine(1);
        conLog.setOperator(1);
        conLog.setRemark("车辆微信预约提前结束扣款");
        conLog.setRules("");
        //扣款人
        conLog.setStandbyB(operator.getUserInnerId() + "");
        Integer result = parkInfoMapper.insertConLog(conLog);
        if (result > 0) {
            Map<String, Object> mso = new HashMap<>();
            mso.put("AccountInnerId", account.getAccountInnerId());
            mso.put("Money", account.getMoney() - money);
            result = operatorMapper.updateAccount(mso);
        }

        return result;
    }

    @Override
    public Map<String, Object> sendMessage(Operator operator, JSONObject jsonObject) {

        String UserId =  jsonObject.getString("userByD");
        String InOrOut =  "1".equals(jsonObject.getString("inOrOut"))?"入场":"出场";
        String RecTime =  jsonObject.getString("recTime");
        String CarCode =  jsonObject.getString("carCode");



        CommonUtil.sendWxMsg(UserId, MessageFormat.format(PublicResultConstant.SEND_IN_OUT, CarCode,RecTime,InOrOut));

        System.out.println(MessageFormat.format(PublicResultConstant.SEND_IN_OUT, CarCode,RecTime,InOrOut));

        return null;
    }

    /**
     * 车位剩余情况
     * @param jsonObject
     * @return
     */
    @Override
    public List<Map<String, Object>> searchParkNumByDate(JSONObject jsonObject) {


       List<Map<String,Object>> mso =  parkInfoMapper.searchParkNumBy(jsonObject);
        return mso;
    }


    public Account searchDep(Operator operator) {
        Map<String, Object> mso = new HashMap<>();
        //公司内部id
        Integer CompanyInnerId =   operator.getCompanyInnerId();

        mso.put("CompanyInnerId",CompanyInnerId);
        Account account = null;

         Integer countPay =  parkInfoMapper.selectPayInfo(mso);

        if(countPay>0){

            mso.put("UserTypeInnerId",-1);
            Operator operatorInfo =  operatorMapper.selectOperatorByMap(mso);

            if(operatorInfo!=null) {
                mso.clear();

                mso.put("UserInnerId", operatorInfo.getUserInnerId());
                mso.put("AccountTypeInnerId", 1);

                account = operatorMapper.selectAccountByUser(mso);

            }
        }else{
            //清除map集合
            mso.clear();

            List<Integer> depList = parkInfoMapper.selectPayInfoList();

            if(depList.contains(operator.getDepartmentInnerId())){

                mso.put("UserTypeInnerId",-1);
                mso.put("DepartmentInnerId",operator.getDepartmentInnerId());
                Operator operatorInfo =  operatorMapper.selectOperatorByMap(mso);

                if(operatorInfo!=null) {
                    mso.clear();

                    mso.put("UserInnerId", operatorInfo.getUserInnerId());
                    mso.put("AccountTypeInnerId", 1);
                    account = operatorMapper.selectAccountByUser(mso);

                }

            }else{


               Integer DeptInnerId =  searchLastDept(operator.getDepartmentInnerId(),depList);


               if(DeptInnerId!=null){
                   mso.put("UserTypeInnerId",-1);
                   mso.put("DepartmentInnerId",DeptInnerId);
                   Operator operatorInfo =  operatorMapper.selectOperatorByMap(mso);
                if(operatorInfo!=null) {
                   mso.clear();

                   mso.put("UserInnerId",operatorInfo.getUserInnerId());
                   mso.put("AccountTypeInnerId",1);
                   account =  operatorMapper.selectAccountByUser(mso);
                   }
               }else {

                    mso.put("DepartmentInnerId", operator.getDepartmentInnerId());
                   Department department = parkInfoMapper.searchDepartmentUpTwo(mso);

                   for (int i = department.getDepartmentLevel() - 1; i >= 2; i--) {

                       mso.put("DepartmentInnerId", department.getUpInnerId());
                       mso.put("DepartmentLevel", i);
                       department = parkInfoMapper.searchDepartmentUpTwo(mso);

                   }
                   mso.clear();
                   mso.put("DepartmentInnerId", department.getDepartmentInnerId());
                   mso.put("UserTypeInnerId", -1);
                   Operator operatorInfo = operatorMapper.selectOperatorByMap(mso);
                   if(operatorInfo!=null) {
                       mso.put("UserInnerId", operatorInfo.getUserInnerId());
                       mso.put("AccountTypeInnerId", 1);
                       account = operatorMapper.selectAccountByUser(mso);
                   }

               }
            }

        }


        return account;

    }

    /**
     * 返回最终部门id
     * @param DepartmentInnerId
     */
    public Integer searchLastDept(Integer DepartmentInnerId,List<Integer> depList) {

        Integer DeptUp =   parkInfoMapper.selectDeptParent(DepartmentInnerId);

        if(DeptUp!=null && DeptUp>0){
            if(depList.contains(DeptUp)){

                return  DeptUp;
            }else {
                searchLastDept(DeptUp, depList);
            }

        }

        return  null;

    }


    /**
     * 验证车位数量
     * @param jsonObject
     */
    public void searchParkNumVali(JSONObject jsonObject) throws BusinessException {

      List<Integer>  integerList =  parkInfoMapper.searchParkNum(jsonObject);

      for (Integer parkNum:integerList){

          if(parkNum<=0){
              throw new BusinessException(PublicResultConstant.STAFFPARK_NUM_ERROR);
          }

      }

    }

}
