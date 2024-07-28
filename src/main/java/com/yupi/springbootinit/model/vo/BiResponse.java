package com.yupi.springbootinit.model.vo;

import lombok.Data;

@Data
public class BiResponse {
    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;
    //新生成的图标ID

    private Long chartId;
}
