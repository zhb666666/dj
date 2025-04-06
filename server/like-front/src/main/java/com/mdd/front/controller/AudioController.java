package com.mdd.front.controller;

import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.front.service.IAudioCateService;
import com.mdd.front.service.IAudioService;
import com.mdd.front.vo.audio.AudioDetailVo;
import com.mdd.front.vo.audioCate.AudioCateDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/audio")
@Api(tags = "音频管理")
public class AudioController {
    @Resource
    IAudioService iAudioService;
    @Resource
    IAudioCateService iAudioCateService;

    @NotLogin
    @GetMapping("list")
    @ApiOperation(value = "音频列表和分类详情")
    public AjaxResult<HashMap<String,Object>> list(@RequestParam("cid") Integer cid){
        List<AudioDetailVo> audioDetailVos = iAudioService.listForCate(cid);
        AudioCateDetailVo detail = iAudioCateService.detail(cid);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("cate",detail);
        stringObjectHashMap.put("list",audioDetailVos);
        return AjaxResult.success(stringObjectHashMap);
    }
}
