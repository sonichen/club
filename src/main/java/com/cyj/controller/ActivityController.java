package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Activity;
import com.cyj.domain.Activity;
import com.cyj.domain.Club;
import com.cyj.domain.User;
import com.cyj.service.IActivityService;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@CrossOrigin(origins = "*")
@Api(tags = {"活动信息表"})
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IActivityService activityService;
    @ApiOperation(value = "根据状态查询活动数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态 1-成立,-1申请中,-2申请失败"),
    })
    @GetMapping("/getTotal")
    public Integer getUserCount(Integer status){
        int num=activityService.count(status);
        return  num;
    }
    /**
     * 分页返回
     * @param page
     * @param list
     * @return
     */
    @ApiOperation(value = "分页返回活动讯息 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "status", value = "状态 1-成立,-1申请中,-2申请失败 "),
            @ApiImplicitParam(name = "place", value = "地点")
    })
    @GetMapping ("/queryActivityAll")
    public JsonObject queryActivityAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
//           @RequestBody Activity activity
            String title,
            Integer clubId,
            Integer status,
            String place

    ){
        Activity activity=new Activity();

        if(title!=null){
            activity.setTitle(title);
        }
        if(clubId!=null){
            activity.setClubId(clubId);
        }
        if(status!=null){
            activity.setStatus(status);
        }
        if(place!=null){
            activity.setPlace(place);
        }
        System.out.println("activity==="+activity.getTitle());
        JsonObject object=new JsonObject();
        PageInfo<Activity> pageInfo= activityService.queryActivityAll(page,list,activity);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }



    @ApiOperation(value = "直接新增活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "place", value = "地点")
    })
    @PostMapping("/add")
    public R add(Integer clubId,
                 Date beginTime,
                 Date endTime,
                 String title,
                 String content,
                 String place,
                 String remark,
                 HttpServletRequest request){
        Activity activity=new Activity();
        if(clubId!=null){
            activity.setClubId(clubId);
        }
        if(title!=null){
            activity.setTitle(title);
        }
        if(beginTime!=null){
            activity.setBeginTime(beginTime);
        }
        if(endTime!=null){
            activity.setEndTime(endTime);
        }
        if(content!=null){
            activity.setContext(content);
        }
        if(place!=null){
            activity.setPlace(place);
        }
        if(remark!=null){
            activity.setRemark(remark);
        }
        User user= SessionUtils.getUser(request);
        activity.setCreator(user.getNumber());
        activity.setApprover(user.getNumber());
        activity.setStatus(1);
        activity.setCreateTime(new Date());
        activity.setCheckTime(new Date());
        int num=activityService.add(activity);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }

    @ApiOperation(value = "申请活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "status", value = "状态 1-成立,-1申请中,-2申请失败 "),
            @ApiImplicitParam(name = "place", value = "地点"),
            @ApiImplicitParam(name = "projecttype", value = "项目类型，前端控制一下，就定产品给的那几个"),
    })
    @PostMapping("/apply")
    public R apply(
//            @RequestBody Activity activity,
                   Integer clubId,
                   Date beginTime,
                   Date endTime,
                   String title,
                   String content,
                   String place,
                   String remark,
                   String projecttype,
                   HttpServletRequest request){
        User user= SessionUtils.getUser(request);
        Activity activity=new Activity();
        if(clubId!=null){
            activity.setClubId(clubId);
        }
        if(title!=null){
            activity.setTitle(title);
        }
        if(beginTime!=null){
            activity.setBeginTime(beginTime);
        }
        if(endTime!=null){
            activity.setEndTime(endTime);
        }
        if(content!=null){
            activity.setContext(content);
        }
        if(place!=null){
            activity.setPlace(place);
        }
        if(remark!=null){
            activity.setRemark(remark);
        }
        if(projecttype!=null){
            activity.setProjecttype(projecttype);
        }
        activity.setCreator(user.getNumber());
        activity.setStatus(-1);
        activity.setCreateTime(new Date());
        int num=activityService.add(activity);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }

    @ApiOperation(value = "审批 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "活动id【必填】"),
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "status", value = "1-成立,-1申请中,-2申请失败 【必填】")
    })
    @PostMapping("/check")
    public R check(
//            @RequestBody Activity activity,
            Integer id,
            Integer clubId,
                   Integer status,
                   HttpServletRequest request){
        Activity activity=new Activity();
        User user= SessionUtils.getUser(request);
        activity.setId(id);
        activity.setApprover(user.getNumber());
        activity.setClubId(clubId);
        activity.setStatus(status);
       if(activity.getStatus()==null){
           activity.setStatus(0);
           return R.fail("未审批");
       }
        activity.setCheckTime(new Date());
        int num=activityService.updateData(activity);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }
    @ApiOperation(value = "删除")
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            activityService.delete(Long.parseLong(id));
        }
        return R.ok();
    }

    @ApiOperation(value = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "社团id【必填】")
    })
    @PostMapping("/update")
    public R update(
//            @RequestBody Activity activity,
                    int id,
                    Integer clubId,
                    Date beginTime,
                    Date endTime,
                    String title,
                    String content,
                    String place,
                    String remark

    ){
        Activity activity=new Activity();
        activity.setId(id);
        if(clubId!=null){
            activity.setClubId(clubId);
        }
        if(title!=null){
            activity.setTitle(title);
        }
        if(beginTime!=null){
            activity.setBeginTime(beginTime);
        }
        if(endTime!=null){
            activity.setEndTime(endTime);
        }
        if(content!=null){
            activity.setContext(content);
        }
        if(place!=null){
            activity.setPlace(place);
        }
        if(remark!=null){
            activity.setRemark(remark);
        }
        activity.setReTime(new Date());
        int num=activityService.updateData(activity);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }

}
