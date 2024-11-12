package com.flydb.data.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmitHistory {
    private String id;
    private String title;
    private String name;
    private String time;

    public SubmitHistory(String title) {
        this.title = title;
        this.name = "flyDB";
        this.time = DateUtil.now();
        this.status = 0;
    }

    // 0-待提交、1-已提交、2-已回滚
    private Integer status;

    private List<SubmitHistoryInfo> items;
}
