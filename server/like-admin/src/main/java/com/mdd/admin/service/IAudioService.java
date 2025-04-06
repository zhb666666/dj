package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.AudioCreateValidate;
import com.mdd.admin.validate.AudioUpdateValidate;
import com.mdd.admin.validate.AudioSearchValidate;
import com.mdd.admin.vo.AudioListedVo;
import com.mdd.admin.vo.AudioDetailVo;
import com.mdd.common.core.PageResult;

/**
 * 有声读物服务接口类
 * @author LikeAdmin
 */
public interface IAudioService {

    /**
     * 有声读物列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AudioListedVo>
     */
    PageResult<AudioListedVo> list(PageValidate pageValidate, AudioSearchValidate searchValidate);

    /**
     * 有声读物详情
     *
     * @author LikeAdmin
     * @param id 主键ID
     * @return AudioDetailVo
     */
    AudioDetailVo detail(Integer id);

    /**
     * 有声读物新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    void add(AudioCreateValidate createValidate);

    /**
     * 有声读物编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    void edit(AudioUpdateValidate updateValidate);

    /**
     * 有声读物删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    void del(Integer id);

}
