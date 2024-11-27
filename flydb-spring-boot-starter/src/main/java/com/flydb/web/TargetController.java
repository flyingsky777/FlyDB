package com.flydb.web;

import com.flydb.db.TargetService;
import com.flydb.dto.ResultVo;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flydb/target")
public class TargetController {
    @Autowired
    private TargetService dbService;

    // 获取所有表名
    @GetMapping("/getTableNames")
    public ResultVo getTableNames() {
        List<Table> tableNames = dbService.getTableNames();
        return ResultVo.success(tableNames);
    }

    // 获取表信息
    @GetMapping("/getTableInfo")
    public ResultVo getTableInfo(@RequestParam String tableName) {
        List<TableInfo> tableInfo = dbService.getTableInfo(tableName);
        return ResultVo.success(tableInfo);
    }

    // 获取表数据
    @GetMapping("/getTableData")
    public ResultVo getTableData(String tableName, int page, int pageSize) {
        List<Map<String, Object>> tableData = dbService.getTableData(tableName, page, pageSize);
        return ResultVo.success(tableData);
    }
}
