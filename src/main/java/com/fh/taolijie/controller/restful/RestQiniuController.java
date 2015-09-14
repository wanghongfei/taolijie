package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.QiniuUtils;
import com.qiniu.api.auth.AuthException;
import org.apache.commons.codec.EncoderException;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 9/14/15.
 */
@RestController
@RequestMapping("/api/qn")
public class RestQiniuController {

    /**
     * 根据key生成上传token
     * @param key
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText genToken(@RequestParam String key) {
        String token = null;
        try {
            token = QiniuUtils.genToken(key);
        } catch (AuthException e) {
            e.printStackTrace();

            // 记录到日志文件中
            LogUtils.logException(e);
            return new ResponseText(ErrorCode.FAILED);

        } catch (JSONException e) {
            e.printStackTrace();

            LogUtils.logException(e);
            return new ResponseText(ErrorCode.FAILED);
        }

        return new ResponseText(token);
    }

    /**
     * 生成资源URL
     * @param key
     * @return
     */
    @RequestMapping(value = "/url", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText genUrl(@RequestParam String key) {
        String url = null;
        try {
            url = QiniuUtils.genUrl(QiniuUtils.SPACE_NAME, key);
        } catch (EncoderException e) {
            e.printStackTrace();

            LogUtils.logException(e);
            return new ResponseText(ErrorCode.FAILED);

        } catch (AuthException e) {
            e.printStackTrace();

            LogUtils.logException(e);
            return new ResponseText(ErrorCode.FAILED);
        }

        return new ResponseText(url);
    }
}
