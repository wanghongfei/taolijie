package com.fh.taolijie.controller;

import com.fh.taolijie.controller.dto.ImageDto;
import com.fh.taolijie.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 该控制器用来存取一些静态资源文件，如果图片等
 * Created by wynfrith on 15-3-31.
 */
@RequestMapping("/static")
@Controller
public class StaticController {
    private static int _500KB = 1024 * 500;// 500KB

    @Autowired
    ImageService imageService;

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
     * 上传图片options方法验证
     */
    @RequestMapping(value = "upload", method =RequestMethod.OPTIONS, produces = "application/json; charset=utf-8")
    public @ResponseBody String uploadOptions(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        return "{code:0}";
    }

    @ExceptionHandler
    @ResponseBody
    public String doException(Exception e,HttpServletRequest request) throws Exception {
        String result = null;
        
        if (e instanceof MaxUploadSizeExceededException) {
            long maxSize = ((MaxUploadSizeExceededException) e)
                    .getMaxUploadSize();
            result = "文件过大";
        }else if(e instanceof RuntimeException){
            result = "ERROR";
        }else{
            result = "ERROR";
        }
        return result;

    }

    /**
     * 用户上传图片
     * @return
     */
    @RequestMapping(value = "upload", method =RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String upload(@RequestParam MultipartFile file,
                                       HttpServletResponse response) {

        ImageDto imageDto = new ImageDto();
        response.setHeader("Access-Control-Allow-Origin", "*");

        try (InputStream inStream = file.getInputStream()) {
            // 读取byte数据
            byte[] imageByte = writeToBuffer(inStream, file.getSize());
            imageDto.setBinData(imageByte);

            String fileName = file.getOriginalFilename();
            imageDto.setFileName(fileName);
            // 写入数据库
            Integer imageId = imageService.saveImage(imageDto);
            System.out.println(imageId);
            // 返回成功信息

        } catch (IOException ex) {
            // 返回上传失败错误信息
            System.out.println("error!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        return "savepic";
    }



    private byte[] writeToBuffer(InputStream in, Long size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(size.intValue());
        byte[] buf = new byte[_500KB];

        int len = 0;
        while ( (len = in.read(buf)) != -1 ) {
            buffer.put(buf, 0, len);
        }


        return buffer.array();
    }

}
