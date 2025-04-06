package com.mdd.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "点赞实体")
public class Like implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("业务id")
    private Integer serviceId;

    @ApiModelProperty("点赞类型")
    private Integer type;

    @ApiModelProperty("是否点赞")
    private Integer status;


    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;
}
