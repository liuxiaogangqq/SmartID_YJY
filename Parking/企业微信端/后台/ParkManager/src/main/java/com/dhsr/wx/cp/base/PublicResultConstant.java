package com.dhsr.wx.cp.base;

/**
 * @author liuxiaogang
 */
public class PublicResultConstant {

    public static final String FAILED = "系统错误";

    public static final String SUCCEED = "操作成功";

    public static final String RULE_SUCCEED = "操作成功,正在下发授权";

    public static final String UNAUTHORIZED = "获取登录用户信息认证失败";

    public static final String UNAUTHORIZEDNULL = "缺少Authorization信息";

    public static final String ERROR = "操作失败";

    public static final String TIME_GROUP_ERROR = "时间组被占用，不能删除";

    public static final String TIME_GROUP_NUM_ERROR = "时间组最大限制为8个";

    public static final String HOLIDAY_NUM_ERROR = "节假日最大限制为19个";

    public static final String HOLIDAY_NAME_ERROR = "节假日名称重复";

    public static final String DATA_ERROR = "数据操作错误";

    public static final String PARAM_ERROR = "参数错误";

    public static final String INVALID_USERNAME_PASSWORD = "用户名或密码错误";

    public static final String INVALID_RE_PASSWORD = "两次输入密码不一致";

    public static final String INVALID_USER = "用户不存在";

    public static final String INVALID_ROLE = "角色不存在";

    public static final String TASK_NO = "角色不存在";

    public static final String ROLE_USER_USED = "角色使用中，不可删除";

    public static final String USER_NO_PERMITION = "当前用户无该接口权限";

    public static final String VERIFY_PARAM_ERROR = "校验码错误";

    public static final String VERIFY_PARAM_PASS = "校验码过期";

    public static final String MOBILE_ERROR = "手机号格式错误";

    public static final String UPDATE_ROLEINFO_ERROR = "更新角色信息失败";

    public static final String UPDATE_SYSADMIN_INFO_ERROR = "不能修改管理员信息!";

    public static final String CREATE_FINGER_ERROR = "创建指静脉文件失败!";

    public static final String CREATE_FINGER_NULL = "指静脉信息不能为空!";

    public static final String CONNET_ERROR = "连接设备错误!";

    public static final String GATEWAY_NAME_REPEAT = "网关名称重复!";

    public static final String LOCK_NO_ERROR = "请选择设备!";

    public static final String LOCK_NOUSER_ERROR = "请选择人员!";

    public static final String GATEWAY_DELETE = "网关下有设备，不允许删除!";

    public static final String LOCK_NAME_REPEAT = "设备名称重复!";

    public static final String LOCK_CODE_REPEAT = "设备编号重复!";

    public static final String LOCK_MAX_ERROR = "超过设备最大授权数量!";

    public static final String OPERATOR_ERROR = "操作员编号重复!";

    public static final String USER_NAME_REPEAT = "用户编号重复!";

    public static final String DEPARTMENT_CODE_REPEAT = "部门编号重复!";

    public static final String DEPARTMENT_ERROR = "部门下存在人员，不能进行删除!";

    public static final String DEPARTMENT_CHL_ERROR = "请先删除子集部门!";

    public static final String GATEWAY_ERROR = "网关通道忙!";

    public static final String GATEWAY_MAX_ERROR = "超过网关最大授权数量!";

    public static final String MARK_ERROR = "标识号已存在!";

    public static final String USER_MARK_ERROR = "此人名下有标识，不能删除!";

    public static final String MONEY_ORDER = "未查到订单号！";

    public static final String MONEY_ERROR = "支付异常!";

    public static final String MONEY_URL_ERROR = "获取支付金额异常!";

    public static final String MONEY_URL_NO = "车辆在免费停车时间范围内,无需支付!";

    public static final String FILE_NULL_ERROR = "文件不能为空!";

    public static final String FILE_TYPE_ERROR = "文件格式错误!";

    public static final String FILE_SIZE_ERROR = "文件大小不能为0!";

    public static final String MONEY_CON = "未找到支付相关信息，请联系管理员！";

    public static final String TIME_VALIDITY = "无法取消预约，已经超过预约开始时间或连接服务器异常!";

    public static final String TIME_MONEY = "您好，系统发现当前车牌号有一笔费用未结清，请结清后重新预约!";

    public static final String MONEY_NO = "余额预警，您的余额已经到达预警范围，请尽快充值，以免影响您的正常使用!";

    public static final String STATE_ERROR = "当前状态异常!";

    public static final String PARK_NUM_ERROR = "访客车位已满!";

    public static final String STAFFPARK_NUM_ERROR = "临时车位已满!";

    public static final String NET_CON_ERROR = "网络连接异常!";

    public static final String DATE_CON_ERROR = "日期获取错误!";

    public static final String DATE_PRE_ERROR = "日期转换错误!";

    public static final String DATE_REPEAT_ERROR = "预约时间重复!";

    public static final String DATE_THE_ERROR = "您的车已经在场内，不允许当天预约!";

    public static final String DATE_VIS_ERROR = "预约时间必须大于当前时间!";

    public static final String CANCLE_VIS_ERROR = "您已经取消请刷新后重试!";

    public static final String SEND_IN_OUT = "尊敬的用户，您预约的车辆【{0}】已经在【{1}】{2}!";


}
