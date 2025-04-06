package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.admin.service.IAccessRecordService;
import com.mdd.common.entity.AccessRecord;
import com.mdd.common.mapper.AccessRecordMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessRecordService implements IAccessRecordService {

    @Resource
    AccessRecordMapper accessRecordMapper;
    @Override
    public Long getTodayVisitCount(Date currentTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(currentTime);
        QueryWrapper<AccessRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("DATE(create_time) = DATE('"+today+"')");
        return accessRecordMapper.selectCount(queryWrapper);
    }
}
