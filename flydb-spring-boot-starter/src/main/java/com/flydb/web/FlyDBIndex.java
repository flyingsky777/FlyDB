package com.flydb.web;

import cn.hutool.core.util.StrUtil;
import com.flydb.config.FlyDBProperties;
import com.flydb.dto.LoginDto;
import com.flydb.dto.ResultVo;
import com.flydb.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/flydb")
public class FlyDBIndex {
    @Autowired
    FlyDBProperties properties;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(LoginDto dto) {
        if (StrUtil.isNotBlank(dto.getName()) && StrUtil.isNotBlank(dto.getPass())) {
            if (dto.getName().equals(properties.getAccount()) && dto.getPass().equals(properties.getPassword())) {
                String token = Util.getToken(dto.getName(), dto.getPass());
                return ResultVo.success(token);
            } else {
                return ResultVo.error("账号或密码错误");
            }
        } else {
            return ResultVo.error("账号或密码不能为空");
        }
    }
}
