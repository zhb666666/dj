package com.mdd.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("党员详情Vo")
public class MemberDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "党员头像")
    private String avatar;

    @ApiModelProperty(value = "党员级别")
    private Integer level;

    @ApiModelProperty(value = "党支部")
    private Integer department;

    @ApiModelProperty(value = "个人简介")
    private String description;

    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
