package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Club;
import com.cyj.domain.Member;
import com.cyj.domain.Member;
import com.cyj.domain.User;
import com.cyj.service.IClubService;
import com.cyj.service.IMemberService;
import com.cyj.util.JsonObject;
import com.cyj.util.R;
import com.cyj.util.SessionUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * 社团成员表 前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"社团成员表"})
@RestController
@RequestMapping("/member")
public class MemberController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IMemberService memberService;
    @Resource
    private IClubService clubService;
    @ApiOperation(value = "查询社团成员【传入社团id】|查询我的社团【传入学生id和社团状态1，得到当前加入的社团】")
    @PostMapping ("/queryMemberAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态 1-成立，-1申请中，-2申请失败"),
            @ApiImplicitParam(name = "type", value = "学生类型【普通成员-1，社长-2】"),
            @ApiImplicitParam(name = "clubId", value = "社团id"),
            @ApiImplicitParam(name = "studentId", value = "学生id")
    })
    public JsonObject queryMemberAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
//            @RequestBody Member member
            Integer status,
            Integer type,
            Integer clubId,
            String studentId

    ){
        Member member=new Member();
       if(  status     !=null){
           member.setStatus(status);
       }
       if(  type       !=null){
           member.setType(type);
       }
       if(  clubId     !=null){
           member.setClubId(clubId);
       }
       if( studentId   !=null){
           member.setStudentId(studentId);
       }

        JsonObject object=new JsonObject();
        PageInfo<Member> pageInfo= memberService.queryMemberAll(page,list,member);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }


    /**
     * 0-申请
     * 1-加入
     * @param
     * @return
     */
    @ApiOperation(value = "申请加入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id【必填】"),
            @ApiImplicitParam(name = "remark",value = "备注")
    })
    @PostMapping("/add")
    public R add(
//            @RequestBody Member member,
            String remark,
                 Integer clubId,
                 HttpServletRequest request){
        Member member=new Member();
        if(  clubId     !=null){
            member.setClubId(clubId);
        }
        if(remark!=null){
            member.setRemark(remark);
        }
        User user= SessionUtils.getUser(request);
        String studentId=user.getNumber();
        member.setStudentId(studentId);
        member.setCreateTime(new Date());
        member.setStatus(-1);
        member.setType(1);
        int num=memberService.add(member);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }
    @ApiOperation(value = "设置社长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成员id【必填】"),
    })
    @PostMapping("/setLeader")
    public R setLeader(Integer id,
                       HttpServletRequest request){
        Member member=new Member();
        member.setId(id);
        member.setType(2);
        member.setRwTime(new Date());
        int num=memberService.updateData(member);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }
    @ApiOperation(value = "撤销社长")
    @PostMapping("/deleteLeader")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成员id【必填】"),
    })
    public R deleteLeader(Integer id,HttpServletRequest request){
        Member member=new Member();
        member.setId(id);
        member.setType(1);
        member.setRwTime(new Date());
        int num=memberService.updateData(member);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }
    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成员id【必填】"),
            @ApiImplicitParam(name = "status",value = "1-成立，-1申请中，-2申请失败【必填】")
    })
    @PostMapping("/check")
    public R checkAdd(Integer id,
                        Integer status,
                      HttpServletRequest request){
//        member=memberService.findByStudentId();
        Member member=new Member();
        member.setId(id);
        member.setStatus(status);
        System.out.println("id=="+member.getId());

        User user= SessionUtils.getUser(request);
        member.setChecker(user.getNumber());
        member.setIntoTime(new Date());
        member.setRwTime(new Date());
        member.setStatus(1);
        Club club=new Club();
        club.setId(member.getClubId());
        club.setName(member.getClubName());
        club.setCount(clubService.countClubMember(club)+1);

        int num=memberService.updateData(member);


        if(num>0){
            int count=clubService.updateCount(club);
            return R.ok();
        }else {
            int count=clubService.updateCount(club);
            return R.fail("修改失败");
        }
    }

    @ApiOperation(value = "删除成员")
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            int clubId=memberService.findById(Long.parseLong(id)).getClubId();
            System.out.println("社团id"+clubId);
            clubService.updateCount(new Club().setId(clubId));
            memberService.delete(Long.parseLong(id));
        }
        return R.ok();
    }
    @ApiOperation(value = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成员id【必填】"),
            @ApiImplicitParam(name = "remark",value = "备注")
    })
    @PostMapping("/update")
    public R update(
            Integer id,
           String remark
    ){
        Member member=new Member();
        member.setId(id);
        member.setRemark(remark);
        member.setRwTime(new Date());
        int num=memberService.updateData(member);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }


}
