package com.dhsr.wx.cp.config;

import com.dhsr.wx.cp.annotation.AccessLimit;
import com.dhsr.wx.cp.annotation.Log;
import com.dhsr.wx.cp.annotation.ParamXssPass;
import com.dhsr.wx.cp.annotation.ValidationParam;
import com.dhsr.wx.cp.aspect.*;
import com.dhsr.wx.cp.utils.ComUtil;
import com.dhsr.wx.cp.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 切面:防止xss攻击 记录log  参数验证
 *
 * @author liuxiaogang
 */
@Aspect
@Configuration
public class ControllerAspect {

    @Pointcut("execution(* com.dhsr.wx.cp.controller..*(..))  ")
    public void aspect() {
    }

    @Around(value = "aspect()")
    public Object validationPoint(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===============================================================================");
        Method method = currentMethod(pjp, pjp.getSignature().getName());
        //创建被装饰者
        AspectApiImpl aspectApi = new AspectApiImpl();
        //是否需要验证参数
        if (!ComUtil.isEmpty(StringUtil.getMethodAnnotationOne(method, ValidationParam.class.getSimpleName()))) {
            new ValidationParamAspect(aspectApi).doHandlerAspect(pjp, method);
        }
        //是否需要限流
        if (method.isAnnotationPresent(AccessLimit.class)) {
            new AccessLimitAspect(aspectApi).doHandlerAspect(pjp, method);
        }
        //是否需要拦截xss攻击
        if (method.isAnnotationPresent(ParamXssPass.class)) {
            new ParamXssPassAspect(aspectApi).doHandlerAspect(pjp, method);
        }
        //是否需要记录日志
        if (method.isAnnotationPresent(Log.class)) {
            return new RecordLogAspect(aspectApi).doHandlerAspect(pjp, method);
        }
        return pjp.proceed(pjp.getArgs());
    }

    /**
     * 获取目标类的所有方法，找到当前要执行的方法
     */
    private Method currentMethod(ProceedingJoinPoint joinPoint, String methodName) {
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }


}
