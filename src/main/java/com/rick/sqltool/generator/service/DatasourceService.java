package com.rick.sqltool.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.sqltool.generator.domain.Datasource;
import org.springframework.transaction.annotation.Transactional;

/**
* @author guo
* @description 针对表【datasource】的数据库操作Service
* @createDate 2022-11-09 17:50:56
*/
@Transactional(rollbackFor = Exception.class)
public interface DatasourceService extends IService<Datasource> {

}
