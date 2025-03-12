package com.github.qiu121.service.impl;

import com.github.qiu121.entity.SystemLogs;
import com.github.qiu121.mapper.SystemLogsMapper;
import com.github.qiu121.service.ISystemLogsService;
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
public class SystemLogsServiceImpl extends ServiceImpl<SystemLogsMapper, SystemLogs> implements ISystemLogsService {

}
