package com.mdd.front.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("回复的用户Vo")
public class UserReplyVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户编号")
    private Integer sn;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;
}
