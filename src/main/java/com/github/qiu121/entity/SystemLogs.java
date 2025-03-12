package com.github.qiu121.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serial;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
* @author to_Geek
* @date 2025/03/09
* @version 1.0
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("`system_logs`")
public class SystemLogs implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID，主键
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 操作用户ID，外键关联users表
     */
    private Integer userId;

    /**
     * 执行的操作，例如“新增图书”
     */
    private String action;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logTime;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 操作详细信息
     */
    private String details;
}