package com.flydb.web;

import com.flydb.dto.LoginDto;
import com.flydb.dto.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/flydb")
public class FlyDBIndex {

    @GetMapping
    public String index() {
        return "index.html";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(LoginDto dto) {
        return ResultVo.success();
    }
}
