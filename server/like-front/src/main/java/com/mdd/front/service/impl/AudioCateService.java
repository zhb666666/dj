package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.AudioCate;
import com.mdd.common.mapper.AudioCateMapper;
import com.mdd.common.util.UrlUtils;
import com.mdd.front.service.IAudioCateService;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.audioCate.AudioCateDetailVo;
import com.mdd.front.vo.audioCate.AudioCateListedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;

@Service
public class AudioCateService implements IAudioCateService {
    @Resource
    AudioCateMapper audioCateMapper;
    @Override
    public PageResult<AudioCateListedVo> list(PageValidate pageValidate) {
        Integer pageNo = pageValidate.getPageNo();
        Integer pageSize = pageValidate.getPageSize();
        QueryWrapper<AudioCate> audioCateQueryWrapper = new QueryWrapper<>();
        audioCateQueryWrapper.eq("is_show",1)
                .orderByDesc(Arrays.asList("sort","id"));
        IPage<AudioCate> iPage = audioCateMapper.selectPage(
                new Page<>(pageNo,pageSize),audioCateQueryWrapper);
        LinkedList<AudioCateListedVo> audioCateListedVos = new LinkedList<>();
        for (AudioCate item : iPage.getRecords()) {
            AudioCateListedVo audioCateListedVo = new AudioCateListedVo();
            BeanUtils.copyProperties(item,audioCateListedVo);
            audioCateListedVo.setImage(UrlUtils.toAbsoluteUrl(item.getImage()));
            audioCateListedVos.add(audioCateListedVo);
        }
        return PageResult.iPageHandle(iPage.getTotal(),iPage.getCurrent(),iPage.getSize(),audioCateListedVos);
    }

    @Override
    public AudioCateDetailVo detail(Integer id) {
        AudioCate audioCate = audioCateMapper.selectOne(
                new QueryWrapper<AudioCate>()
                        .eq("is_show",1)
                        .eq("id",id)
                        .last("limit 1")
        );
        Assert.notNull(audioCate,"音频分类详情不存在");
        AudioCateDetailVo audioCateDetailVo = new AudioCateDetailVo();
        BeanUtils.copyProperties(audioCate,audioCateDetailVo);
        audioCateDetailVo.setImage(UrlUtils.toAbsoluteUrl(audioCate.getImage()));
        return audioCateDetailVo;
    }


}
