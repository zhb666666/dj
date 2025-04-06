package com.mdd.admin.controller;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAudioService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.AudioCreateValidate;
import com.mdd.admin.validate.AudioUpdateValidate;
import com.mdd.admin.validate.AudioSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.AudioListedVo;
import com.mdd.admin.vo.AudioDetailVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/Audio")
@Api(tags = "有声读物管理")
public class AudioController {

    @Resource
    IAudioService iAudioService;

    @GetMapping("/list")
    @ApiOperation(value="有声读物列表")
    public AjaxResult<PageResult<AudioListedVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated AudioSearchValidate searchValidate) {
        PageResult<AudioListedVo> list = iAudioService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="有声读物详情")
    public AjaxResult<AudioDetailVo> detail(@Validated @IDMust() @RequestParam("id") Integer id) {
        AudioDetailVo detail = iAudioService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "有声读物新增")
    @PostMapping("/add")
    @ApiOperation(value="有声读物新增")
    public AjaxResult<Object> add(@Validated @RequestBody AudioCreateValidate createValidate) {
        iAudioService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "有声读物编辑")
    @PostMapping("/edit")
    @ApiOperation(value="有声读物编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AudioUpdateValidate updateValidate) {
        iAudioService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "有声读物删除")
    @PostMapping("/del")
    @ApiOperation(value="有声读物删除")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iAudioService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
