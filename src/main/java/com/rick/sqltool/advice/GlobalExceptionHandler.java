package com.rick.sqltool.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLSyntaxErrorException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String handleSQLException(RuntimeException e){
        log.error(getStackTrace(e));
        return "shit";
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseBody
    public String handleSQLSyntaxErrorException(SQLSyntaxErrorException e){
        log.error(getStackTrace(e));
        return "shit";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e){
        log.error(getStackTrace(e));
        return "shit";
    }

    public String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }




}
