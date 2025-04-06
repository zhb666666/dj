package com.mdd.admin.service;

import com.alibaba.fastjson2.JSONArray;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.MemberCreateValidate;
import com.mdd.admin.validate.MemberUpdateValidate;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.vo.MemberListedVo;
import com.mdd.admin.vo.MemberDetailVo;
import com.mdd.common.core.PageResult;

/**
 * 党员服务接口类
 * @author LikeAdmin
 */
public interface IMemberService {

    /**
     * 党员列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<MemberListedVo>
     */
    PageResult<MemberListedVo> list(PageValidate pageValidate, MemberSearchValidate searchValidate);

    /**
     * 党员详情
     *
     * @author LikeAdmin
     * @param id 主键ID
     * @return MemberDetailVo
     */
    MemberDetailVo detail(Integer id);

    /**
     * 党员新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    void add(MemberCreateValidate createValidate);

    /**
     * 党员编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    void edit(MemberUpdateValidate updateValidate);

    /**
     * 党员删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    void del(Integer id);

}
