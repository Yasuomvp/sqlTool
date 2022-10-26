package com.rick.sqltool.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class DbConnectionInfo {

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

}
