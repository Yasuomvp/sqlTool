package com.rick.sqltool.runner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;


@Data
@AllArgsConstructor
@Slf4j
public class SqlRunner implements Runnable {

    private Connection connection;

    private String schema;

    private String[] sqls;

    private CountDownLatch countDownLatch;


    @Override
    public void run() {

        log.info("线程名称： " + Thread.currentThread().getName() + " 开始执行 " + this.getSchema() + " 的SQL");

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String changeSchemaSql = "ALTER SESSION SET CURRENT_SCHEMA = " + this.schema;
        try {
            statement.execute(changeSchemaSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (String sql : sqls) {
            try {
                statement.execute(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();

        }
        log.info("线程名称： " + Thread.currentThread().getName() + " " + this.getSchema() + " SQL执行完毕");


    }
}
