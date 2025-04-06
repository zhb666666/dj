package com.mdd.front.service;

import com.mdd.front.validate.like.LikeValidate;
import com.mdd.front.vo.like.LikeVo;

public interface ILikeService {

    /**
     * 为用户获取点赞详情
     * @param userId 用户id
     * @param serviceId 业务id
     * @return LikeVo
     */
    LikeVo detailForUser(Integer userId,Integer serviceId);

    /**
     * 统计业务的点赞数
     * @param serviceId 业务id
     * @return Integer
     */
    Long countForService(Integer serviceId);

    void like(Integer userId,LikeValidate likeValidate);

    void dislike(Integer userId,LikeValidate likeValidate);

}
