package com.ahnu.gis.aop;

import com.ahnu.gis.annotations.SelectCache;
import com.ahnu.gis.util.SystemConstantUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Aspect
@Component
public class TemperatureAOP {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureAOP.class);

    // TODO: 2018/7/15 需要配置jedis连接池
    @Autowired
    private Jedis jedis;

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.ahnu.gis.annotations.SelectCache)")
    public void selectCachePointcut() {

    }

    @Around("selectCachePointcut()&&@annotation(selectCache)")
    public Object aroundSelectCache(ProceedingJoinPoint joinPoint, SelectCache selectCache) throws Throwable {
        LOGGER.info("环绕通知目标方法名: {}, 获取注解value值: {}", joinPoint.getSignature().getName(), selectCache.value());
        //获取目标Class类型
        Class<?> clazz = joinPoint.getTarget().getClass();
        //获取环绕通知方法名
        String methodName = joinPoint.getSignature().getName();
        //获取注解字段value值
        String annotationValue = selectCache.value();
        Object[] args = joinPoint.getArgs();
        for (Object argItem : args) {
            if (argItem instanceof Integer) {
                Integer id = (Integer) argItem;
                Method declaredMethod = clazz.getDeclaredMethod(methodName, Integer.class);
                //获取方法返回Class类型
                Class<?> returnType = declaredMethod.getReturnType();
                //从缓存中获取值
                String str = jedis.get(SystemConstantUtil.TYPE_DATA + "_" + annotationValue + "_" + id);
                if (StringUtils.isNotBlank(str)) {
                    LOGGER.info("从缓存中获取值");
                    Object readValue = objectMapper.readValue(str, returnType);
                    return readValue;
                } else {
                    LOGGER.info("未从注解中获取值，将执行数据库查询操作");
                    Object proceed = joinPoint.proceed();
                    String proceedStr = objectMapper.writeValueAsString(proceed);
                    LOGGER.info("查询结果存入缓存中");
                    jedis.setex(SystemConstantUtil.TYPE_DATA + "_" + annotationValue + "_" + id, 86400, proceedStr);
                    return proceed;
                }
            }
        }

        return null;
    }
}
