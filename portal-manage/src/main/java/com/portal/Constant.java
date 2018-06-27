package com.portal;

import com.google.common.collect.Maps;
import com.portal.common.utils.PropertiesLoader;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 常量
 * 
 */
public class Constant {


    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("common.properties");

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (StrUtil.isEmpty(value)){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String resourcesPaths, String key) {
        PropertiesLoader loader = new PropertiesLoader(resourcesPaths);
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

}
