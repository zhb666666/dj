package com.mdd.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("访问记录实体")
public class AccessRecord implements Serializable {
    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}
