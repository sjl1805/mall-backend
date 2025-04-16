package com.example.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页记录数
     */
    private Long size;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 分页数据
     */
    private List<T> records;

    /**
     * 构造方法
     *
     * @param current 当前页码
     * @param size    每页记录数
     * @param total   总记录数
     * @param records 分页数据
     */
    public PageResult(Long current, Long size, Long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;

        // 计算总页数
        if (size > 0) {
            this.pages = (total + size - 1) / size;
        } else {
            this.pages = 0L;
        }

        // 是否有上一页
        this.hasPrevious = current > 1;

        // 是否有下一页
        this.hasNext = current < pages;
    }

}