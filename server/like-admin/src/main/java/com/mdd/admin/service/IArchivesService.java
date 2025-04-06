package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.ArchivesCreateValidate;
import com.mdd.admin.validate.ArchivesUpdateValidate;
import com.mdd.admin.validate.ArchivesSearchValidate;
import com.mdd.admin.vo.ArchivesListedVo;
import com.mdd.admin.vo.ArchivesDetailVo;
import com.mdd.common.core.PageResult;

/**
 * 党员档案服务接口类
 * @author LikeAdmin
 */
public interface IArchivesService {

    /**
     * 党员档案列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ArchivesListedVo>
     */
    PageResult<ArchivesListedVo> list(PageValidate pageValidate, ArchivesSearchValidate searchValidate);

    /**
     * 党员档案详情
     *
     * @author LikeAdmin
     * @param id 主键ID
     * @return ArchivesDetailVo
     */
    ArchivesDetailVo detail(Integer id);

    /**
     * 党员档案新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    void add(ArchivesCreateValidate createValidate);

    /**
     * 党员档案编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    void edit(ArchivesUpdateValidate updateValidate);

    /**
     * 党员档案删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    void del(Integer id);

}
