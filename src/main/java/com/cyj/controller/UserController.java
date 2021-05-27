package com.cyj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.domain.Club;
import com.cyj.domain.Student;
import com.cyj.domain.User;
import com.cyj.service.IClubService;
import com.cyj.service.IUserService;
import com.cyj.util.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@CrossOrigin(origins = "*")
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IUserService userService;

    @Autowired
    private IClubService clubService;

    @ApiOperation(value = "用户总数")
    @GetMapping("/getTotal")
    public Integer getUserCount(){
        int num=userService.count();
       return  num;
    }
    @ApiOperation(value = "分页查询所有用户，可以条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "备注"),
            @ApiImplicitParam( name="type"        , value="用户类型 1-学生，type2-老师"             ) ,
            @ApiImplicitParam( name="college"     , value="学院"            ),
            @ApiImplicitParam( name="grade"       , value="年级"              ),
            @ApiImplicitParam( name="number"      , value="学号"           ),
            @ApiImplicitParam( name="sex"         , value="性别 1-女 2-男"               ),
            @ApiImplicitParam( name="username"    , value="姓名"           ),
            @ApiImplicitParam( name="major"       , value="专业"            ),
            @ApiImplicitParam( name="studentType" , value="1-本科生，2-研究生"               ),
            @ApiImplicitParam( name="status"      , value="状态  1-正常，-1-冻结"           ),
    })
    @GetMapping ("/queryUserAll")
    public JsonObject queryUserInfoAll(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "15") Integer limit,
//                                       @RequestBody User user
                                       Integer type,
                                       String college,
                                       String grade,
                                       String number,
                                       Integer sex,
                                       String username,
                                       String major,
                                       Integer studentType,
                                       Integer status
    ){
        System.out.println("type=="+type);

        User user=new User();

        if(type!=null){
            user.setType(type);
        }
        if(college!=null){
            user.setCollege(college);
        }
        if(major!=null){
            user.setMajor(major);
        }
        if(grade!=null){
            user.setGrade(grade);
        }
        if(number!=null){
            user.setNumber(number);
        }
        if(sex!=null){
            user.setSex(sex);
        }
        if(username!=null){
            user.setUsername(username);
        }
        if(studentType!=null){
            user.setStudentType(studentType);
        }
        if(status!=null){
            user.setStatus(status);
        }
        JsonObject object=new JsonObject();
        PageInfo<User> pageInfo= userService.findUserAll(page,limit,user);
        object.setCode(0);
        object.setMsg("ok");
        object.setCount(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return object;
    }
    @ApiOperation(value = "一次性返回所有的用户，支持条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "备注"),
            @ApiImplicitParam( name="type"        , value="用户类型 1-学生，type2-老师"             ) ,
            @ApiImplicitParam( name="college"     , value="学院"            ),
            @ApiImplicitParam( name="grade"       , value="年级"              ),
            @ApiImplicitParam( name="number"      , value="学号"           ),
            @ApiImplicitParam( name="sex"         , value="性别 1-女 2-男"               ),
            @ApiImplicitParam( name="username"    , value="姓名"           ),
            @ApiImplicitParam( name="major"       , value="专业"            ),
            @ApiImplicitParam( name="studentType" , value="1-本科生，2-研究生"               ),
            @ApiImplicitParam( name="status"      , value="状态  1-正常，-1-冻结"           ),
    })
    @GetMapping("/findAll")
    public R findAll(
//            @RequestBody User user
            Integer type,
            String college,
            String grade,
            String number,
            Integer sex,
            String username,
            String major,
            Integer studentType,
            Integer status

    ){
        User user=new User();
        if(type!=null){
            user.setType(type);
        }
        if(college!=null){
            user.setCollege(college);
        }
        if(major!=null){
            user.setMajor(major);
        }
        if(grade!=null){
            user.setGrade(grade);
        }
        if(number!=null){
            user.setNumber(number);
        }
        if(sex!=null){
            user.setSex(sex);
        }
        if(username!=null){
            user.setUsername(username);
        }
        if(studentType!=null){
            user.setStudentType(studentType);
        }
        if(status!=null){
            user.setStatus(status);
        }
        List<User> users=userService.findAll(user);
        return  R.ok("返回成功",users);
    }
    @ApiOperation(value = "新增老师")
    @ApiImplicitParams({
            @ApiImplicitParam( name="college"     , value="学院"            ),
            @ApiImplicitParam( name="grade"       , value="年级"              ),
            @ApiImplicitParam( name="sex"         , value="性别 1-女 2-男"               ),
            @ApiImplicitParam( name="username"    , value="姓名"          ),
            @ApiImplicitParam( name="major"       , value="专业"            ),
            @ApiImplicitParam( name="email"       , value="邮箱"            ),
            @ApiImplicitParam( name="tel"       , value="手机号码"            ),
            @ApiImplicitParam( name="number"       , value="学号【必填】"            ),
            @ApiImplicitParam( name="username"       , value="姓名"            ),
            @ApiImplicitParam(name = "password", value = "默认123456"),

    })
    @PostMapping("/addTeacher")
    public R addTeacher(
//            @RequestBody User user
            String college,
            String grade,
            Integer sex,
            String number,
            String username,
            String password,
            String major,
            Integer studentType,
            String tel,
            String email,
            String remark
    ){
        User user=new User();
        if(college!=null){
            user.setCollege(college);
        }
        if(major!=null){
            user.setMajor(major);
        }
        if(grade!=null){
            user.setGrade(grade);
        }
        if(number!=null){
            user.setNumber(number);
        }
        if(sex!=null){
            user.setSex(sex);
        }
        if(username!=null){
            user.setUsername(username);
        }
        if(studentType!=null){
            user.setStudentType(studentType);
        }
        if(password!=null){
            user.setPassword(password);
        }
        if(email!=null){
            user.setEmail(email);
        }
        if(tel!=null){
            user.setTel(tel);
        }
        if(remark!=null){
            user.setRemark(remark);
        }
        user.setType(2);
        user.setStatus(1);
        System.out.println(user);
        // 判断账号是否重复
        User theUser = userService.queryByAccount(user);
        System.out.println("我是theUser=="+theUser);
        if (theUser != null) {
            System.out.println("存在的用户"+theUser);
           return R.fail("该用户已经存在");
        }
        // 完善账号信息
//        默认密码：123456
        if(user.getPassword()!=null){
            user.setPassword(MD5Utils.getMD5(user.getPassword()));
        }else{
            user.setPassword(MD5Utils.getMD5("123456"));
        }

        userService.add(user);
        return R.ok();
    }
    @ApiOperation(value = "新增学生")
    @ApiImplicitParams({
            @ApiImplicitParam( name="college"     , value="学院"            ),
            @ApiImplicitParam( name="grade"       , value="年级"              ),
            @ApiImplicitParam( name="sex"         , value="性别 1-女 2-男"               ),
            @ApiImplicitParam( name="number"       , value="学号【必填】"            ),
            @ApiImplicitParam( name="username"       , value="姓名"            ),
            @ApiImplicitParam(name = "password", value = "默认123456"),
            @ApiImplicitParam( name="major"       , value="专业"            ),
            @ApiImplicitParam( name="studentType" , value="1-本科生，2-研究生"               ),
            @ApiImplicitParam( name="email"       , value="邮箱"            ),
            @ApiImplicitParam( name="tel"       , value="手机号码"            ),
            @ApiImplicitParam(name = "remark", value = "备注"),
    })
    @PostMapping("/addStudent")
    public R addStudent(
//            @RequestBody User user
            String college,
            String grade,
            Integer sex,
            String number,
            String username,
             String password,
            String major,
            Integer studentType,
            String tel,
            String email,
            String remark
    ){
        User user=new User();
        if(college!=null){
            user.setCollege(college);
        }
        if(major!=null){
            user.setMajor(major);
        }
        if(grade!=null){
            user.setGrade(grade);
        }
        if(number!=null){
            user.setNumber(number);
        }
        if(sex!=null){
            user.setSex(sex);
        }
        if(username!=null){
            user.setUsername(username);
        }
        if(studentType!=null){
            user.setStudentType(studentType);
        }
        if(password!=null){
            user.setPassword(password);
        }
        if(email!=null){
            user.setEmail(email);
        }
        if(tel!=null){
            user.setTel(tel);
        }
        if(remark!=null){
            user.setRemark(remark);
        }
        System.out.println("theyhu=="+user);
        user.setIntotime(new Date());
        user.setType(1);
        user.setStatus(1);
        // 判断账号是否重复
        User theUser = userService.queryByAccount(user);
        System.out.println("我是=="+theUser);
        if (theUser != null) {
            return R.fail("该用户已经存在");
        }
        // 完善账号信息
//        默认密码：123456
        if(user.getPassword()!=null){
            user.setPassword(MD5Utils.getMD5(user.getPassword()));
        }else{
            user.setPassword(MD5Utils.getMD5("123456"));
        }

        userService.add(user);
        return R.ok();
    }

    @ApiOperation(value = "删除，逗号拼接id")
    @PostMapping("/deleteByIds")
    public R delete(String ids){
        List<String> list= Arrays.asList(ids.split(","));
        for(String id:list){
            userService.delete(Long.parseLong(id));
        }
        return R.ok();
    }


    @ApiOperation(value = "更新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number",value = "学号[必填]"  ),
            @ApiImplicitParam(name = "type",value = "用户类型[必填]"  ),
            @ApiImplicitParam(name = "oldPwd",value = "旧秘密【必】"  ),
            @ApiImplicitParam(name = "neewPwd",value = "新密码[必填]"  )
    })
    @PostMapping("/updatePsw")
    public R update(
           String oldPwd,
     String newPwd,
            String number,
         Integer type
             ){
        System.out.println("integer=="+type);
        User theUser=new User();
        System.out.println("秘密=="+MD5Utils.getMD5(oldPwd));
        theUser.setPassword(MD5Utils.getMD5(oldPwd));
        theUser.setNumber(number);
        theUser.setType(type);
        System.out.println("theuser=="+theUser);
        int id=userService.findIdByNumberAndType(theUser);
         theUser   =userService.findById(new Long(id));
        System.out.println(theUser);
        if(MD5Utils.getMD5(oldPwd).equals(theUser.getPassword())){//输入的老密码和原密码一致
            theUser.setPassword(MD5Utils.getMD5(newPwd));
            userService.updateData(theUser);
            return R.ok();
        }else{
            return R.fail("两次密码不一致");
        }
    }
    @ApiOperation(value = "更新信息")

    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "备注"),
            @ApiImplicitParam( name="college"     , value="学院"            ),
            @ApiImplicitParam( name="grade"       , value="年级"              ),
            @ApiImplicitParam( name="sex"         , value="性别 1-女 2-男"               ),
            @ApiImplicitParam( name="username"    , value="姓名"           ),
            @ApiImplicitParam( name="major"       , value="专业"            ),
            @ApiImplicitParam( name="studentType" , value="1-本科生，2-研究生"               ),
            @ApiImplicitParam( name="email"       , value="邮箱"            ),
            @ApiImplicitParam( name="tel"       , value="手机号码"            ),
            @ApiImplicitParam(name = "number",value = "学号[必填]"  ),
            @ApiImplicitParam(name = "type",value = "用户类型[必填]"  )
    })
    public R update(
//            @RequestBody User user
            String college,
            String grade,
           String number,
            Integer sex,
            String username,
            String password,
            String major,
            Integer studentType,
            String tel,
            String email,
            Integer type
    ){
        User user=new User();
        if(type!=null){
            user.setType(type);
        }
        if(college!=null){
            user.setCollege(college);
        }
        if(major!=null){
            user.setMajor(major);
        }
        if(grade!=null){
            user.setGrade(grade);
        }
        if(number!=null){
            user.setNumber(number);
        }
        if(sex!=null){
            user.setSex(sex);
        }
        if(username!=null){
            user.setUsername(username);
        }
        if(studentType!=null){
            user.setStudentType(studentType);
        }
        if(password!=null){
            user.setPassword(password);
        }
        if(email!=null){
            user.setEmail(email);
        }
        if(tel!=null){
            user.setTel(tel);
        }
        int id=userService.findIdByNumberAndType(user);
        user.setId(id);
        int num=userService.updateData(user);
        if(num>0){
            return R.ok();
        }else{
            return R.fail("修改失败");
        }
    }
    @ApiOperation(value = "冻结用户[-1]&&正常用户[1] ")
    @PostMapping("/freeze")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类型id"),
            @ApiImplicitParam(name = "status", value = "状态")

    })
    public R freeze(
            Integer id,
            Integer status){
        if(id==null){
            id=1;
        }
        System.out.println("id==="+id);
        //根据id获取当前的数据记录
        User user=userService.findById(new Long(id));
        System.out.println("我是测试"+user);
            user.setStatus(status);
           int num= userService.updateData(user);
           if(num>0){
               return R.ok();
           }else{
               return R.fail("冻结失败");
           }

    }
    @RequestMapping(value = "UserExcelDownloads", method = RequestMethod.GET)
    public void downloadAllClassmate(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<User> classmateList = userService.findAll(new User());

        String fileName = "userinf"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "学号", "姓名", "身份类型", "登录密码"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (User teacher : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(teacher.getGrade());
            row1.createCell(1).setCellValue(teacher.getNumber());
            row1.createCell(2).setCellValue(teacher.getType());
            row1.createCell(3).setCellValue(teacher.getMajor());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

}
