package com.mdd.front.service;

import com.mdd.common.core.PageResult;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.member.MemberDetailVo;
import com.mdd.front.vo.member.MemberListedVo;

import java.util.List;

public interface IMemberService {
    PageResult<MemberListedVo> list(PageValidate pageValidate);

    MemberDetailVo detail(Integer memberId);
}
