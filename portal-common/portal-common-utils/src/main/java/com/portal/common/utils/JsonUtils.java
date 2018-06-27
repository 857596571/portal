package com.portal.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mzlion.core.json.TypeRef;
import com.mzlion.core.lang.ArrayUtils;
import com.mzlion.core.lang.Assert;
import com.mzlion.core.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON格式序列化和反序列化处理
 */
public class JsonUtils {
    private JsonUtils() {
        throw new UnsupportedOperationException();
    }
    public static <T> String toJson(T value) {
        return toJson(value, true, new String[0]);
    }

    public static <T> String toJson(T value, String... propertyNames) {
        return toJson(value, true, propertyNames);
    }

    public static <T> String toJson(T value, boolean toUnicode, final String... propertyNames) {
        if(value == null) {
            return null;
        } else {
            List<SerializerFeature> serializerFeatureList = new ArrayList();
            serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
            if(toUnicode) {
                serializerFeatureList.add(SerializerFeature.BrowserCompatible);
            }

            SerializerFeature[] serializerFeatures = new SerializerFeature[serializerFeatureList.size()];
            serializerFeatureList.toArray(serializerFeatures);
            return ArrayUtils.isEmpty(propertyNames)?JSON.toJSONString(value, serializerFeatures):JSON.toJSONString(value, new PropertyFilter() {
                public boolean apply(Object object, String name, Object value) {
                    return !ArrayUtils.containsElement(propertyNames, name);
                }
            }, serializerFeatures);
        }
    }
    public static <T> String toJson(T value, boolean toUnicode, String dataFormat) {
        if(value == null) {
            return null;
        } else {
            List<SerializerFeature> serializerFeatureList = new ArrayList();
            serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
            if(toUnicode) {
                serializerFeatureList.add(SerializerFeature.BrowserCompatible);
            }

            SerializerFeature[] serializerFeatures = new SerializerFeature[serializerFeatureList.size()];
            serializerFeatureList.toArray(serializerFeatures);
            return StringUtils.isEmpty(dataFormat)?JSON.toJSONString(value, serializerFeatures):JSON.toJSONStringWithDateFormat(value, dataFormat, serializerFeatures );
        }
    }
    public static String toJson(Object value, final Map<Class<?>, List<String>> classOfProps) {
        return JSON.toJSONString(value, new PropertyFilter() {
            public boolean apply(Object object, String name, Object value) {
                List<String> props = (List)classOfProps.get(object.getClass());
                return !props.contains(name);
            }
        }, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible});
    }

    public static String toJson(Object value, SerializerFeature... features) {
        Assert.notNull(features, "Array SerializerFeature is null or empty.");
        return JSON.toJSONString(value, features);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T fromJson(String json, TypeRef<T> typeRef) {
        return JSON.parseObject(json, typeRef.getType(), new Feature[0]);
    }

    public static void println(Object value) {
        System.out.println(toJson(value, new SerializerFeature[]{SerializerFeature.PrettyFormat, SerializerFeature.BrowserCompatible}));
    }
}

