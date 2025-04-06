package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.AccessRecord;
import com.mdd.common.mapper.AccessRecordMapper;
import com.mdd.common.util.TimeUtils;
import com.mdd.front.service.IAccessRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AccessRecordService implements IAccessRecordService {
    @Resource
    AccessRecordMapper accessRecordMapper;
    @Override
    public void add(String ipAddress) {
        if (ipAddress.length() < 1){
            return;
        }

        String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        QueryWrapper<AccessRecord> recordQueryWrapper = new QueryWrapper<AccessRecord>()
                .select("id")
                .eq("ip_address", ipAddress)
                .between("create_time",
                        TimeUtils.dateToTimestamp(currentDay+" 00:00:00"),
                        TimeUtils.dateToTimestamp(currentDay+" 23:59:59")
                );
        AccessRecord accessRecord1 = accessRecordMapper.selectOne(recordQueryWrapper);
        if (accessRecord1 != null){
            return;
        }
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setIpAddress(ipAddress);
        accessRecord.setCreateTime(System.currentTimeMillis()/1000);
        accessRecordMapper.insert(accessRecord);
    }
}
