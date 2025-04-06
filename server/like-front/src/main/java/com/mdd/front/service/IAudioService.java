package com.mdd.front.service;

import com.mdd.front.vo.audio.AudioDetailVo;

import java.util.List;

public interface IAudioService {
    List<AudioDetailVo> listForCate(Integer cateId);
}
