package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Finance;
import com.cyj.domain.Club;
import com.cyj.domain.Finance;
import com.cyj.domain.User;
import com.cyj.service.IFinanceService;
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
 * 财务项目表 前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"财务项目表"})
@RestController
@RequestMapping("/finance")
public class FinanceController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IFinanceService financeService;


    @GetMapping ("/queryFinanceAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "status", value = "状态 1-成立,-1申请中,-2申请失败 "),
            @ApiImplicitParam(name = "creator", value = "申请人"),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    public JsonObject queryFinanceAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
//            @RequestBody Finance finance
            String title,
            Integer status,
            Integer clubId,
            String remark,
            String creator
    ){
        Finance finance=new Finance();
        if( title   !=null){
            finance.setTitle(title);
        }
        if( status!=null){
            finance.setStatus(status);
        }
        if( clubId!=null){
            finance.setClubId(clubId);
        }
        if( remark!=null){
            finance.setRemark(remark);
        }
        if( creator!=null){
            finance.setCreator(creator);
        }

        JsonObject object=new JsonObject();
        PageInfo<Finance> pageInfo= financeService.queryFinanceAll(page,list,finance);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }
    @ApiOperation(value = "直接新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "money", value = "花费 "),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    @PostMapping("/add")
    public R add(
//            @RequestBody Finance finance,
            String title,
            Double money,
            Integer clubId,
            String remark,
            HttpServletRequest request){
        User user= SessionUtils.getUser(request);
        Finance finance=new Finance();
        if( title  !=null){
            finance.setTitle(title);
        }
        if( money!=null){
            finance.setMoney(money);
        }
        if( clubId!=null){
            finance.setClubId(clubId);
        }
        if( remark!=null){
            finance.setRemark(remark);
        }
        finance.setApprover(user.getNumber());
        finance.setCreator(user.getNumber());
        finance.setStatus(1);
        finance.setCreateTime(new Date());
        finance.setCheckTime(new Date());
        int num=financeService.add(finance);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }
    @ApiOperation(value = "申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clubId", value = "社团id"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "money", value = "花费 "),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    @PostMapping("/apply")
    public R apply(
//            @RequestBody Finance finance,
            String title,
            Double money,
            Integer clubId,
            String remark,
                   HttpServletRequest request){
        Finance finance=new Finance();
        if( title   !=null){
            finance.setTitle(title);
        }
        if( money!=null){
            finance.setMoney(money);
        }
        if( clubId!=null){
            finance.setClubId(clubId);
        }
        if( remark!=null){
            finance.setRemark(remark);
        }

        User user= SessionUtils.getUser(request);
        finance.setCreator(user.getNumber());
        finance.setStatus(-1);
        finance.setCreateTime(new Date());
        int num=financeService.add(finance);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }

    @ApiOperation(value = "审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "财务信息id【必填】"),
            @ApiImplicitParam(name = "status", value = " 1-成立,-1申请中,-2申请失败 "),
    })
    @PostMapping("/check")
    public R check(
//            @RequestBody Finance finance,
            Integer id,
                   Integer status,
                   HttpServletRequest request){
        Finance finance=new Finance();
        finance.setId(id);
        finance.setStatus(status);
        User user= SessionUtils.getUser(request);
        finance.setApprover(user.getNumber());

        finance.setCheckTime(new Date());
        int num=financeService.updateData(finance);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }
    @ApiOperation(value = "删除")
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        System.out.println("ids==="+ids);
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            financeService.delete(Long.parseLong(id));
        }
        return R.ok();
    }
    @ApiOperation(value = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "财务id【必填】"),
            @ApiImplicitParam(name = "clubId", value = "社团id"),
            @ApiImplicitParam(name = "title", value = "活动名称"),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    @PostMapping("/update")
    public R update(
//            @RequestBody Finance finance
            String title,
            Integer clubId,
            String remark,
            Integer id
    ){
        Finance finance=new Finance();
        finance.setId(id);
        if( title   !=null){
            finance.setTitle(title);
        }

        if( clubId!=null){
            finance.setClubId(clubId);
        }
        if( remark!=null){
            finance.setRemark(remark);
        }
        int num=financeService.updateData(finance);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }


}
