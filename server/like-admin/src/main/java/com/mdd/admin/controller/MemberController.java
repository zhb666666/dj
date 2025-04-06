package com.mdd.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IMemberService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.MemberCreateValidate;
import com.mdd.admin.validate.MemberUpdateValidate;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.MemberListedVo;
import com.mdd.admin.vo.MemberDetailVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/member")
@Api(tags = "党员管理")
public class MemberController {

    @Resource
    IMemberService iMemberService;

    @GetMapping("/list")
    @ApiOperation(value="党员列表")
    public AjaxResult<PageResult<MemberListedVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated MemberSearchValidate searchValidate) {
        PageResult<MemberListedVo> list = iMemberService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="党员详情")
    public AjaxResult<MemberDetailVo> detail(@Validated @IDMust() @RequestParam("id") Integer id) {
        MemberDetailVo detail = iMemberService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "党员新增")
    @PostMapping("/add")
    @ApiOperation(value="党员新增")
    public AjaxResult<Object> add(@Validated @RequestBody MemberCreateValidate createValidate) {
        iMemberService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "党员编辑")
    @PostMapping("/edit")
    @ApiOperation(value="党员编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody MemberUpdateValidate updateValidate) {
        iMemberService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "党员删除")
    @PostMapping("/del")
    @ApiOperation(value="党员删除")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iMemberService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
