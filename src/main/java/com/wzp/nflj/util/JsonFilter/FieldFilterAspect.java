package com.wzp.nflj.util.JsonFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.wzp.nflj.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/9/1 15:59
 */
@Aspect
@Component
public class FieldFilterAspect {

    @Pointcut("@annotation(JsonFilter)||@annotation(JsonFilters)")
    public void fieldFilterAspect() {
    }

    @Around("fieldFilterAspect()")
    public Result around(ProceedingJoinPoint pjp) throws Throwable {
        Result result = (Result) pjp.proceed();
        if (result.getCode() != 0) {
            return result;
        }
        Object object = result.getResult();
        if (null == object) {
            return result;
        }
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        JsonFilter jsonFilter = method.getAnnotation(JsonFilter.class);
        if (jsonFilter == null) {
            JsonFilters jsonFilters = method.getAnnotation(JsonFilters.class);
            if (jsonFilters == null) {
                return result;
            } else {
                List<SerializeFilter> serializeFilters = getSerializeFilterList(jsonFilters);
                result.setResult(toJSONString(object, serializeFilters));
            }
        } else {
            SerializeFilter serializeFilter = getSerializeFilter(jsonFilter);
            result.setResult(toJSONString(object, serializeFilter));
        }
        return result;
    }


    private SerializeFilter getSerializeFilter(JsonFilter jsonFilter) {
        if (jsonFilter.includes().length == 0) {
            if (jsonFilter.excludes().length == 0) {
                return null;
            } else {
                PropertyFilter propertyFilter = new PropertyFilter() {
                    @Override
                    public boolean apply(Object object, String name, Object value) {
                        if (Arrays.stream(jsonFilter.excludes()).anyMatch(name::equals) && jsonFilter.target().isInstance(object)) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };
                return propertyFilter;
            }
        }
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(jsonFilter.target(), jsonFilter.includes());
        return simplePropertyPreFilter;
    }

    private List<SerializeFilter> getSerializeFilterList(JsonFilters jsonFilters) {
        if (jsonFilters.value() == null || jsonFilters.value().length == 0) {
            return null;
        }
        List<SerializeFilter> list = new ArrayList<>();
        for (JsonFilter jsonFilter : jsonFilters.value()) {
            SerializeFilter serializeFilter = getSerializeFilter(jsonFilter);
            list.add(serializeFilter);
        }
        return list;
    }

    private Object toJSONString(Object object, List<SerializeFilter> serializeFilters) {
        SerializeFilter[] filters = serializeFilters.toArray(new SerializeFilter[serializeFilters.size()]);
        return JSON.parse(JSONObject.toJSONString(object, filters, SerializerFeature.WriteMapNullValue));
    }

    private Object toJSONString(Object object, SerializeFilter serializeFilter) {
        return JSON.parse(JSONObject.toJSONString(object, serializeFilter, SerializerFeature.WriteMapNullValue));
    }

}
