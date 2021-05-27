package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
import com.cyj.domain.Member;
import com.cyj.domain.User;
import com.cyj.service.*;
import com.cyj.util.JsonObject;
import com.cyj.util.R;
import com.cyj.util.SessionUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 社团信息表 前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"社团信息表"})
@RestController
@RequestMapping("/club")
public class ClubController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IClubService clubService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IActivityService activityService;

    @Resource
    private INoticeService noticeService;
    @Resource
    private IFinanceService financeService;

    @ApiOperation(value = "根据状态的社团数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "社团状态 1-成立，-1注销,2-申请成立，-2申请注销"),
    })
    @GetMapping("/getTotal")
    public Integer getUserCount(Integer status){
        int num=clubService.count(status);
        return  num;
    }


    @ApiOperation(value = "分页返回所有社团【必填：无】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "社团名"),
            @ApiImplicitParam(name = "status", value = "社团状态 1-成立，-1注销,2-申请成立，-2申请注销"),
            @ApiImplicitParam(name = "type", value = "社团类型"),
    })
    @GetMapping ("/queryClubAll")
    public JsonObject queryClubAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
//            @RequestBody  Club club,
            String name,
            Integer status,
            Integer type,
            HttpServletRequest request
            ){
        User user= SessionUtils.getUser(request);

        Club club=new Club();
        if(name!=null){
            club.setName(name);
        }
        System.out.println("status=="+status);
        if(status!=null){
            club.setStatus(status);
        }
        if(type!=null){
            club.setType(type);
        }


        JsonObject object=new JsonObject();
        PageInfo<Club> pageInfo= clubService.queryClubAll(page,list,club);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }

    @ApiOperation(value = "直接新增社团信息表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "社团类型【必填 】"),
            @ApiImplicitParam(name = "name", value = "社团名【必填 】"),
            @ApiImplicitParam(name = "content", value = "简介"),
            @ApiImplicitParam(name = "remark", value = "备注"),
            @ApiImplicitParam(name = "chief", value = "社长学号"),
            @ApiImplicitParam(name = "scope", value = "服务范围【前端控制一下给两个选择：校级社团/ 院级社团】"),
            @ApiImplicitParam(name = "college", value = "所属院系"),
    })
    @PostMapping("/add")
    public R add(
//            @RequestBody Club club,
            @RequestParam  String name,
            Integer type,
            String content,
            String remark,
            String cheif,
            String scope,
            String college,
            HttpServletRequest request){
        Club club=new Club();
        if(cheif!=null){
            club.setChief(cheif);
        }
        if(type!=null){
            club.setType(type);
        }
        if(name!=null){
            club.setName(name);
        }
        if(content!=null){
            club.setContent(content);
        }
        if(remark!=null){
            club.setRemark(remark);
        }
        if(scope!=null){
            club.setScope(scope);
        }
        if(college!=null){
            club.setCollege(college);
        }
        User user=SessionUtils.getUser(request);
        club.setApprover(user.getNumber());
        club.setApplTime(new Date());
        club.setCreateTime(new Date());
        club.setStatus(1);
        club.setCount(0);
        int num=clubService.add(club);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }
    @ApiOperation(value = "申请创建社团")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "社团名【必填】"),
            @ApiImplicitParam(name = "content", value = "简介"),
            @ApiImplicitParam(name = "remark", value = "备注"),
    })
    @PostMapping("/apply")
    public R apply(
//            @RequestBody Club club,
                   HttpServletRequest request,
                   String name,
                   String content,
                   String remark
    ){
        Club club=new Club();
        if(name!=null){
            club.setName(name);
        }
        if(content!=null){
            club.setContent(content);
        }
        if(remark!=null){
            club.setRemark(remark);
        }
        User user=SessionUtils.getUser(request);
        club.setChief(user.getNumber());
        club.setApplTime(new Date());
        club.setStatus(2);
        club.setCount(1);
        //对于社长
        Member member=new Member();
        member.setStudentId(user.getNumber());
        member.setStatus(-1);
        member.setType(2);
        member.setCreateTime(new Date());
        memberService.add(member);
        int num=clubService.add(club);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }

    @ApiOperation(value = "审批/申请 社团状态")
    @PostMapping("/check")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "status", value = "1-成立，-1注销,2-申请成立，-2申请注销[必填]"),
    })
    public R checkAdd(
//            @RequestBody Club club,
            int clubId,
            Integer status,
            HttpServletRequest request){
        Club club=new Club();
        club.setId(clubId);
        User user=SessionUtils.getUser(request);
        club.setApprover(user.getNumber());
        club.setStatus(status);
        club.setCreateTime(new Date());

       if(status==1){
           //对于社长
           String cheif=club.getChief();
           Member member =new Member();
           member.setClubId(clubId);
           member.setStudentId(cheif);
            member.setIntoTime(new Date());
             member.setChecker(user.getNumber());
           member.setStatus(-1);
           member.setType(2);
           memberService.updateData(member);
       }
        int num=clubService.updateData(club);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("审批失败");
        }
    }

    @ApiOperation(value = "直接删除社团信息表")
    @PostMapping("/deleteByIds")
    public R delete(String ids){

        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            //删除活动表
            activityService.deleteByClubId(Integer.parseInt(id));
            //删除公告
            noticeService.deleteByClubId(Integer.parseInt(id));
            //删除成员
            memberService.deleteByClubId(Integer.parseInt(id));
            //删除财务
            financeService.deleteByClubId(Integer.parseInt(id));
            //删除社团
            clubService.delete(Long.parseLong(id));
        }
        return R.ok();
    }

    @ApiOperation(value = "更新社团信息表")
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "name", value = "社团名"),
            @ApiImplicitParam(name = "content", value = "简介"),
            @ApiImplicitParam(name = "remark", value = "备注"),
    })
    public R update(
//            @RequestBody Club club,
            Integer clubId,
            String name,
            String content,
            String remark
    ){
        Club club=new Club();
        club.setId(clubId);
        if(name!=null){
            club.setName(name);
        }
        if(content!=null){
            club.setContent(content);
        }
        if(remark!=null){
            club.setRemark(remark);
        }

        int num=0;
        num= clubService.updateData(club);

        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }

    @ApiOperation(value = "返回各个社团的成员数量,输入社团id")
    @PostMapping("count")
    public JsonObject countMember(
//            @RequestBody Club club
            int clubId
    ){
        JsonObject object=new JsonObject();
        int num=clubService.countClubMember(new Club().setId(clubId));
        if(num>=0){
            object.setCode(1);
            object.setMsg("查询成功");
            List data=new ArrayList();
            data.add(num);
            object.setData(data);
            return object;
        }else{
//          return   R.fail("");
            object.setMsg("查询失败");
            object.setCode(-1);
            return object;
        }
    }


}
