package com.fh.taolijie.controller.restful.recommend;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RecoType;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.RecoService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by whf on 10/15/15.
 */
@RestController
@RequestMapping("/api/re")
public class RestRecoCtr {
    @Autowired
    private RecoService reService;

    @Autowired
    private QuestService questService;

    @Autowired
    private JobPostService jobService;

    @Autowired
    private ShPostService shService;

    /**
     * 查询推荐帖子
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText myApply(HttpServletRequest req,
                                @RequestParam() Integer type,
                                @RequestParam(defaultValue = "0") int pn,
                                @RequestParam(defaultValue = Constants.PAGE_CAP) int ps) {

        pn = PageUtils.getFirstResult(pn, ps);

        RecoPostModel cmd = new RecoPostModel(pn, ps);
        RecoType rt = RecoType.fromCode(type);
        if (null == rt) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }
        cmd.setType(rt.code());
        cmd.setValid(true);

        // 查询推荐索引表
        ListResult<RecoPostModel> postLr = reService.findBy(cmd);
        // 取出帖子id
        List<Integer> idList = postLr.getList().stream()
                .map(RecoPostModel::getPostId)
                .collect(Collectors.toList());

        // 根据不同的帖子类型调用不同的service方法
        switch (rt) {
            case QUEST:
                List<QuestModel> list = questService.findInBatch(idList);
                ListResult<QuestModel> questLr = new ListResult<>(list, postLr.getResultCount());
                return new ResponseText(questLr);

            case JOB:
                ListResult<JobPostModel> jobLr = jobService.getInBatch(idList);
                return new ResponseText(jobLr);

            case SH:
                ListResult<SHPostModel> shLr = shService.getInBatch(idList);
                return new ResponseText(shLr);


            default:
        }

        return new ResponseText();

    }
}
