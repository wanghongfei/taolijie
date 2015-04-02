package com.fh.taolijie.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该控制器用来存取一些静态资源文件，如果图片等
 * Created by wynfrith on 15-3-31.
 */
@RequestMapping("/static")
@Controller
public class StaticController {


    /**
     * 获取图片
     * 根据格式的不同会转发到对应格式的控制器
     * 如果格式不支持返回错误的json信息
     *
     * @param imageStr 图片名字 imageStr = id+ext
     *
     * @return
     */
    @RequestMapping(value = "/images",method = RequestMethod.GET)
    public String getPic(@PathVariable String imageStr){
        return "";
    }


    @RequestMapping(value = "/images/jpeg",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    public String getJPEG(){
        return "";
    }
    @RequestMapping(value = "/images/png",method = RequestMethod.GET,produces = MediaType.IMAGE_PNG_VALUE)
    public String getPNG(){
        return "";
    }
    @RequestMapping(value = "/images/gif",method = RequestMethod.GET,produces = MediaType.IMAGE_GIF_VALUE)
    public String getGIF(){
        return "";
    }


    /**
     * 用户上传图片
     * @return
     */
    @RequestMapping(value = "upload")
    public @ResponseBody String upload(){
        return "savepic";
    }


}
