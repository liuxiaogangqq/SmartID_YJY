package com.dhsr.wx.cp.task;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.base.PublicResultConstant;
import com.dhsr.wx.cp.entity.ChargeRecord;
import com.dhsr.wx.cp.entity.Department;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.entity.OrderRecord;
import com.dhsr.wx.cp.mapper.OperatorMapper;
import com.dhsr.wx.cp.mapper.ParkInfoMapper;
import com.dhsr.wx.cp.utils.CommonUtil;
import com.dhsr.wx.cp.utils.DateTimeUtil;
import com.dhsr.wx.cp.utils.PubFun;
import lombok.extern.slf4j.Slf4j;
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
public class TaskEventDate {

    boolean  isTask1DealDate =  false;

    @Resource
    private ParkInfoMapper parkInfoMapper;
    @Resource
    private OperatorMapper operatorMapper;

    /**
     * 定时执行逾期任务
     */
    @Scheduled(cron = "0 50 23 * * ?")
    public void taskDeal() {

        if (!isTask1DealDate) {

            Map<String,Object> mso = new HashMap<>(3);

            //DateTimeUtil.addDays(new Date(),30);

            mso.put("ParkDate", DateTimeUtil.formatDateTimetoString(DateTimeUtil.addDays(new Date(),30),DateTimeUtil.FMT_yyyyMMdd));
            mso.put("ParkType",1);
            mso.put("ParkNum","VisNumber");
            parkInfoMapper.insertParkDate(mso);
            mso.put("ParkDate", DateTimeUtil.formatDateTimetoString(DateTimeUtil.addDays(new Date(),30),DateTimeUtil.FMT_yyyyMMdd));
            mso.put("ParkType",2);
            mso.put("ParkNum","CarsNumber");
            parkInfoMapper.insertParkDate(mso);



       /*     for(int i =0;i<=29;i++){
                mso.put("ParkDate", DateTimeUtil.formatDateTimetoString(DateTimeUtil.addDays(new Date(),i),DateTimeUtil.FMT_yyyyMMdd));
                mso.put("ParkType",1);
                mso.put("ParkNum","VisNumber");
                parkInfoMapper.insertParkDate(mso);
            }

            for(int i =0;i<=29;i++){
                mso.put("ParkDate", DateTimeUtil.formatDateTimetoString(DateTimeUtil.addDays(new Date(),i),DateTimeUtil.FMT_yyyyMMdd));
                mso.put("ParkType",2);
                mso.put("ParkNum","CarsNumber");
                parkInfoMapper.insertParkDate(mso);
            }*/




            isTask1DealDate = false;

        } else {
            System.out.println("isTask1DealDate------waiting------");
        }

    }




}
