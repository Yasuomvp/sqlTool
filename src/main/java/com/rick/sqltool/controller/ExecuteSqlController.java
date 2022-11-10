package com.rick.sqltool.controller;


import com.rick.sqltool.entity.DbConnectionInfo;
import com.rick.sqltool.entity.SchemaAndSqlInfo;
import com.rick.sqltool.runner.SqlRunner;
import com.rick.sqltool.service.SqlService;
import com.rick.sqltool.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/tool")
@CrossOrigin
@Transactional
public class ExecuteSqlController {

    private DbConnectionInfo dbConnectionInfo;

    private final SqlService sqlService;

    private final ThreadPoolExecutor threadPoolExecutor;

    public ExecuteSqlController(SqlService sqlService, ThreadPoolExecutor threadPoolExecutor) {
        this.sqlService = sqlService;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String testHello() {
        return "hello world";
    }

    @RequestMapping("/dbConn")
    @ResponseBody
    public String dbConn(@RequestBody DbConnectionInfo info) {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection(info.getDbUrl(), info.getDbUsername(), info.getDbPassword());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
                this.dbConnectionInfo = info;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequestMapping("/executeSqlByRunnable")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String executeSqlByRunnable(@RequestBody SchemaAndSqlInfo info) throws Exception {
        String[] schemas = info.getSchemas().split(",");
        String[] sqls = info.getSqls().split(";");

        CountDownLatch countDownLatch = new CountDownLatch(schemas.length);

        for (String schema : schemas) {
            Connection connection = DBUtil.getConnection(dbConnectionInfo.getDbUrl(), dbConnectionInfo.getDbUsername(), dbConnectionInfo.getDbPassword());
            threadPoolExecutor.execute(new SqlRunner(connection, schema, sqls, countDownLatch));
        }
        countDownLatch.await();
        return "yes";


    }


    @RequestMapping("/executeSqlByFuture")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String executeSqlByFuture(@RequestBody SchemaAndSqlInfo info) throws Exception {
        String[] schemas = info.getSchemas().split(",");
        String[] sqls = info.getSqls().split(";");

        List<CompletableFuture<Boolean>> futureList = new ArrayList<>();
        AtomicBoolean flag = new AtomicBoolean(true);

        for (String schema:schemas){
            CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return sqlService.executeSql(dbConnectionInfo,schema,sqls);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, threadPoolExecutor);
            futureList.add(completableFuture);
        }

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]))
                .whenComplete((v,th)->{
                    for (CompletableFuture<Boolean> future : futureList) {
                        try {
                            if (!future.get()) {
                                flag.set(false);
                                break;
                            };
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).join();

        if (flag.get()) return "yes";
        return "shit";
    }
}
