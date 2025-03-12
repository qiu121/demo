package com.github.qiu121.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("`fines`")
public class Fines implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 罚款ID，主键
     */
    @TableId(value = "fine_id", type = IdType.AUTO)
    private Long fineId;

    /**
     * 用户ID，外键关联users表
     */
    private Integer userId;

    /**
     * 对应的借阅记录ID，外键关联borrow_records表
     */
    private Integer recordId;

    /**
     * 罚款金额
     */
    private BigDecimal fineAmount;

    /**
     * 是否已支付罚款
     */
    private Boolean paid;

    /**
     * 罚款生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 罚款更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}