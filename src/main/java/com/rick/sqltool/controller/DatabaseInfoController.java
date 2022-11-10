package com.rick.sqltool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sqltool.generator.domain.Datasource;
import com.rick.sqltool.generator.mapper.DatasourceMapper;
import com.rick.sqltool.generator.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@Controller
@RequestMapping("/db")
@CrossOrigin
@Transactional
public class DatabaseInfoController {

    private final DatasourceService datasourceService;

    private final DatasourceMapper datasourceMapper;

    public DatabaseInfoController(DatasourceService datasourceService, DatasourceMapper datasourceMapper) {
        this.datasourceService = datasourceService;
        this.datasourceMapper = datasourceMapper;
    }

    @PostMapping("/add")
    @ResponseBody
    public Boolean addDatasourceInfo(@RequestBody Datasource datasource){
        return datasourceService.save(datasource);
    }

    @GetMapping("/listAll")
    @ResponseBody
    public List<Datasource> listAll(){
        return datasourceService.list();
    }

    @GetMapping("/listAllByPage")
    @ResponseBody
    public IPage<Datasource> listAllByPage(@RequestParam("current") Long current, @RequestParam("size") Long size){
        return datasourceService.page(new Page<>(current, size));
    }

}
