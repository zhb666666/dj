package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.service.IAudioService;
import com.mdd.admin.validate.AudioCreateValidate;
import com.mdd.admin.validate.AudioUpdateValidate;
import com.mdd.admin.validate.AudioSearchValidate;
import com.mdd.admin.vo.AudioListedVo;
import com.mdd.admin.vo.AudioDetailVo;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.Audio;
import com.mdd.common.entity.AudioCate;
import com.mdd.common.mapper.AudioMapper;
import com.mdd.common.util.ListUtils;
import com.mdd.common.util.TimeUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 有声读物实现类
 * @author LikeAdmin
 */
@Service
public class AudioServiceImpl implements IAudioService {
        
    @Resource
    AudioMapper audioMapper;

    /**
     * 有声读物列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AudioListedVo>
     */
    @Override
    public PageResult<AudioListedVo> list(PageValidate pageValidate, AudioSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();
        MPJQueryWrapper<Audio> audioCateMPJQueryWrapper = new MPJQueryWrapper<>();
        audioCateMPJQueryWrapper.selectAll(Audio.class)
                .select("C.title as category_title")
                .leftJoin("?_audio_cate C ON C.id = t.cid".replace("?_",GlobalConfig.tablePrefix))
                .orderByDesc(Arrays.asList("sort","id"));

        audioMapper.setSearch(audioCateMPJQueryWrapper, searchValidate, new String[]{
            "=:cide@t.cid:str",
            "=:isShow@t.is_show:int",
            "like:title@t.title:str"
        });

        IPage<AudioListedVo> iPage = audioMapper.selectJoinPage(
                new Page<>(page, limit),AudioListedVo.class, audioCateMPJQueryWrapper);

        List<AudioListedVo> list = new LinkedList<>();
        for(AudioListedVo item : iPage.getRecords()) {
            AudioListedVo vo = new AudioListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(TimeUtils.timestampToDate(item.getCreateTime()));
            vo.setUpdateTime(TimeUtils.timestampToDate(item.getUpdateTime()));
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 有声读物详情
     *
     * @author LikeAdmin
     * @param id 主键参数
     * @return Audio
     */
    @Override
    public AudioDetailVo detail(Integer id) {
        Audio model = audioMapper.selectOne(
                new QueryWrapper<Audio>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AudioDetailVo vo = new AudioDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 有声读物新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    @Override
    public void add(AudioCreateValidate createValidate) {
        Audio model = new Audio();
        model.setCid(createValidate.getCid());
        model.setTitle(createValidate.getTitle());
        model.setSrc(createValidate.getSrc());
        model.setIsShow(createValidate.getIsShow());
        model.setSort(createValidate.getSort());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        audioMapper.insert(model);
    }

    /**
     * 有声读物编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    @Override
    public void edit(AudioUpdateValidate updateValidate) {
        Audio model = audioMapper.selectOne(
                new QueryWrapper<Audio>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setCid(updateValidate.getCid());
        model.setTitle(updateValidate.getTitle());
        model.setSrc(updateValidate.getSrc());
        model.setIsShow(updateValidate.getIsShow());
        model.setSort(updateValidate.getSort());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        audioMapper.updateById(model);
    }

    /**
     * 有声读物删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    @Override
    public void del(Integer id) {
        Audio model = audioMapper.selectOne(
                new QueryWrapper<Audio>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        audioMapper.delete(new QueryWrapper<Audio>().eq("id", id));
    }

}
