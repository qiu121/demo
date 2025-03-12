package com.github.qiu121.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("`books`")
public class Books implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 图书ID，主键
     */
    @TableId(value = "book_id", type = IdType.AUTO)
    private Long bookId;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版年份
     */
    private Integer publishYear;

    /**
     * 图书分类，如小说、技术、历史等
     */
    private String category;

    /**
     * ISBN编号，唯一
     */
    private String isbn;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 当前可借数量
     */
    private Integer available;

    /**
     * 图书入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 图书信息更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}