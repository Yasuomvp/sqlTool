package com.rick.sqltool.controller;

import com.rick.sqltool.generator.domain.Datasource;
import com.rick.sqltool.generator.service.DatasourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/db")
@CrossOrigin
public class DatabaseInfoController {

    private final DatasourceService datasourceService;

    public DatabaseInfoController(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Boolean addDatasourceInfo(@RequestBody Datasource datasource){
        return datasourceService.save(datasource);
    }

}
