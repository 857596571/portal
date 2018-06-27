package com.portal.common.web.util;

import com.portal.common.utils.exception.SystemException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Web 工具类
 *
 * @author dcp
 */
public final class WebUtils {

    /**
     * 文件临时存储路径
     */
    private static final String TEMP_FILE_PATH = "/tmp";

    private WebUtils() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 文件类型转换
     *
     * @param file MultipartFile
     * @return File file
     */
    public static File transfer(MultipartFile file) {
        File upFile = new File(new File(TEMP_FILE_PATH), file.getOriginalFilename());
        try {
            file.transferTo(upFile);
        } catch (IllegalStateException | IOException ex) {
            throw new SystemException(ex);
        }
        return upFile;
    }



    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new WafRequestWrapper(request);
    }

    public static String getIp(){
        return getRequest().getRemoteHost();
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request =getRequest();
        Enumeration enums = request.getParameterNames();
        while ( enums.hasMoreElements()){
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

}
