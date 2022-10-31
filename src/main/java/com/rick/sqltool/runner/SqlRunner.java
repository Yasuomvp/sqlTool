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
public class SqlRunner implements Runnable{

    private Connection connection;

    private String shcema;

    private String[] sqls;

    private CountDownLatch countDownLatch;


    @Override
    public void run() {

        log.info("线程名称： "+Thread.currentThread().getName()+" 开始执行 " + this.getShcema() + " 的SQL");

        Statement statement = null;


        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String changeSchemaSql = "ALTER SESSION SET CURRENT_SCHEMA = " + this.shcema;

        try {
            statement.execute(changeSchemaSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (String sql : sqls) {

            try {
                statement.execute(sql);
                countDownLatch.countDown();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        log.info("线程名称： "+Thread.currentThread().getName() +" " + this.getShcema() + " SQL执行完毕");


    }
}
