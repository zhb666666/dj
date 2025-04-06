package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.Like;
import com.mdd.common.mapper.LikeMapper;
import com.mdd.front.service.ILikeService;
import com.mdd.front.validate.like.LikeValidate;
import com.mdd.front.vo.like.LikeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeService implements ILikeService {

    @Resource
    LikeMapper likeMapper;
    @Override
    public LikeVo detailForUser(Integer userId, Integer serviceId) {
        LikeVo likeVo = new LikeVo();
        likeVo.setStatus(0);
        Like like = likeMapper.selectOne(
                new QueryWrapper<Like>()
                        .eq("user_id", userId)
                        .eq("service_id", serviceId)
                        .last("limit 1")
        );
        if (null != like){
            BeanUtils.copyProperties(like,likeVo);
        }
        return likeVo;
    }

    /**
     * 统计业务的点赞数
     *
     * @param serviceId 业务id
     * @return Integer
     */
    @Override
    public Long countForService(Integer serviceId) {
        return likeMapper.selectCount(
                new QueryWrapper<Like>()
                        .select("id")
                        .eq("service_id", serviceId)
        );
    }

    @Override
    public void like(Integer userId,LikeValidate likeValidate) {
        Like like = likeMapper.selectOne(
                new QueryWrapper<Like>()
                        .eq("user_id", userId)
                        .eq("service_id", likeValidate.getServiceId())
                        .eq("type", likeValidate.getType())
                        .last("limit 1")
        );
        if (null == like){
            Like newLike = new Like();
            newLike.setUserId(userId);
            newLike.setServiceId(likeValidate.getServiceId());
            newLike.setStatus(1);
            newLike.setType(likeValidate.getType());
            likeMapper.insert(newLike);
        }else{
            like.setStatus(1);
            likeMapper.updateById(like);
        }

    }

    @Override
    public void dislike(Integer userId, LikeValidate likeValidate) {
        Like like = likeMapper.selectOne(
                new QueryWrapper<Like>()
                        .eq("user_id", userId)
                        .eq("service_id", likeValidate.getServiceId())
                        .eq("type", likeValidate.getType())
                        .last("limit 1")
        );
        if (null == like){
            Like newLike = new Like();
            newLike.setUserId(userId);
            newLike.setServiceId(likeValidate.getServiceId());
            newLike.setStatus(0);
            newLike.setType(likeValidate.getType());
            likeMapper.insert(newLike);
        }else{
            like.setStatus(0);
            likeMapper.updateById(like);
        }
    }
}
