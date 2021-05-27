//package com.cyj.controller;
//
//
//
//import com.cyj.domain.ExcelData;
//import com.cyj.domain.User;
//import com.cyj.service.IUserService;
//import com.cyj.util.ExcelConstant;
//import com.cyj.util.ExcelUtils;
//import com.cyj.util.R;
//import com.github.pagehelper.PageHelper;
//
//import com.github.pagehelper.PageInfo;
//
//import io.swagger.annotations.Api;
//
//import io.swagger.annotations.ApiImplicitParam;
//
//import io.swagger.annotations.ApiImplicitParams;
//
//import io.swagger.annotations.ApiOperation;
//
//import org.springframework.web.bind.annotation.*;
//
//
//
//import javax.annotation.Resource;
//
//import javax.servlet.http.HttpServletResponse;
//
//import java.util.ArrayList;
//
//import java.util.List;
//
//
//
//@RestController
//
//@RequestMapping("excel")
//
//public class ExcelController {
//
//
//
//    @Resource
//
//    private IUserService userInfoService;
//
//
//
//    @RequestMapping("/test")
//
//    public R<Integer> test(){
//
//        int rowIndex = 0;
//
//        List<User> list = userInfoService.selectAlla(0, 0);
//
//        ExcelData data = new ExcelData();
//
//        data.setName("hello");
//
//        List<String> titles = new ArrayList();
//
//        titles.add("ID");
//
//        titles.add("userName");
//
//        titles.add("password");
//
//        data.setTitles(titles);
//
//
//
//        List<List<Object>> rows = new ArrayList();
//
//        for(int i = 0, length = list.size();i<length;i++){
//
//            User userInfo = list.get(i);
//
//            List<Object> row = new ArrayList();
//
//            row.add(userInfo.getId());
//
//            row.add(userInfo.getNumber());
//
//            row.add(userInfo.getPassword());
//
//            rows.add(row);
//
//        }
//
//        data.setRows(rows);
//
//        try{
//
//            rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//
//        }
//
//        return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
//
//    }
//
//
//
//    @RequestMapping("/test2")
//
//    public void test2(HttpServletResponse response){
//
//        int rowIndex = 0;
//
//        List<User> list = userInfoService.selectAlla(0, 0);
//
//        ExcelData data = new ExcelData();
//
//        data.setName("hello");
//
//        List<String> titles = new ArrayList();
//
//        titles.add("ID");
//
//        titles.add("userName");
//
//        titles.add("password");
//
//        data.setTitles(titles);
//
//
//
//        List<List<Object>> rows = new ArrayList();
//
//        for(int i = 0, length = list.size();i<length;i++){
//
//            User userInfo = list.get(i);
//
//            List<Object> row = new ArrayList();
//
//            row.add(userInfo.getId());
//
//            row.add(userInfo.getNumber());
//
//            row.add(userInfo.getPassword());
//
//            rows.add(row);
//
//        }
//
//        data.setRows(rows);
//
//        try{
//
//            ExcelUtils.exportExcel(response,"test2",data);
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//}