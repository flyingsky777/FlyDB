package com.flydb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultVo {
    private int code;
    private Long total;
    private String msg;
    private Object data;

    public static ResultVo success() {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("success");
        return resultVo;
    }

    public static ResultVo success(Object data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("success");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo page(long total, Object data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("success");
        resultVo.setTotal(total);
        resultVo.setData(data);
        return resultVo;
    }


    public static ResultVo error(String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(500);
        resultVo.setMsg(msg);
        return resultVo;
    }

}
