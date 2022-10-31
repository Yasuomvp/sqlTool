package com.rick.sqltool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@EnableAsync
public class SqlToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlToolApplication.class, args);
    }

    @RequestMapping("/")
    public String mainPage(){
        return "forward:sqlTool.html";
    }

}
