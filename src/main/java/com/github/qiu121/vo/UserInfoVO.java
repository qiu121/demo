package com.github.qiu121.vo;

import com.github.qiu121.entity.Permission;
import com.github.qiu121.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Leo
 * @version 1.0
 * @date 2025/03/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVO {
    private String userId;
    private String username;
    private Role role;
    private String email;
    private String phone;
    private List<Permission> permission;
}
