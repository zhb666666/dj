package com.mdd.admin.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.service.IMemberService;
import com.mdd.admin.validate.MemberCreateValidate;
import com.mdd.admin.validate.MemberUpdateValidate;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.vo.MemberListedVo;
import com.mdd.admin.vo.MemberDetailVo;
import com.mdd.admin.vo.MemberTreeVo;
import com.mdd.admin.vo.system.SystemAuthDeptVo;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.Member;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.mapper.MemberMapper;
import com.mdd.common.mapper.system.SystemAuthDeptMapper;
import com.mdd.common.util.ListUtils;
import com.mdd.common.util.TimeUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 党员实现类
 * @author LikeAdmin
 */
@Service
public class MemberServiceImpl implements IMemberService {
        
    @Resource
    MemberMapper memberMapper;

    @Resource
    SystemAuthDeptMapper systemAuthDeptMapper;

    /**
     * 党员列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<MemberListedVo>
     */
    @Override
    public PageResult<MemberListedVo> list(PageValidate pageValidate, MemberSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();
        MPJQueryWrapper<Member> memberMPJQueryWrapper = new MPJQueryWrapper<>();
        memberMPJQueryWrapper.selectAll(Member.class)
                .select("D.name as d_name","P.name as p_name")
                .leftJoin("?_system_auth_dept D ON D.id = t.department".replace("?_",GlobalConfig.tablePrefix))
                .leftJoin("?_system_auth_post P ON P.id = t.level".replace("?_",GlobalConfig.tablePrefix))
                .orderByAsc(Arrays.asList("t.sort", "t.id"));
        if (searchValidate.getDepartment() != null && searchValidate.getDepartment() > 1){
            memberMPJQueryWrapper.eq("t.department",searchValidate.getDepartment());
        }
        memberMapper.setSearch(memberMPJQueryWrapper, searchValidate, new String[]{
            "like:name@t.name:str",
            "=:level@t.level:int",
            "=:isShow@is_show:int",
        });

        IPage<MemberListedVo> iPage = memberMapper.selectJoinPage(new Page<>(page, limit),MemberListedVo.class, memberMPJQueryWrapper);

        List<MemberListedVo> list = new LinkedList<>();
        for(MemberListedVo item : iPage.getRecords()) {
            MemberListedVo vo = new MemberListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setAvatar(UrlUtils.toAbsoluteUrl(item.getAvatar()));
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }



    /**
     * 党员详情
     *
     * @author LikeAdmin
     * @param id 主键参数
     * @return Member
     */
    @Override
    public MemberDetailVo detail(Integer id) {
        Member model = memberMapper.selectOne(
                new QueryWrapper<Member>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        MemberDetailVo vo = new MemberDetailVo();
        BeanUtils.copyProperties(model, vo);
        vo.setAvatar(UrlUtils.toAbsoluteUrl(model.getAvatar()));
        return vo;
    }

    /**
     * 党员新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    @Override
    public void add(MemberCreateValidate createValidate) {
        Member model = new Member();
        model.setName(createValidate.getName());
        model.setAvatar(UrlUtils.toRelativeUrl(createValidate.getAvatar()));
        model.setLevel(createValidate.getLevel());
        model.setDepartment(createValidate.getDepartment());
        model.setDescription(createValidate.getDescription());
        model.setIsShow(createValidate.getIsShow());
        model.setSort(createValidate.getSort());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        memberMapper.insert(model);
    }

    /**
     * 党员编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    @Override
    public void edit(MemberUpdateValidate updateValidate) {
        Member model = memberMapper.selectOne(
                new QueryWrapper<Member>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setName(updateValidate.getName());
        model.setAvatar(UrlUtils.toRelativeUrl(updateValidate.getAvatar()));
        model.setLevel(updateValidate.getLevel());
        model.setDepartment(updateValidate.getDepartment());
        model.setDescription(updateValidate.getDescription());
        model.setIsShow(updateValidate.getIsShow());
        model.setSort(updateValidate.getSort());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        memberMapper.updateById(model);
    }

    /**
     * 党员删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    @Override
    public void del(Integer id) {
        Member model = memberMapper.selectOne(
                new QueryWrapper<Member>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        memberMapper.delete(new QueryWrapper<Member>().eq("id", id));
    }

}
