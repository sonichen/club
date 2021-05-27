package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Club;
import com.cyj.domain.Notice;
import com.cyj.domain.Notice;
import com.cyj.domain.User;
import com.cyj.service.IClubService;
import com.cyj.service.INoticeService;
import com.cyj.service.INoticeService;
import com.cyj.util.JsonObject;
import com.cyj.util.R;
import com.cyj.util.SessionUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.prefs.NodeChangeEvent;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"公告"})
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Resource
    private INoticeService noticeService;
    @Resource
    private IClubService clubService;

    @ApiOperation(value = "查询所有公告|查询我的社团公告")
    @GetMapping ("/queryNoticeAll")
    public JsonObject queryNoticeAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
            String title,
            String content,
            String clubName
//            @RequestBody Notice notice
    ){
        Notice notice=new Notice();
        if(title!=null){
            notice.setTitle(title);
        }
        if(content!=null){
            notice.setContent(content);
        }
        if(clubName!=null){
            int id=clubService.findIdByName(clubName);
            notice.setClubId(id);
        }
        System.out.println("公告测试="+notice);
        JsonObject object=new JsonObject();
        PageInfo<Notice> pageInfo= noticeService.queryNoticeAll(page,list,notice);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }
    @ApiOperation(value = "新增,如果是社长，必须传入对于的社团id")
    @PostMapping("/add")
    public R add(
//            @RequestBody Notice notice,
            String title,
            String content,
            Integer clubId,
            String remark,
            HttpServletRequest request){
        Notice notice=new Notice();
        if(remark!=null){
            notice.setRemark(remark);
        }
        if(title!=null){
            notice.setTitle(title);
        }
        if(content!=null){
            notice.setContent(content);
        }
        User user= SessionUtils.getUser(request);
        if(user.getType()==0){
            notice.setClubId(clubId);
        }
        notice.setCreator(user.getNumber());

        notice.setCreateTime(new Date());
        int num=noticeService.add(notice);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }

    @ApiOperation(value = "删除")
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            noticeService.delete(Long.parseLong(id));
        }
        return R.ok();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public R update(
//            @RequestBody Notice notice
            Integer id,
            String title,
            String content,
            Integer clubId,
            String remark
    ){
        Notice notice=new Notice();
        if(title!=null){
            notice.setTitle(title);
        }
        if(content!=null){
            notice.setContent(content);
        }
        if(clubId!=null){
            notice.setClubId(clubId);
        }
        if(id!=null){
            notice.setId(id);
        }
        if(remark!=null){
            notice.setRemark(remark);
        }
        notice.setRwTime(new Date());
        int num=noticeService.updateData(notice);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }

}
