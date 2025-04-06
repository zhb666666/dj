package com.mdd.common.mapper;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * 党员Mapper
 * @author LikeAdmin
 */
@Mapper
public interface MemberMapper extends IBaseMapper<Member> {
}
