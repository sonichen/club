package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
import com.cyj.domain.Clubtype;
import com.cyj.domain.Member;
import com.cyj.service.IClubtypeService;
import com.cyj.service.IMemberService;
import com.cyj.util.JsonObject;
import com.cyj.util.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"社团类型"})
@RestController
@RequestMapping("/clubtype")
public class ClubtypeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IClubtypeService clubtypeService;

    @Resource
    private IMemberService memberService;

    @ApiOperation(value = "分页返回社团类型，支持高级查询备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "备注"),
    })
    @GetMapping ("/queryClubtypeAll")
    public JsonObject queryClubtypeAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer list,
            String remark
    ){
        Clubtype clubtype=new Clubtype();
        if(remark!=null){
            clubtype.setRemark(remark);
        }
        JsonObject object=new JsonObject();
        PageInfo<Clubtype> pageInfo= clubtypeService.queryClubtypeAll(page,list,clubtype);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return  object;
    }


    @ApiOperation(value = "一次性返回所有类型,支持高级查询")
    @GetMapping("/queryAll")
    public List<Clubtype> queryList(String type,String remark){
        Clubtype clubtype=new Clubtype();
        if(type!=null){
            clubtype.setType(type);
        }
        if(remark!=null){
            clubtype.setRemark(remark);
        }

        List<Clubtype> list = clubtypeService.findAllClubtype(clubtype);
        return list;
    }


    @ApiOperation(value = "新增社团类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型，【eg文学类】"),
            @ApiImplicitParam(name = "remark", value = "备注【eg文学类社团介绍】")
    })
    @PostMapping("/add")
    public R add(String type, String remark){
        Clubtype clubtype=new Clubtype();
        clubtype.setType(type);
        //检查是否添加过
        List<Clubtype> check=clubtypeService.findAllClubtype(clubtype);
        if(check.size()>0){
            return R.fail("该类型已经存在");
        }
        clubtype.setRemark(remark);

        int num=clubtypeService.add(clubtype);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("添加失败");
        }
    }
    @ApiOperation(value = "删除社团类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "类型id"),
    })
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        System.out.println("ids==="+ids);
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            clubtypeService.delete(Long.parseLong(id));
        }
        return R.ok();
    }
    @ApiOperation(value = "更新社团类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "remark", value = "备注"),
            @ApiImplicitParam(name = "id", value = "类型id")
    })
    @PostMapping("/update")
    public R update(
//            @RequestBody Clubtype clubtype
            String type, String remark,Integer id
    ){
        Clubtype clubtype=
//                clubtypeService.findById(Long.parseLong(id+""));
        new Clubtype();
        clubtype.setType(type);
        clubtype.setRemark(remark);
        clubtype.setId(id);
        if(type!=null){
            clubtype.setType(type);
        }
        if(remark!=null){
            clubtype.setRemark(remark);
        }
        int num=clubtypeService.updateData(clubtype);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("修改失败");
        }
    }


}
