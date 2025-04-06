package com.mdd.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("党员实体")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
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

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除时间")
    private Long deleteTime;

}