package com.mdd.admin.controller;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAudioCateService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.AudioCateCreateValidate;
import com.mdd.admin.validate.AudioCateUpdateValidate;
import com.mdd.admin.validate.AudioCateSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.AudioCateListedVo;
import com.mdd.admin.vo.AudioCateDetailVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/AudioCate")
@Api(tags = "有声读物类目管理")
public class AudioCateController {

    @Resource
    IAudioCateService iAudioCateService;

    @GetMapping("/all")
    @ApiOperation(value = "所有有声读物列表")
    public AjaxResult<List<AudioCateListedVo>> all(){
        List<AudioCateListedVo> all = iAudioCateService.all();
        return AjaxResult.success(all);
    }

    @GetMapping("/list")
    @ApiOperation(value="有声读物类目列表")
    public AjaxResult<PageResult<AudioCateListedVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated AudioCateSearchValidate searchValidate) {
        PageResult<AudioCateListedVo> list = iAudioCateService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="有声读物类目详情")
    public AjaxResult<AudioCateDetailVo> detail(@Validated @IDMust() @RequestParam("id") Integer id) {
        AudioCateDetailVo detail = iAudioCateService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "有声读物类目新增")
    @PostMapping("/add")
    @ApiOperation(value="有声读物类目新增")
    public AjaxResult<Object> add(@Validated @RequestBody AudioCateCreateValidate createValidate) {
        iAudioCateService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "有声读物类目编辑")
    @PostMapping("/edit")
    @ApiOperation(value="有声读物类目编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AudioCateUpdateValidate updateValidate) {
        iAudioCateService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "有声读物类目删除")
    @PostMapping("/del")
    @ApiOperation(value="有声读物类目删除")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iAudioCateService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
