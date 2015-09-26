package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 8/26/15.
 */
@RestController
@RequestMapping("/api/u/sh")
public class RestShUController {
    @Autowired
    ShPostService shService;

    /**
     * 是否赞美
     * @return
     */
    @RequestMapping(value = "/fav/{shId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText isFav(@PathVariable("shId") Integer shId,
                              HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        boolean result = shService.isPostFavorite(credential.getId(), shId);

        return new ResponseText(result);
    }

    /**
     * 查询收藏列表
     * @param shId
     * @param req
     * @return
     */
    @RequestMapping(value = "/favlist", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText favList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<SHPostModel> lr = shService.getFavoritePost(credential.getId(), pageNumber, pageSize);

        return new ResponseText(lr);
    }

    /**
     * 更新图片URL
     * @return
     */
    @RequestMapping(value = "/{shId}/pic", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updatePic(@PathVariable("shId") Integer shId,
                                  @RequestParam String picAddr,
                                  HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 判断帖子是不是自己发的
        SHPostModel sh = shService.findPost(shId);
        if (!sh.getMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 更新URL
        SHPostModel example = new SHPostModel();
        example.setId(shId);
        example.setPicturePath(picAddr);
        shService.updatePost(shId, example);

        return new ResponseText();
    }
}
