package com.portal.common.web.controller;

import com.portal.common.utils.Constant;
import com.portal.common.web.editor.DateEditor;
import com.portal.common.web.editor.StringEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 控制器支持类
 *
 * @author dcp
 */
@RestController
@RequestMapping("/baseController")
public abstract class BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     *
     * @param binder the binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new StringEditor());
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new DateEditor());
    }


    /**
     * 下载文件
     * @author	<a href="mailto:chenbin@iwangding.com">陈彬</a>
     * @param response
     * @param filePath 要下载的文件相对路径
     * @param fileName 文件名字
     */
    @GetMapping("download")
    public void download(HttpServletResponse response, String filePath, String fileName){
        response.reset();
        //response.setHeader("Content-Disposition", "attachment; filename=dict.txt");
        //response.setContentType("application/x-download; charset=utf-8");
        File file=new File(Constant.getConfig("fileUploadDir")+"/"+filePath);
        if(file.exists()){
            InputStream is = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            BufferedOutputStream bos = null;
            try {
                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                os = response.getOutputStream();
                bos = new BufferedOutputStream(os);
                response.setContentType("application/vnd.ms-excel");//设置response内容的类型
                response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));//设置头部信息
                int len = 0;
                byte[] buffer = new byte[2048];
                //开始向网络传输文件流
                while ((len = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
            } catch (Exception e) {
                logger.error("文件下载出错："+e.getMessage(), e);
            } finally {
                if(is!=null){
                    try {
                        is.close();
                        is = null;
                    } catch (IOException e) {
                        logger.error("关闭输入流出错："+e.getMessage(), e);
                    }
                }
                if(bis!=null){
                    try {
                        bis.close();
                        bis = null;
                    } catch (IOException e) {
                        logger.error("关闭输入流出错："+e.getMessage(), e);
                    }
                }
                if(os!=null){
                    try {
                        os.close();
                        os = null;
                    } catch (IOException e) {
                        logger.error("关闭输出流出错："+e.getMessage(), e);
                    }
                }
                if(bos!=null){
                    try {
                        bos.close();
                        bos = null;
                    } catch (IOException e) {
                        logger.error("关闭输出流出错："+e.getMessage(), e);
                    }
                }
            }
        }else{
            logger.error("文件不存在");
        }
    }

}
