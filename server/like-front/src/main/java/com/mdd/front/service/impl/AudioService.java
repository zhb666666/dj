package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.Audio;
import com.mdd.common.mapper.AudioMapper;
import com.mdd.common.util.UrlUtils;
import com.mdd.front.service.IAudioService;
import com.mdd.front.vo.audio.AudioDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AudioService implements IAudioService {
    @Resource
    AudioMapper audioMapper;
    @Override
    public List<AudioDetailVo> listForCate(Integer cateId) {
        List<Audio> audio = audioMapper.selectList(
                new QueryWrapper<Audio>()
                        .eq("is_show", 1)
                        .eq("cid", cateId)
                        .orderByDesc(Arrays.asList("sort", "id"))
        );
        LinkedList<AudioDetailVo> audioDetailVos = new LinkedList<>();
        for (Audio item : audio) {
            AudioDetailVo audioDetailVo = new AudioDetailVo();
            BeanUtils.copyProperties(item,audioDetailVo);
            audioDetailVos.add(audioDetailVo);
        }
        return audioDetailVos;
    }
}
