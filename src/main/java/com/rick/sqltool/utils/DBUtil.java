package com.rick.sqltool.utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBUtil {

    private static Connection dbconnection;

    public static Connection getConnection(){
        return dbconnection;
    }

    public static Connection setAndReturnConnection(String URL,String USER,String PASSWORD) throws Exception {

        // 加载Oracle驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // 连接数据库
        dbconnection =  DriverManager.getConnection(URL, USER, PASSWORD);
        return dbconnection;
    }

}
