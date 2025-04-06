package com.mdd.front.service;

import com.mdd.common.core.PageResult;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.audioCate.AudioCateDetailVo;
import com.mdd.front.vo.audioCate.AudioCateListedVo;

public interface IAudioCateService {
    PageResult<AudioCateListedVo> list(PageValidate pageValidate);
    AudioCateDetailVo detail(Integer id);
}
