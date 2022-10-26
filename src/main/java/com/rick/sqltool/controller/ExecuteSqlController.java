package com.rick.sqltool.controller;


import com.rick.sqltool.entity.DbConnectionInfo;
import com.rick.sqltool.utils.DBUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;

@Controller
@RequestMapping("/tool")
@CrossOrigin
public class ExecuteSqlController {

    @RequestMapping("/test")
    @ResponseBody
    public String testHello(){
        return "hello world";
    }

    @RequestMapping("/dbConn")
    @ResponseBody
    public String dbConn(@RequestBody DbConnectionInfo info){
        try {
            DBUtil.setAndReturnConnection(info.getDbUrl(), info.getDbUsername(), info.getDbPassword());
            return "yes";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
