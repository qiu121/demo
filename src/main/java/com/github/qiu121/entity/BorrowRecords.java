package com.github.qiu121.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@TableName("`borrow_records`")
public class BorrowRecords implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 借阅记录ID，主键
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    /**
     * 借书用户ID，外键关联users表
     */
    private Integer userId;

    /**
     * 借阅的图书ID，外键关联books表
     */
    private Integer bookId;

    /**
     * 借书日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate borrowDate;

    /**
     * 应还日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate dueDate;

    /**
     * 实际归还日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate returnDate;

    /**
     * 借阅状态
     */
    private String status;

    /**
     * 逾期罚款金额
     */
    private BigDecimal fine;

    /**
     * 记录创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}