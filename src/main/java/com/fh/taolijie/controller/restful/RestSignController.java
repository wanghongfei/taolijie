package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.PicType;
import com.fh.taolijie.dto.SignAndPolicy;
import com.fh.taolijie.service.impl.SeqService;
import com.fh.taolijie.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 9/14/15.
 */
@RestController
@RequestMapping("/api/user/sign")
public class RestSignController {
    @Autowired
    private SeqService seqService;

    /**
     * 生成七牛签名
     */
    @RequestMapping(value = "/n", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText qiniuSign(HttpServletRequest req) {
        Integer memId = SessionUtils.getCredential(req).getId();

        // 检查上次请求间隔
        // 不能少于1s
        if (!seqService.checkInterval(memId)) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);
        }

        return new ResponseText(seqService.genQiniuToken());
    }

    /**
     * 生成签名
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText genToken(@RequestParam("expiration") Long expiration,
                                 @RequestParam Integer picType,

                                 @RequestParam(value = "content-length-range", required = false) String fileRange,
                                 @RequestParam(value = "content-md5", required = false) String md5,
                                 @RequestParam(value = "content-secret", required = false) String secret,
                                 @RequestParam(value = "content-type", required = false) String contentType,
                                 @RequestParam(value = "image-width-range", required = false) String imgWidthRange,
                                 @RequestParam(value = "image-height-range", required = false) String imgHeightRange,
                                 @RequestParam(value = "notify-url", required = false) String notiUrl,
                                 @RequestParam(value = "return-url", required = false) String returnUrl,
                                 @RequestParam(value = "x-gmkerl-thumbnail", required = false) String thumbnail,
                                 @RequestParam(value = "x-gmkerl-type", required = false) String gmkerlType,
                                 @RequestParam(value = "x-gmkerl-value", required = false) String gmkerlValue,
                                 @RequestParam(value = "x-gmkerl-quality", required = false) String gmkerlQuality,
                                 @RequestParam(value = "x-gmkerl-unsharp", required = false) String gmkerlUnsharp,
                                 @RequestParam(value = "x-gmkerl-rotate", required = false) String gmkerlRotate,
                                 @RequestParam(value = "x-gmkerl-crop", required = false) String gmkerlCrop,
                                 @RequestParam(value = "x-gmkerl-exif-switch", required = false) String gmkerlExif,
                                 @RequestParam(value = "ext-param", required = false) String extParam,
                                 @RequestParam(value = "allow-file-type", required = false) String fileType,
                                 HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        // 检查上次请求间隔
        // 不能少于2s
        if (!seqService.checkInterval(credential.getId())) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);
        }

        Map<String, Object> parmMap = new HashMap<>(30);
        parmMap.put("bucket", "taolijie-pic");
        parmMap.put("expiration", expiration);
        parmMap.put("content-length-range", fileRange);
        parmMap.put("content-md5", md5);

        parmMap.put("content-secret", secret);
        parmMap.put("content-type", contentType);

        parmMap.put("image-width-range", imgWidthRange);
        parmMap.put("image-height-range", imgHeightRange);
        parmMap.put("notify-url", notiUrl);
        parmMap.put("return-url", returnUrl);

        parmMap.put("x-gmkerl-thumbnail", thumbnail);
        parmMap.put("x-gmkerl-type", gmkerlType);
        parmMap.put("x-gmkerl-value", gmkerlValue);
        parmMap.put("x-gmkerl-quality", gmkerlQuality);
        parmMap.put("x-gmkerl-unsharp", gmkerlUnsharp);
        parmMap.put("x-gmkerl-rotate", gmkerlRotate);
        parmMap.put("x-gmkerl-crop", gmkerlCrop);
        parmMap.put("x-gmkerl-exif-switch", gmkerlExif);

        parmMap.put("ext-param", extParam);
        parmMap.put("allow-file-type", fileType);


        // 将状态字符串转换成enum
        PicType pt = PicType.fromCode(picType);
        if (null == pt) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 生成key名
        String key = seqService.genKey(pt);
        parmMap.put("save-key", key);

        // 签名
        SignAndPolicy sap = new SignAndPolicy();
        sap.policy = UpYunUtils.genPolicy(parmMap);
        sap.sign = UpYunUtils.sign(sap.policy);
        sap.saveKey = key;

        return new ResponseText(sap);
    }


}
