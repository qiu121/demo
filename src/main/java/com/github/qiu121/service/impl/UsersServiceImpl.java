package com.github.qiu121.service.impl;

import com.github.qiu121.entity.Users;
import com.github.qiu121.mapper.UsersMapper;
import com.github.qiu121.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author to_Geek
* @date 2025/03/09
* @version 1.0
*/
@Service
@Transactional(rollbackFor = Throwable.class)
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
