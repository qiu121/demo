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
@TableName("`user_role`")
public class UserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户角色ID
     */
    @TableId(value = "user_role_id", type = IdType.AUTO)
    private Long userRoleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
}