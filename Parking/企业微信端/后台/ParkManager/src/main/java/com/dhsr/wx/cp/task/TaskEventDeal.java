package com.dhsr.wx.cp.task;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.base.PublicResultConstant;
import com.dhsr.wx.cp.entity.*;
import com.dhsr.wx.cp.mapper.OperatorMapper;
import com.dhsr.wx.cp.mapper.ParkInfoMapper;
import com.dhsr.wx.cp.utils.CommonUtil;
import com.dhsr.wx.cp.utils.DateTimeUtil;
import com.dhsr.wx.cp.utils.PubFun;
import com.dhsr.wx.cp.utils.UnsafeOkHttpClient;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * @ProjectName: sirelock
 * @Package: com.dhsr.sirelock.task
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-03-23 18:13
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-03-23 18:13
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
@Component
@Transactional
@Slf4j
public class TaskEventDeal {

    boolean isTaskReat, isTaskSend, isTask1Deal, isTaskEnd, isTaskSendInfo = false;

    @Resource
    private ParkInfoMapper parkInfoMapper;
    @Resource
    private OperatorMapper operatorMapper;

    /**
     * 定时执行逾期任务
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void taskDeal() {

        if (!isTask1Deal) {
            isTask1Deal = true;
            Map<String, Object> mso = new HashMap<>();

            mso.put("VisitorState", 1);

            List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(mso);

            for (OrderRecord orderRecord : orderRecordList) {
                try {
                    mso.put("RecordInnerId", orderRecord.getRecordInnerId());
                    //处理时间格式
                    Date nowDate = DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);
                    // Date endDate = DateTimeUtil.parseToDate(orderRecord.getVisitorEndTime(),DateTimeUtil.FMT_yyyyMMddHHmmss);
                    if (orderRecord.getOrderType() == 1) {
                        Date visDate = DateTimeUtil.addHours(nowDate, 2);
                        // 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
                        int i = DateTimeUtil.compareTwoDate(visDate, new Date());

                        if (i == 1) {

                            //逾期
                            mso.put("VisitorState", 8);

                            parkInfoMapper.updateRecordStates(mso);

                            orderRecord.setCarTypeInnerId(3);
                            //普通访客车  2未收费  3逾期扣费  0金额
                            insertChargeRecord(orderRecord, visDate, 4, 3, 0);

                            /**
                             * 立方停车场操作
                             */
                            JSONObject jsonObject1 = new JSONObject();
                            //   if(orderRecordList.get(0).getCarTypeInnerId()==3){

                            Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                            Date visDateEnd = DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 105);

                            jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                            jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));

                            jsonObject1.put("carCode", orderRecord.getVisitorCarCode());
                            jsonObject1.put("type", 9);
                            jsonObject1.put("act", 0);//1:添加/修改，0:删除

                            JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                            log.info("取消预约==" + jsonO.toJSONString());


                            jsonObject1.put("ParkResidue", "ParkResidue");
                            jsonObject1.put("VisitorBeginTime", orderRecord.getVisitorBeginTime());
                            jsonObject1.put("VisitorEndTime", orderRecord.getVisitorEndTime());
                            jsonObject1.put("ParkType", 1);
                            parkInfoMapper.updateParkNumAdd(jsonObject1);

                        }

                    } else if (orderRecord.getOrderType() == 2) {
                        Date visDate = DateTimeUtil.addMinutes(nowDate, 30);


                        // 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
                        int i = DateTimeUtil.compareTwoDate(visDate, new Date());

                        if (i == 1) {
                            String paramV = "inTime=" + orderRecord.getVisitorBeginTime() + "&outTime=" + DateTimeUtil.formatDateTimetoString(visDate) + "&carType=1&overdueFlag=2";
                            JSONObject jsonObject = CommonUtil.okHttpGet(paramV);
                            log.info("收到逾期回调消费接口==" + jsonObject.toJSONString());
                            boolean bState = jsonObject.containsKey("state");

                            if (bState && jsonObject.getInteger("state") == 200) {
                                orderRecord.setCarTypeInnerId(1);
                                insertChargeRecord(orderRecord, visDate, 3, 0, jsonObject.getInteger("money") * 100);

                                //逾期
                                mso.put("VisitorState", 8);
                                parkInfoMapper.updateRecordStates(mso);


                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("ParkResidue", "ParkResidue");
                                jsonObject1.put("VisitorBeginTime", orderRecord.getVisitorBeginTime());
                                jsonObject1.put("VisitorEndTime", orderRecord.getVisitorEndTime());
                                jsonObject1.put("ParkType", 2);
                                parkInfoMapper.updateParkNumAdd(jsonObject1);
                            }
                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("定时处理逾期任务异常", e);
                    continue;
                }

            }
            isTask1Deal = false;
            mso.clear();
        } else {
            System.out.println("isTask1Deal------waiting------");
        }

    }

    /**
     * 定时执行发送任务
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void taskDealSend() {

        if (!isTaskSend) {
            Map<String, Object> mso = new HashMap<>();
            isTaskSend = true;
            mso.put("VisitorState", 1);
            mso.put("ACInfo", 0);
            mso.put("VisitorSendTime", "now");
            List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(mso);

            for (OrderRecord orderRecord : orderRecordList) {
                try {
                    mso.put("RecordInnerId", orderRecord.getRecordInnerId());
                    //处理时间格式
                    Date nowDate = DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);
                    // Date endDate = DateTimeUtil.parseToDate(orderRecord.getVisitorEndTime(),DateTimeUtil.FMT_yyyyMMddHHmmss);
                    if (orderRecord.getOrderType() == 2) {
                        Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                        Date visDateEnd = DateTimeUtil.addMinutes(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), 30);

                        //立方停车场接口
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("carCode", orderRecord.getVisitorCarCode());
                        jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                        jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));
                        jsonObject1.put("type", 9);
                        jsonObject1.put("act", 1);//1:添加/修改，0:删除


                        JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);
                        log.info("员工预约登记==" + jsonO.toJSONString());
                        parkInfoMapper.updateRecordAC(mso);
                    } else if (orderRecord.getOrderType() == 1) {
                        //立方停车场接口
                        JSONObject jsonObject1 = new JSONObject();
                        Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                        Date visDateEnd = DateTimeUtil.parseToDate(orderRecord.getVisitorEndTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);

                        jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                        jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));
                        jsonObject1.put("carCode", orderRecord.getVisitorCarCode());

                        jsonObject1.put("type", 9);
                        jsonObject1.put("act", 1);//1:添加/修改，0:删除


                        JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                        log.info("公司预约登记==" + jsonO.toJSONString());
                        parkInfoMapper.updateRecordAC(mso);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("定时处理发送停车场任务异常", e);
                    continue;
                }

            }
            isTaskSend = false;
            mso.clear();
        } else {
            System.out.println("isTaskSend---waiting");
        }


    }

    /**
     * 定时执行延期任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void taskDealRepeat() {

        if (!isTaskReat) {
            isTaskReat = true;
            Map<String, Object> mso = new HashMap<>();

            mso.put("VisitorState", 4);
            mso.put("OrderType", 1);

            List<OrderRecord> orderRecordList = parkInfoMapper.searchOrderRecord(mso);

            for (OrderRecord orderRecord : orderRecordList) {
                try {
                    mso.put("RecordInnerId", orderRecord.getRecordInnerId());
                    //处理时间格式
                    Date endDate = DateTimeUtil.parseToDate(orderRecord.getVisitorEndTime(), DateTimeUtil.FMT_yyyyMMddHHmmss);
                    if (orderRecord.getOrderType() == 1) {
                        // Date visDate = DateTimeUtil.addHours(endDate, 2);
                        // 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
                        int i = DateTimeUtil.compareTwoDate(endDate, new Date());

                        if (i == 1) {
                            /**
                             * 立方停车场操作
                             */
                            JSONObject jsonObject1 = new JSONObject();
                            //   if(orderRecordList.get(0).getCarTypeInnerId()==3){

                            Date visDateStart = DateTimeUtil.addHours(DateTimeUtil.parseToDate(orderRecord.getVisitorBeginTime(), DateTimeUtil.FMT_yyyyMMddHHmmss), -2);
                            Date visDateEnd = DateTimeUtil.addHours(new Date(), 2);

                            jsonObject1.put("beginTime", DateTimeUtil.formatDateTimetoString(visDateStart));
                            jsonObject1.put("endTime", DateTimeUtil.formatDateTimetoString(visDateEnd));

                            jsonObject1.put("carCode", orderRecord.getVisitorCarCode());
                            jsonObject1.put("type", 9);
                            jsonObject1.put("act", 1);//1:添加/修改，0:删除

                            JSONObject jsonO = CommonUtil.okHttpPost(jsonObject1);

                            log.info("延期预约==" + jsonO.toJSONString());
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("定时处理延期任务异常", e);
                    continue;
                }

            }
            isTaskReat = false;
            mso.clear();

        } else {
            System.out.println("isTaskReat----waiting-----");
        }

    }

    /**
     * 定时执行发送任务
     */
    @Scheduled(cron = "0 0 08 * * ?")
    public void taskSendMoneyInfo() {

        if (!isTaskSendInfo) {


            try {

                //操作员信息
                List<Operator> operatorList = operatorMapper.selectOperatorInfo(null);

                for (Operator operator : operatorList) {

                    ParkConfig parkConfig =  parkInfoMapper.searchConfigInfo();

                    Account account = searchDep(operator);

                    if(parkConfig.getMinMoney()>account.getMoney()){
                        if(operator.getStandbyD()!=""&&!"".equals(operator.getStandbyD())){
                            CommonUtil.sendWxMsg(operator.getStandbyD(), PublicResultConstant.MONEY_NO);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("定时处理发送余额报警异常", e);
            }
            isTaskSendInfo = false;

        } else {
            System.out.println("isTaskSendInfo---waiting");
        }


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


            mso.clear();

            mso.put("UserInnerId",operatorInfo.getUserInnerId());
            mso.put("AccountTypeInnerId",1);

            account =  operatorMapper.selectAccountByUser(mso);

        }else{
            //清除map集合
            mso.clear();

            List<Integer> depList = parkInfoMapper.selectPayInfoList();

            if(depList.contains(operator.getDepartmentInnerId())){

                mso.put("UserTypeInnerId",-1);
                mso.put("DepartmentInnerId",operator.getDepartmentInnerId());
                Operator operatorInfo =  operatorMapper.selectOperatorByMap(mso);

                mso.clear();

                mso.put("UserInnerId",operatorInfo.getUserInnerId());
                mso.put("AccountTypeInnerId",1);
                account =  operatorMapper.selectAccountByUser(mso);


            }else{


                Integer DeptInnerId =  searchLastDept(operator.getDepartmentInnerId(),depList);


                if(DeptInnerId!=null){
                    mso.put("UserTypeInnerId",-1);
                    mso.put("DepartmentInnerId",DeptInnerId);
                    Operator operatorInfo =  operatorMapper.selectOperatorByMap(mso);

                    mso.clear();

                    mso.put("UserInnerId",operatorInfo.getUserInnerId());
                    mso.put("AccountTypeInnerId",1);
                    account =  operatorMapper.selectAccountByUser(mso);

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
                    mso.put("UserInnerId", operatorInfo.getUserInnerId());
                    mso.put("AccountTypeInnerId", 1);
                    account = operatorMapper.selectAccountByUser(mso);

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
     * 递归获取部门
     *
     * @param DepartmentInnerId
     * @return
     */
    public String recursionDept(Integer DepartmentInnerId, JSONObject jsonObject) {
        //   String deptString = "";

        if (DepartmentInnerId != null) {
            // deptString += DepartmentInnerId + ",";
            String deptString = jsonObject.getString("deptString") != null ? jsonObject.getString("deptString") + "," : "";
            jsonObject.put("deptString", deptString + DepartmentInnerId);
            List<Integer> integerList = parkInfoMapper.selectDeptChid(DepartmentInnerId);

            Iterator<Integer> integerIterator = integerList.iterator();
            while (integerIterator.hasNext()) {

                Integer dept = integerIterator.next();
                recursionDept(dept, jsonObject);

            }
        }

        return "";
    }


    /**
     * @param orderRecord 预约记录
     * @param visDate     逾期时间
     * @param ChargeState 收费状态
     * @param ChargeType  支付方式
     * @param Money       支付金额
     * @return
     */
    public ChargeRecord insertChargeRecord(OrderRecord orderRecord, Date visDate, int ChargeState, int ChargeType, int Money) {

        ChargeRecord chargeRecord = new ChargeRecord();
        //车牌号
        chargeRecord.setCarNumber(orderRecord.getVisitorCarCode());
        //车辆类型内部id
        chargeRecord.setCarType(orderRecord.getCarTypeInnerId());
        //收费状态 2为未收费
        chargeRecord.setChargeState(ChargeState);
        //支付方式为0 代表未支付
        chargeRecord.setChargeType(ChargeType);
        //金额为0元
        chargeRecord.setMoney(Money);
        //预约开始时间
        chargeRecord.setInTime(orderRecord.getVisitorBeginTime());
        //预约结束时间
        chargeRecord.setOutTime(DateTimeUtil.formatDateTimetoString(visDate));
        //订单号
        chargeRecord.setOrderNo(PubFun.orderNum());
        //记录内部id
        chargeRecord.setVisRecordInnerId(orderRecord.getRecordInnerId());


        log.info("逾期插入消费记录=" + chargeRecord.toString());

        //插入消费记录返回结果
        parkInfoMapper.insertChargeRecord(chargeRecord);
        return chargeRecord;


    }


}
