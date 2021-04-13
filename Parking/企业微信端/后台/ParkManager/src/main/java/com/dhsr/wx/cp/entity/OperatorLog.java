package com.dhsr.wx.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: sirelock
 * @Package: com.dhsr.sirelock.entity
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-03-19 0:21
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-03-19 0:21
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统内部操作日志id;自增
     */
    private Integer LogInnerId;
    /**
     * 操作员内部id
     */
    private Integer OperatorInnerId;
    /**
     * 操作时间
     */
    private String OperatorDate;
    /**
     * 操作类型;1、登录2添加3修改4删除5退出6下发
     */
    private Integer OperatorType;
    /**
     * 操作员IP
     */
    private String OperatorIp;
    /**
     * 操作内容
     */
    private String OperatorContent;
    /**
     * 操作参数
     */
    private String ActionsArgs;
    /**
     * 操作结果
     */
    private Integer Succeed;
    /**
     * 操作方法名
     */
    private String MethodName;
    /**
     * 操作信息
     */
    private String Message;
    /**
     * 操作实体类
     */
    private String ModelName;
    /**
     * 操作描述
     */
    private String LogDescription;
    /**
     * 创建时间
     */
    private String CreateTime;
    /**
     * 操作类
     */
    private String ClassName;
    /**
     * 操作类
     */
    private String ActionN;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public Integer getLogInnerId() {
        return LogInnerId;
    }

    public void setLogInnerId(Integer logInnerId) {
        LogInnerId = logInnerId;
    }

    public Integer getOperatorInnerId() {
        return OperatorInnerId;
    }

    public void setOperatorInnerId(Integer operatorInnerId) {
        OperatorInnerId = operatorInnerId;
    }

    public String getOperatorDate() {
        return OperatorDate;
    }

    public void setOperatorDate(String operatorDate) {
        OperatorDate = operatorDate;
    }

    public Integer getOperatorType() {
        return OperatorType;
    }

    public void setOperatorType(Integer operatorType) {
        OperatorType = operatorType;
    }

    public String getOperatorIp() {
        return OperatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        OperatorIp = operatorIp;
    }

    public String getOperatorContent() {
        return OperatorContent;
    }

    public void setOperatorContent(String operatorContent) {
        OperatorContent = operatorContent;
    }

    public String getActionsArgs() {
        return ActionsArgs;
    }

    public void setActionsArgs(String actionsArgs) {
        ActionsArgs = actionsArgs;
    }

    public Integer getSucceed() {
        return Succeed;
    }

    public void setSucceed(Integer succeed) {
        Succeed = succeed;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getLogDescription() {
        return LogDescription;
    }

    public void setLogDescription(String logDescription) {
        LogDescription = logDescription;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getActionN() {
        return ActionN;
    }

    public void setActionN(String actionN) {
        ActionN = actionN;
    }

    @Override
    public String toString() {
        return "OperatorLog{" +
            "LogInnerId=" + LogInnerId +
            ", OperatorInnerId=" + OperatorInnerId +
            ", OperatorDate='" + OperatorDate + '\'' +
            ", OperatorType=" + OperatorType +
            ", OperatorIp='" + OperatorIp + '\'' +
            ", OperatorContent='" + OperatorContent + '\'' +
            ", ActionsArgs='" + ActionsArgs + '\'' +
            ", Succeed=" + Succeed +
            ", MethodName='" + MethodName + '\'' +
            ", Message='" + Message + '\'' +
            ", ModelName='" + ModelName + '\'' +
            ", LogDescription='" + LogDescription + '\'' +
            ", CreateTime='" + CreateTime + '\'' +
            ", ClassName='" + ClassName + '\'' +
            '}';
    }
}
