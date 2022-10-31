package com.rick.sqltool.controller;


import com.rick.sqltool.entity.DbConnectionInfo;
import com.rick.sqltool.entity.SchemaAndSqlInfo;
import com.rick.sqltool.runner.SqlRunner;
import com.rick.sqltool.utils.DBUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/tool")
@CrossOrigin
public class ExecuteSqlController {

    private DbConnectionInfo dbConnectionInfo;

    @RequestMapping("/test")
    @ResponseBody
    public String testHello(){
        return "hello world";
    }

    @RequestMapping("/dbConn")
    @ResponseBody
    public String dbConn(@RequestBody DbConnectionInfo info){
        Connection connection = null;
        try {
            connection = DBUtil.getConnection(info.getDbUrl(), info.getDbUsername(), info.getDbPassword());
            return "yes";
        } catch (Exception e) {
            return "shit";
        } finally {
            try {
                connection.close();
                this.dbConnectionInfo = info;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequestMapping("/executeSql")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String executeSql(@RequestBody SchemaAndSqlInfo info) throws Exception {
        String[] schemas = info.getSchemas().split(",");
        String[] sqls = info.getSqls().split(";");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(schemas.length * 2, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
        CountDownLatch countDownLatch = new CountDownLatch(schemas.length);


        for (String schema : schemas) {
            Connection connection = DBUtil.getConnection(dbConnectionInfo.getDbUrl(),dbConnectionInfo.getDbUsername(),dbConnectionInfo.getDbPassword());
            threadPoolExecutor.execute(new SqlRunner(connection,schema,sqls,countDownLatch));
        }
        countDownLatch.await();
        return "yes";

    }


}
