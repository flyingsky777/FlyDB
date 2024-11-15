package com.flydb.web;

import com.flydb.entity.ResultVo;
import com.flydb.service.FlySqliteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/flydb")
public class FlyDBController {
    @Resource
    private FlySqliteService service;

    // 获取提交记录
    @GetMapping("/getCommitLog")
    public ResultVo getCommitLog() {
        return null;
    }

    // 获取提交详情
    @GetMapping("/getCommitDetail")
    public ResultVo getCommitDetail(String commitId) {
        return null;
    }

    // 添加一条记录
    @GetMapping("/add")
    public ResultVo add(String tableName, String data) {
        return null;
    }

    // 提交
    @GetMapping("/commit")
    public ResultVo commit(String commitMsg) {
        return null;
    }

    // 回滚[待定]
    @GetMapping("/rollback")
    public ResultVo rollback(String commitId) {
        return null;
    }

    // 判断是否拥有审核权限
    @GetMapping("/hasAuth")
    public ResultVo hasAuth() {
        return null;
    }

    // 审核
    @GetMapping("/auth")
    public ResultVo auth(String commitId, String authMsg) {
        return null;
    }
}
