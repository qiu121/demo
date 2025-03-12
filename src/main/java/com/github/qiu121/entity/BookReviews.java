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
@TableName("`book_reviews`")
public class BookReviews implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID，主键
     */
    @TableId(value = "review_id", type = IdType.AUTO)
    private Long reviewId;

    /**
     * 评论用户ID，外键关联users表
     */
    private Integer userId;

    /**
     * 评论的图书ID，外键关联books表
     */
    private Integer bookId;

    /**
     * 评分，1-5星
     */
    private Integer rating;

    /**
     * 用户的评论内容
     */
    private String reviewText;

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 评论更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}