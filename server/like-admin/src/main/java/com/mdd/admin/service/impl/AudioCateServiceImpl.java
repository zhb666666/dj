package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.service.IAudioCateService;
import com.mdd.admin.validate.AudioCateCreateValidate;
import com.mdd.admin.validate.AudioCateUpdateValidate;
import com.mdd.admin.validate.AudioCateSearchValidate;
import com.mdd.admin.vo.AudioCateListedVo;
import com.mdd.admin.vo.AudioCateDetailVo;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.AudioCate;
import com.mdd.common.mapper.AudioCateMapper;
import com.mdd.common.util.ListUtils;
import com.mdd.common.util.TimeUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 有声读物类目实现类
 * @author LikeAdmin
 */
@Service
public class AudioCateServiceImpl implements IAudioCateService {
        
    @Resource
    AudioCateMapper audioCateMapper;

    /**
     * 有声读物类目列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AudioCateListedVo>
     */
    @Override
    public PageResult<AudioCateListedVo> list(PageValidate pageValidate, AudioCateSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AudioCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));

        audioCateMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "like:title:str",
            "=:image:str",
            "=:sort:long",
        });

        IPage<AudioCate> iPage = audioCateMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AudioCateListedVo> list = new LinkedList<>();
        for(AudioCate item : iPage.getRecords()) {
            AudioCateListedVo vo = new AudioCateListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setImage(UrlUtils.toAbsoluteUrl(item.getImage()));
            vo.setCreateTime(TimeUtils.timestampToDate(item.getCreateTime()));
            vo.setUpdateTime(TimeUtils.timestampToDate(item.getUpdateTime()));
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    @Override
    public List<AudioCateListedVo> all() {
        List<AudioCate> audioCates = audioCateMapper.selectList(
                new QueryWrapper<AudioCate>()
                        .eq("is_show", 1)
                        .orderByAsc("id")
        );
        LinkedList<AudioCateListedVo> audioCateListedVos = new LinkedList<>();
        for (AudioCate item : audioCates) {
            AudioCateListedVo audioCateListedVo = new AudioCateListedVo();
            BeanUtils.copyProperties(item,audioCateListedVo);
            audioCateListedVos.add(audioCateListedVo);
        }
        return audioCateListedVos;
    }

    /**
     * 有声读物类目详情
     *
     * @author LikeAdmin
     * @param id 主键参数
     * @return AudioCate
     */
    @Override
    public AudioCateDetailVo detail(Integer id) {
        AudioCate model = audioCateMapper.selectOne(
                new QueryWrapper<AudioCate>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AudioCateDetailVo vo = new AudioCateDetailVo();
        BeanUtils.copyProperties(model, vo);
        vo.setImage(UrlUtils.toAbsoluteUrl(model.getImage()));
        return vo;
    }

    /**
     * 有声读物类目新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    @Override
    public void add(AudioCateCreateValidate createValidate) {
        AudioCate model = new AudioCate();
        model.setTitle(createValidate.getTitle());
        model.setImage(UrlUtils.toRelativeUrl(createValidate.getImage()));
        model.setSort(createValidate.getSort());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        audioCateMapper.insert(model);
    }

    /**
     * 有声读物类目编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    @Override
    public void edit(AudioCateUpdateValidate updateValidate) {
        AudioCate model = audioCateMapper.selectOne(
                new QueryWrapper<AudioCate>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setTitle(updateValidate.getTitle());
        model.setImage(UrlUtils.toRelativeUrl(updateValidate.getImage()));
        model.setSort(updateValidate.getSort());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        audioCateMapper.updateById(model);
    }

    /**
     * 有声读物类目删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    @Override
    public void del(Integer id) {
        AudioCate model = audioCateMapper.selectOne(
                new QueryWrapper<AudioCate>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        audioCateMapper.delete(new QueryWrapper<AudioCate>().eq("id", id));
    }

}
