package com.example.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "分页响应格式")
public class PageResult<T> {
    @Schema(name = "total", description = "总记录数", example = "100")
    private long total;
    
    @Schema(name = "list", description = "数据列表")
    private List<T> list;
    
    @Schema(name = "page", description = "当前页码", example = "1")
    private int page;
    
    @Schema(name = "size", description = "每页数量", example = "10")
    private int size;

    public PageResult(List<T> list, long total, int page, int size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
    }
} 