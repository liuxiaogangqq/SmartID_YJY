package com.dhsr.wx.cp.entity;

/**
 * 消费记录表
 */
public class ConLog {
    private Long ConLogInnerId;
    private Integer MarkInnerId;
    private String MarkId;
    private Integer MarkTypeInnerId;
    private Integer UserInnerId;
    private Integer DepartmentInnerId;
    private Integer CompanyInnerId;
    private Integer ConTerminalInnerId;
    private Integer TerminalTypeInnerId;
    private Integer MerchantInnerId;
    private Integer AppInnerId;
    private Integer AreaInnerId;
    private Integer PersonMoney;
    private Integer AllowanceMoney;
    private Integer NumberMoney;
    private Integer PersonBFMoney;
    private Integer PersonAFMoney;
    private Integer AllowanceBFMoney;
    private Integer AllowanceAFMoney;
    private Integer NumberBFMoney;
    private Integer NumberAFMoney;
    private Integer Money1;
    private Integer Money2;
    private Integer Money;
    private Integer DiscountMoney;
    private Integer Proportion;
    private String ConDatetime;
    private String UploadDatetime;
    private Integer ConTypeInnerId;
    private Integer ConPattern;
    private Integer ConWay;
    private Integer LimitTimes;
    private Integer OffLine;
    private Long ErrorConLogInnerId;
    private Integer Operator;
    private String Remark;
    private String StandbyA;
    private String StandbyB;
    private String StandbyC;
    private String StandbyD;
    private String Rules;

    public ConLog() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ConLog(Long conLogInnerId, Integer markInnerId, String markId, Integer markTypeInnerId, Integer userInnerId,
                  Integer departmentInnerId, Integer companyInnerId, Integer conTerminalInnerId, Integer terminalTypeInnerId,
                  Integer merchantInnerId, Integer appInnerId, Integer areaInnerId, Integer personMoney,
                  Integer allowanceMoney, Integer numberMoney, Integer personBFMoney, Integer personAFMoney,
                  Integer allowanceBFMoney, Integer allowanceAFMoney, Integer numberBFMoney, Integer numberAFMoney,
                  Integer money1, Integer money2, Integer money, Integer discountMoney, Integer proportion,
                  String conDatetime, String uploadDatetime, Integer conTypeInnerId, Integer conPattern, Integer conWay,
                  Integer limitTimes, Integer offLine, Long errorConLogInnerId, Integer operator, String remark,
                  String standbyA, String standbyB, String standbyC, String standbyD, String rules) {
        super();
        ConLogInnerId = conLogInnerId;
        MarkInnerId = markInnerId;
        MarkId = markId;
        MarkTypeInnerId = markTypeInnerId;
        UserInnerId = userInnerId;
        DepartmentInnerId = departmentInnerId;
        CompanyInnerId = companyInnerId;
        ConTerminalInnerId = conTerminalInnerId;
        TerminalTypeInnerId = terminalTypeInnerId;
        MerchantInnerId = merchantInnerId;
        AppInnerId = appInnerId;
        AreaInnerId = areaInnerId;
        PersonMoney = personMoney;
        AllowanceMoney = allowanceMoney;
        NumberMoney = numberMoney;
        PersonBFMoney = personBFMoney;
        PersonAFMoney = personAFMoney;
        AllowanceBFMoney = allowanceBFMoney;
        AllowanceAFMoney = allowanceAFMoney;
        NumberBFMoney = numberBFMoney;
        NumberAFMoney = numberAFMoney;
        Money1 = money1;
        Money2 = money2;
        Money = money;
        DiscountMoney = discountMoney;
        Proportion = proportion;
        ConDatetime = conDatetime;
        UploadDatetime = uploadDatetime;
        ConTypeInnerId = conTypeInnerId;
        ConPattern = conPattern;
        ConWay = conWay;
        LimitTimes = limitTimes;
        OffLine = offLine;
        ErrorConLogInnerId = errorConLogInnerId;
        Operator = operator;
        Remark = remark;
        StandbyA = standbyA;
        StandbyB = standbyB;
        StandbyC = standbyC;
        StandbyD = standbyD;
        Rules = rules;
    }

    public Long getConLogInnerId() {
        return ConLogInnerId;
    }

    public void setConLogInnerId(Long conLogInnerId) {
        ConLogInnerId = conLogInnerId;
    }

    public Integer getMarkInnerId() {
        return MarkInnerId;
    }

    public void setMarkInnerId(Integer markInnerId) {
        MarkInnerId = markInnerId;
    }

    public String getMarkId() {
        return MarkId;
    }

    public void setMarkId(String markId) {
        MarkId = markId;
    }

    public Integer getMarkTypeInnerId() {
        return MarkTypeInnerId;
    }

    public void setMarkTypeInnerId(Integer markTypeInnerId) {
        MarkTypeInnerId = markTypeInnerId;
    }

    public Integer getUserInnerId() {
        return UserInnerId;
    }

    public void setUserInnerId(Integer userInnerId) {
        UserInnerId = userInnerId;
    }

    public Integer getDepartmentInnerId() {
        return DepartmentInnerId;
    }

    public void setDepartmentInnerId(Integer departmentInnerId) {
        DepartmentInnerId = departmentInnerId;
    }

    public Integer getCompanyInnerId() {
        return CompanyInnerId;
    }

    public void setCompanyInnerId(Integer companyInnerId) {
        CompanyInnerId = companyInnerId;
    }

    public Integer getConTerminalInnerId() {
        return ConTerminalInnerId;
    }

    public void setConTerminalInnerId(Integer conTerminalInnerId) {
        ConTerminalInnerId = conTerminalInnerId;
    }

    public Integer getTerminalTypeInnerId() {
        return TerminalTypeInnerId;
    }

    public void setTerminalTypeInnerId(Integer terminalTypeInnerId) {
        TerminalTypeInnerId = terminalTypeInnerId;
    }

    public Integer getMerchantInnerId() {
        return MerchantInnerId;
    }

    public void setMerchantInnerId(Integer merchantInnerId) {
        MerchantInnerId = merchantInnerId;
    }

    public Integer getAppInnerId() {
        return AppInnerId;
    }

    public void setAppInnerId(Integer appInnerId) {
        AppInnerId = appInnerId;
    }

    public Integer getAreaInnerId() {
        return AreaInnerId;
    }

    public void setAreaInnerId(Integer areaInnerId) {
        AreaInnerId = areaInnerId;
    }

    public Integer getPersonMoney() {
        return PersonMoney;
    }

    public void setPersonMoney(Integer personMoney) {
        PersonMoney = personMoney;
    }

    public Integer getAllowanceMoney() {
        return AllowanceMoney;
    }

    public void setAllowanceMoney(Integer allowanceMoney) {
        AllowanceMoney = allowanceMoney;
    }

    public Integer getNumberMoney() {
        return NumberMoney;
    }

    public void setNumberMoney(Integer numberMoney) {
        NumberMoney = numberMoney;
    }

    public Integer getPersonBFMoney() {
        return PersonBFMoney;
    }

    public void setPersonBFMoney(Integer personBFMoney) {
        PersonBFMoney = personBFMoney;
    }

    public Integer getPersonAFMoney() {
        return PersonAFMoney;
    }

    public void setPersonAFMoney(Integer personAFMoney) {
        PersonAFMoney = personAFMoney;
    }

    public Integer getAllowanceBFMoney() {
        return AllowanceBFMoney;
    }

    public void setAllowanceBFMoney(Integer allowanceBFMoney) {
        AllowanceBFMoney = allowanceBFMoney;
    }

    public Integer getAllowanceAFMoney() {
        return AllowanceAFMoney;
    }

    public void setAllowanceAFMoney(Integer allowanceAFMoney) {
        AllowanceAFMoney = allowanceAFMoney;
    }

    public Integer getNumberBFMoney() {
        return NumberBFMoney;
    }

    public void setNumberBFMoney(Integer numberBFMoney) {
        NumberBFMoney = numberBFMoney;
    }

    public Integer getNumberAFMoney() {
        return NumberAFMoney;
    }

    public void setNumberAFMoney(Integer numberAFMoney) {
        NumberAFMoney = numberAFMoney;
    }

    public Integer getMoney1() {
        return Money1;
    }

    public void setMoney1(Integer money1) {
        Money1 = money1;
    }

    public Integer getMoney2() {
        return Money2;
    }

    public void setMoney2(Integer money2) {
        Money2 = money2;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }

    public Integer getDiscountMoney() {
        return DiscountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        DiscountMoney = discountMoney;
    }

    public Integer getProportion() {
        return Proportion;
    }

    public void setProportion(Integer proportion) {
        Proportion = proportion;
    }

    public String getConDatetime() {
        return ConDatetime;
    }

    public void setConDatetime(String conDatetime) {
        ConDatetime = conDatetime;
    }

    public String getUploadDatetime() {
        return UploadDatetime;
    }

    public void setUploadDatetime(String uploadDatetime) {
        UploadDatetime = uploadDatetime;
    }

    public Integer getConTypeInnerId() {
        return ConTypeInnerId;
    }

    public void setConTypeInnerId(Integer conTypeInnerId) {
        ConTypeInnerId = conTypeInnerId;
    }

    public Integer getConPattern() {
        return ConPattern;
    }

    public void setConPattern(Integer conPattern) {
        ConPattern = conPattern;
    }

    public Integer getConWay() {
        return ConWay;
    }

    public void setConWay(Integer conWay) {
        ConWay = conWay;
    }

    public Integer getLimitTimes() {
        return LimitTimes;
    }

    public void setLimitTimes(Integer limitTimes) {
        LimitTimes = limitTimes;
    }

    public Integer getOffLine() {
        return OffLine;
    }

    public void setOffLine(Integer offLine) {
        OffLine = offLine;
    }

    public Long getErrorConLogInnerId() {
        return ErrorConLogInnerId;
    }

    public void setErrorConLogInnerId(Long errorConLogInnerId) {
        ErrorConLogInnerId = errorConLogInnerId;
    }

    public Integer getOperator() {
        return Operator;
    }

    public void setOperator(Integer operator) {
        Operator = operator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getStandbyA() {
        return StandbyA;
    }

    public void setStandbyA(String standbyA) {
        StandbyA = standbyA;
    }

    public String getStandbyB() {
        return StandbyB;
    }

    public void setStandbyB(String standbyB) {
        StandbyB = standbyB;
    }

    public String getStandbyC() {
        return StandbyC;
    }

    public void setStandbyC(String standbyC) {
        StandbyC = standbyC;
    }

    public String getStandbyD() {
        return StandbyD;
    }

    public void setStandbyD(String standbyD) {
        StandbyD = standbyD;
    }

    public String getRules() {
        return Rules;
    }

    public void setRules(String rules) {
        Rules = rules;
    }

    @Override
    public String toString() {
        return "ConLog [ConLogInnerId=" + ConLogInnerId + ", MarkInnerId=" + MarkInnerId + ", MarkId=" + MarkId
            + ", MarkTypeInnerId=" + MarkTypeInnerId + ", UserInnerId=" + UserInnerId + ", DepartmentInnerId="
            + DepartmentInnerId + ", CompanyInnerId=" + CompanyInnerId + ", ConTerminalInnerId="
            + ConTerminalInnerId + ", TerminalTypeInnerId=" + TerminalTypeInnerId + ", MerchantInnerId="
            + MerchantInnerId + ", AppInnerId=" + AppInnerId + ", AreaInnerId=" + AreaInnerId + ", PersonMoney="
            + PersonMoney + ", AllowanceMoney=" + AllowanceMoney + ", NumberMoney=" + NumberMoney
            + ", PersonBFMoney=" + PersonBFMoney + ", PersonAFMoney=" + PersonAFMoney + ", AllowanceBFMoney="
            + AllowanceBFMoney + ", AllowanceAFMoney=" + AllowanceAFMoney + ", NumberBFMoney=" + NumberBFMoney
            + ", NumberAFMoney=" + NumberAFMoney + ", Money1=" + Money1 + ", Money2=" + Money2 + ", Money=" + Money
            + ", DiscountMoney=" + DiscountMoney + ", Proportion=" + Proportion + ", ConDatetime=" + ConDatetime
            + ", UploadDatetime=" + UploadDatetime + ", ConTypeInnerId=" + ConTypeInnerId + ", ConPattern="
            + ConPattern + ", ConWay=" + ConWay + ", LimitTimes=" + LimitTimes + ", OffLine=" + OffLine
            + ", ErrorConLogInnerId=" + ErrorConLogInnerId + ", Operator=" + Operator + ", Remark=" + Remark
            + ", StandbyA=" + StandbyA + ", StandbyB=" + StandbyB + ", StandbyC=" + StandbyC + ", StandbyD="
            + StandbyD + ", Rules=" + Rules + "]";
    }


}
