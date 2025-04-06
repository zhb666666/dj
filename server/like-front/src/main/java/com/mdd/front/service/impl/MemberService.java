package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.Member;
import com.mdd.common.mapper.MemberMapper;
import com.mdd.common.util.UrlUtils;
import com.mdd.front.service.IMemberService;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.member.MemberDetailVo;
import com.mdd.front.vo.member.MemberListedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;

@Service
public class MemberService implements IMemberService {
    @Resource
    MemberMapper memberMapper;
    @Override
    public PageResult<MemberListedVo> list(PageValidate pageValidate) {
        Integer pageSize = pageValidate.getPageSize();
        Integer pageNo = pageValidate.getPageNo();
        MPJQueryWrapper<Member> memberMPJQueryWrapper = new MPJQueryWrapper<>();

        memberMPJQueryWrapper.selectAll(Member.class)
                .select("D.name as d_name","P.name as p_name")
                .leftJoin("?_system_auth_dept D ON D.id = t.department".replace("?_", GlobalConfig.tablePrefix))
                .leftJoin("?_system_auth_post P ON P.id = t.level".replace("?_",GlobalConfig.tablePrefix))
                .eq("t.is_show",1)
                .isNull("t.delete_time")
                .orderByAsc(Arrays.asList("t.sort","t.id"));
        IPage<MemberListedVo> iPage = memberMapper.selectJoinPage(new Page<>(pageNo,pageSize), MemberListedVo.class,memberMPJQueryWrapper);
        LinkedList<MemberListedVo> memberListedVos = new LinkedList<>();
        for (MemberListedVo record : iPage.getRecords()) {
            MemberListedVo memberListedVo = new MemberListedVo();
            BeanUtils.copyProperties(record,memberListedVo);
            memberListedVo.setAvatar(UrlUtils.toAbsoluteUrl(record.getAvatar()));
            memberListedVos.add(memberListedVo);
        }
        return PageResult.iPageHandle(iPage.getTotal(),iPage.getCurrent(),iPage.getSize(),memberListedVos);
    }

    @Override
    public MemberDetailVo detail(Integer memberId) {
        MPJQueryWrapper<Member> memberMPJQueryWrapper = new MPJQueryWrapper<>();
        memberMPJQueryWrapper
                .selectAll(Member.class)
                .select("D.name as d_name","P.name as p_name")
                .leftJoin("?_system_auth_dept D ON D.id = t.department".replace("?_",GlobalConfig.tablePrefix))
                .leftJoin("?_system_auth_post P ON P.id = t.level".replace("?_",GlobalConfig.tablePrefix))
                .eq("t.is_show",1)
                .isNull("t.delete_time")
                .eq("t.id",memberId)
                .last("limit 1");
        MemberDetailVo memberDetailVo = memberMapper.selectJoinOne(MemberDetailVo.class, memberMPJQueryWrapper);
        Assert.notNull(memberDetailVo,"数据为空");
        memberDetailVo.setAvatar(UrlUtils.toAbsoluteUrl(memberDetailVo.getAvatar()));
        return memberDetailVo;
    }
}
