package com.mdd.common.mapper;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends IBaseMapper<Comment> {
}
