package com.mdd.front.controller;

import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.front.service.IAudioCateService;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.audioCate.AudioCateListedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/audioCate")
@Api(tags = "音频分类管理")
public class AudioCateController {
    @Resource
    IAudioCateService iAudioCateService;
    @NotLogin
    @GetMapping("list")
    @ApiOperation(value = "音频分类列表分页")
    public AjaxResult<PageResult<AudioCateListedVo>> list(@Validated PageValidate pageValidate){
        PageResult<AudioCateListedVo> list = iAudioCateService.list(pageValidate);
        return AjaxResult.success(list);
    }

}
