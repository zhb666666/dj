package com.mdd.front.controller;

import com.mdd.common.core.AjaxResult;
import com.mdd.front.LikeFrontThreadLocal;
import com.mdd.front.service.ILikeService;
import com.mdd.front.validate.like.LikeValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/like")
@Api(value = "点赞管理")
public class LikeController {
    @Resource
    ILikeService iLikeService;
    @PostMapping("like")
    @ApiOperation(value = "给业务点赞")
    public AjaxResult<Object> like(@Validated @RequestBody LikeValidate likeValidate){
        Integer userId = LikeFrontThreadLocal.getUserId();
        iLikeService.like(userId,likeValidate);
        return AjaxResult.success();
    }

    @PostMapping("dislike")
    @ApiOperation(value = "给业务取消点赞")
    public AjaxResult<Object> dislike(@Validated @RequestBody LikeValidate likeValidate){
        Integer userId = LikeFrontThreadLocal.getUserId();
        iLikeService.dislike(userId,likeValidate);
        return AjaxResult.success();
    }

}
