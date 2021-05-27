package com.cyj.controller;


import com.cyj.domain.Club;
import com.cyj.domain.User;
import com.cyj.service.IClubService;
import com.cyj.service.IMemberService;
import com.cyj.service.IUserService;
import com.cyj.util.MD5Utils;
import com.cyj.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@Api(tags = {"登录"})
@Controller
public class LoginController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IMemberService memberService;
    @ApiOperation(value = "用户登录 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "学号/工号"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "type", value = "用户类型，2-老师， 1-学生"),
            @ApiImplicitParam(name = "loginType", value = "登入类型，1-学号/工号， 2-邮箱")
    })
    @PostMapping("/loginIn")
    @ResponseBody
//    public Map loginIn(@RequestBody  User user, HttpServletRequest request){
    public Map loginIn(String number, String password, int type,int loginType,HttpServletRequest request) throws JSONException {
        Map map=new HashMap();
        User user=new User();
        user.setNumber(number);
        user.setPassword(password);
        user.setType(type);
        System.out.println("user=="+user);

        HttpSession session=request.getSession();
        if(session==null){
            map.put("code",404);
            map.put("msg","登录超时了");
            return map;
        }
        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        User user1=null;
        if(loginType==1){
            user1=userService.queryUserByNameAndPwd(user);
        }else{
            user1=userService.queryUserByEmailAndPwd(user);
        }
        System.out.println("user=="+user1);
        List<Club> clubList=memberService.findMyClub(user.getNumber());
        System.out.println(clubList);
        if(clubList.size()>0){
            user1.setClubs(clubList);
        }

        if(user1==null){
            map.put("code",404);
            map.put("msg","用户名或者密码错误");
            return map;
        }else{
            session.setAttribute("user",user1);
            map.put("code",200);
            map.put("user",user1);
//            map.put("username",user.getUsername());
            return map;
        }


    }


    /**
     * 退出功能
     */
    @ApiOperation(value = "用户退出")
    @PostMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.invalidate();
//        response.sendRedirect(request.getContextPath()+"/login.html");

    }
}
