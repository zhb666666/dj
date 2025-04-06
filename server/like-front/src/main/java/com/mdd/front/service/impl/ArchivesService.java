package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.Archives;
import com.mdd.common.mapper.ArchivesMapper;
import com.mdd.front.service.IArchivesService;
import com.mdd.front.vo.ArchivesDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class ArchivesService implements IArchivesService {
    @Resource
    ArchivesMapper archivesMapper;
    @Override
    public ArchivesDetailVo detailForUser(Integer userId) {
        QueryWrapper<Archives> archivesQueryWrapper = new QueryWrapper<>();
        archivesQueryWrapper.eq("user_id",userId)
                .last("limit 1");
        Archives archives = archivesMapper.selectOne(archivesQueryWrapper);
        Assert.notNull(archives,"档案数据为空");
        ArchivesDetailVo archivesDetailVo = new ArchivesDetailVo();
        BeanUtils.copyProperties(archives,archivesDetailVo);
        return archivesDetailVo;
    }
}
