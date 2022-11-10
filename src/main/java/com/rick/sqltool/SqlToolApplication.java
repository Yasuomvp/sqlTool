package com.rick.sqltool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@MapperScan("com.rick.sqltool.generator.mapper")
public class SqlToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlToolApplication.class, args);
    }


}
