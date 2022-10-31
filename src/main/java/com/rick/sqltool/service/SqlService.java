package com.rick.sqltool.service;

import com.rick.sqltool.entity.DbConnectionInfo;
import com.rick.sqltool.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SqlService {

    public Boolean executeSql(DbConnectionInfo info, String schema, String[] sqls) throws Exception {

        log.info("线程名称： " + Thread.currentThread().getName() + " 开始执行 " + schema + " 的SQL");
        Connection connection = DBUtil.getConnection(info.getDbUrl(), info.getDbUsername(), info.getDbPassword());
        Statement statement = connection.createStatement();
        String changeSchemaSql = "ALTER SESSION SET CURRENT_SCHEMA = " + schema;
        statement.execute(changeSchemaSql);
        for (String sql : sqls) {
            statement.execute(sql);
        }
        log.info("线程名称： " + Thread.currentThread().getName() + " " + schema + " SQL执行完毕");
        return true;

    }
}