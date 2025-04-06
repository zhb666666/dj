package com.mdd.front.controller;

import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.front.service.IMemberService;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.member.MemberDetailVo;
import com.mdd.front.vo.member.MemberListedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/member")
@Api(tags = "党员管理")
public class MemberController {
    @Resource
    IMemberService iMemberService;

    @NotLogin
    @GetMapping("list")
    @ApiOperation(value = "党员列表")
    public AjaxResult<PageResult<MemberListedVo>> list(PageValidate pageValidate){
        PageResult<MemberListedVo> list = iMemberService.list(pageValidate);
        return AjaxResult.success(list);
    }
    @NotLogin
    @GetMapping("detail")
    @ApiOperation(value = "党员详情")
    public AjaxResult<MemberDetailVo> detail(@RequestParam("id") Integer memberId){
        MemberDetailVo detail = iMemberService.detail(memberId);
        return AjaxResult.success(detail);
    }
}
