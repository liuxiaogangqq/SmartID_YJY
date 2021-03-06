package com.dhsr.wx.cp.aspect;

import com.alibaba.fastjson.JSONObject;
import com.dhsr.wx.cp.annotation.Log;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.entity.OperatorLog;
import com.dhsr.wx.cp.service.ILogService;
import com.dhsr.wx.cp.service.IOperatorService;
import com.dhsr.wx.cp.service.SpringContextBeanService;
import com.dhsr.wx.cp.utils.ComUtil;
import com.dhsr.wx.cp.utils.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录日志切面
 *
 * @author liuxiaogang
 */
public class RecordLogAspect extends AbstractAspectManager {

    public RecordLogAspect(AspectApi aspectApi) {
        super(aspectApi);
    }

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        super.doHandlerAspect(pjp, method);
        return execute(pjp, method);
    }

    private Logger logger = LoggerFactory.getLogger(RecordLogAspect.class);

    @Override
    @Async
    protected Object execute(ProceedingJoinPoint pjp, Method method) throws Throwable {
        Log log = method.getAnnotation(Log.class);
        // 异常日志信息
        String actionLog = null;
        StackTraceElement[] stackTrace = null;
        // 是否执行异常
        boolean isException = false;
        // 接收时间戳
        long endTime;
        // 开始时间戳
        long operationTime = System.currentTimeMillis() / 1000;
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable throwable) {
            isException = true;
            actionLog = throwable.getMessage();
            stackTrace = throwable.getStackTrace();
            throw throwable;
        } finally {
            // 日志处理
            logHandle(pjp, method, log, actionLog, operationTime, isException, stackTrace);
        }
    }

    private void logHandle(ProceedingJoinPoint joinPoint,
                           Method method,
                           Log log,
                           String actionLog,
                           long startTime,
                           boolean isException,
                           StackTraceElement[] stackTrace) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ILogService operationLogService = SpringContextBeanService.getBean(ILogService.class);
        IOperatorService operatorService = SpringContextBeanService.getBean(IOperatorService.class);
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String authorization = request.getHeader("Authorization");
        OperatorLog operatorLog = new OperatorLog();
        if (!ComUtil.isEmpty(authorization)) {
            String userNo = JWTUtil.getUserNo(authorization);
            Map<String, Object> mso = new HashMap<>();
            mso.put("UserId", userNo);
            Operator operator = operatorService.selectByMap(mso);
            if (operator != null) {
                operatorLog.setOperatorInnerId(operator.getUserInnerId());
                operatorLog.setOperatorContent(operator.getUserName());
                operatorLog.setMessage(userNo);
            }
        }
        operatorLog.setOperatorIp(getIpAddress(request));
        operatorLog.setClassName(joinPoint.getTarget().getClass().getName());

        //   operatorLog.setCreateTime(startTime+"");
        operatorLog.setLogDescription(log.description());
        operatorLog.setModelName(log.modelName());
        operatorLog.setActionN(log.action());
        if (isException) {
            StringBuilder sb = new StringBuilder();
            sb.append(actionLog + " &#10; ");
            for (int i = 0; i < stackTrace.length; i++) {
                sb.append(stackTrace[i] + " &#10; ");
            }

            System.out.println(sb.toString() + "-------" + sb.length());

            //  operatorLog.setMessage("");
        }
        operatorLog.setMethodName(method.getName());
        operatorLog.setSucceed(isException == true ? 2 : 1);
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean isJoint = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof JSONObject) {
                JSONObject parse = (JSONObject) JSONObject.parse(args[i].toString());
                if (!ComUtil.isEmpty(parse.getString("password"))) {
                    parse.put("password", "*******");
                }
                if (!ComUtil.isEmpty(parse.getString("rePassword"))) {
                    parse.put("rePassword", "*******");
                }
                if (!ComUtil.isEmpty(parse.getString("oldPassword"))) {
                    parse.put("oldPassword", "*******");
                }
                //   operatorLog.setActionsArgs(parse.toString());
                logger.info("ActionsArgs:" + parse.toString());
            } else if (args[i] instanceof String
                || args[i] instanceof Long
                || args[i] instanceof Integer
                || args[i] instanceof Double
                || args[i] instanceof Float
                || args[i] instanceof Byte
                || args[i] instanceof Short
                || args[i] instanceof Character) {
                isJoint = true;
            } else if (args[i] instanceof String[]
                || args[i] instanceof Long[]
                || args[i] instanceof Integer[]
                || args[i] instanceof Double[]
                || args[i] instanceof Float[]
                || args[i] instanceof Byte[]
                || args[i] instanceof Short[]
                || args[i] instanceof Character[]) {
                Object[] strs = (Object[]) args[i];
                StringBuilder sbArray = new StringBuilder();
                sbArray.append("[");
                for (Object str : strs) {
                    sbArray.append(str.toString() + ",");
                }
                sbArray.deleteCharAt(sbArray.length() - 1);
                sbArray.append("]");
                //   operatorLog.setActionsArgs(sbArray.toString());
                logger.info("ActionsArgs:" + sbArray.toString());
            } else {
                continue;
            }
        }
        if (isJoint) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String key : parameterMap.keySet()) {
                String[] strings = parameterMap.get(key);
                for (String str : strings) {
                    sb.append(key + "=" + str + "&");
                }
            }

            String idNum = sb.length() - 1 >= 0 ? sb.deleteCharAt(sb.length() - 1).toString() : "";
            //   operatorLog.setActionsArgs(idNum);
            logger.info("idNum:" + JSONObject.toJSON(operatorLog) + idNum);
        }
        logger.info("执行方法信息:" + JSONObject.toJSON(operatorLog));
        operationLogService.insertOperator(operatorLog);
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip + ":" + request.getRemotePort();
    }
}
