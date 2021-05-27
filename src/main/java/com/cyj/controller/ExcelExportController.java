package com.cyj.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cyj.domain.ExcelData;
import com.cyj.domain.User;
import com.cyj.util.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelExportController {

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void excel(HttpServletResponse response) throws Exception {
        System.out.println("开始下载");
        ExcelData data = new ExcelData();
        data.setName("用户信息数据");
        //添加表头
        List<String> titles = new ArrayList();

        titles.add("title1");
        titles.add("title2");
        data.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<User> listdata = new ArrayList<>();
        for(int i=1; i<listdata.size();i++){
            row=new ArrayList();
            row.add(listdata.get(i).getNumber());
            row.add(listdata.get(i).getCollege());
            rows.add(row);
        }
        data.setRows(rows);

        SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName=fdate.format(new Date())+".xls";
        ExcelUtils.exportExcel(response, fileName, data);
    }
}