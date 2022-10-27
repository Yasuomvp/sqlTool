package com.rick.sqltool.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SchemaAndSqlInfo {

    private String schemas;

    private String sqls;

}
