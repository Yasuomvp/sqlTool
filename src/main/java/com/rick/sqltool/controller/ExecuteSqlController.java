package com.rick.sqltool.controller;


import com.rick.sqltool.entity.DbConnectionInfo;
import com.rick.sqltool.entity.SchemaAndSqlInfo;
import com.rick.sqltool.utils.DBUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

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
            return "shit";
        }
    }

    @RequestMapping("/executeSql")
    @ResponseBody
    public String executeSql(@RequestBody SchemaAndSqlInfo info) throws SQLException {
        String[] schemas = info.getSchemas().split(",");
        String[] sqls = info.getSqls().split(";");

        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();

        for (String schema : schemas) {
            String changeSchemaSql = "ALTER SESSION SET CURRENT_SCHEMA = " + schema;
            statement.execute(changeSchemaSql);
            for (String sql : sqls) {
                statement.execute(sql);
            }
        }
        return "yes";

    }


}
