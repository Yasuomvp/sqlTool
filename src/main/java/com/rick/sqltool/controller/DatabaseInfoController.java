package com.rick.sqltool.controller;

import com.rick.sqltool.generator.domain.Datasource;
import com.rick.sqltool.generator.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/db")
public class DatabaseInfoController {

    private final DatasourceService datasourceService;

    public DatabaseInfoController(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String addDatasourceInfo(@RequestBody Datasource datasource){

        System.out.println(datasource);

        return "yes";

//        boolean save = datasourceService.save(datasource);
//        if (save){
//            return "yes";
//        }else {
//            return "shit";
//        }

    }

}
