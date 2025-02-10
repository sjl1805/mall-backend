package com.example.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.Collections;
import java.util.List;


@Data
public class PaginationDTO<T> implements IPage<T> {
    // 当前页码（从1开始）
    private long current = 1;
    // 每页显示条数
    private long size = 10;
    // 总记录数
    private long total;
    // 分页数据列表
    private List<T> records = Collections.emptyList();

    // 需要显式声明无参构造
    public PaginationDTO() {
    }

    // 方便创建的分页构造器
    public PaginationDTO(long current, long size) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    // 总页数自动计算
    @Override
    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    // 排序条件（可根据需要实现）
    @Override
    public List<com.baomidou.mybatisplus.core.metadata.OrderItem> orders() {
        return Collections.emptyList();
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
