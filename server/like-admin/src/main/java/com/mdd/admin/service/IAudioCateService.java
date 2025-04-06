package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.AudioCateCreateValidate;
import com.mdd.admin.validate.AudioCateUpdateValidate;
import com.mdd.admin.validate.AudioCateSearchValidate;
import com.mdd.admin.vo.AudioCateListedVo;
import com.mdd.admin.vo.AudioCateDetailVo;
import com.mdd.common.core.PageResult;

import java.util.List;

/**
 * 有声读物类目服务接口类
 * @author LikeAdmin
 */
public interface IAudioCateService {

    /**
     * 有声读物类目列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AudioCateListedVo>
     */
    PageResult<AudioCateListedVo> list(PageValidate pageValidate, AudioCateSearchValidate searchValidate);

    List<AudioCateListedVo> all();

    /**
     * 有声读物类目详情
     *
     * @author LikeAdmin
     * @param id 主键ID
     * @return AudioCateDetailVo
     */
    AudioCateDetailVo detail(Integer id);

    /**
     * 有声读物类目新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    void add(AudioCateCreateValidate createValidate);

    /**
     * 有声读物类目编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    void edit(AudioCateUpdateValidate updateValidate);

    /**
     * 有声读物类目删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    void del(Integer id);

}
