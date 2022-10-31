package com.rick.sqltool.utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBUtil {

    public static Connection getConnection(String URL,String USER,String PASSWORD) throws Exception{
        // 加载Oracle驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // 连接数据库
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
