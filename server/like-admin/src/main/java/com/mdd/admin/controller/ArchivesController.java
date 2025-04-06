package com.mdd.admin.controller;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IArchivesService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.ArchivesCreateValidate;
import com.mdd.admin.validate.ArchivesUpdateValidate;
import com.mdd.admin.validate.ArchivesSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ArchivesListedVo;
import com.mdd.admin.vo.ArchivesDetailVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/archives")
@Api(tags = "党员档案管理")
public class ArchivesController {

    @Resource
    IArchivesService iArchivesService;

    @GetMapping("/list")
    @ApiOperation(value="党员档案列表")
    public AjaxResult<PageResult<ArchivesListedVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated ArchivesSearchValidate searchValidate) {
        PageResult<ArchivesListedVo> list = iArchivesService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="党员档案详情")
    public AjaxResult<ArchivesDetailVo> detail(@Validated @IDMust() @RequestParam("id") Integer id) {
        ArchivesDetailVo detail = iArchivesService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "党员档案新增")
    @PostMapping("/add")
    @ApiOperation(value="党员档案新增")
    public AjaxResult<Object> add(@Validated @RequestBody ArchivesCreateValidate createValidate) {
        iArchivesService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "党员档案编辑")
    @PostMapping("/edit")
    @ApiOperation(value="党员档案编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody ArchivesUpdateValidate updateValidate) {
        iArchivesService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "党员档案删除")
    @PostMapping("/del")
    @ApiOperation(value="党员档案删除")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iArchivesService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
