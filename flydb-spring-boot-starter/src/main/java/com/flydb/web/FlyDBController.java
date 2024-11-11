package com.flydb.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flydb")
public class FlyDBController {

    // 获取提交记录
    @GetMapping("/getCommitLog")
    public String getCommitLog() {
        return "";
    }

    // 获取提交详情
    @GetMapping("/getCommitDetail")
    public String getCommitDetail(String commitId) {
        return "";
    }

    // 添加一条记录
    @GetMapping("/add")
    public String add(String tableName, String data) {
        return "";
    }

    // 提交
    @GetMapping("/commit")
    public String commit(String commitMsg) {
        return "";
    }

    // 回滚[待定]
    @GetMapping("/rollback")
    public String rollback(String commitId) {
        return "";
    }

    // 判断是否拥有审核权限
    @GetMapping("/hasAuth")
    public Boolean hasAuth() {
        return false;
    }

    // 审核
    @GetMapping("/auth")
    public String auth(String commitId, String authMsg) {
        return "";
    }
}
