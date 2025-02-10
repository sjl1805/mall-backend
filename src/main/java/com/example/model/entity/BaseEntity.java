package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data

public class BaseEntity implements Serializable {
    /* 序列化版本号 */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /* 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /* 删除标志 */
    /* 乐观锁版本号 */
    @Version
    private Integer version;
    @TableField("deleted")
    private Integer deleted = 0;
} 