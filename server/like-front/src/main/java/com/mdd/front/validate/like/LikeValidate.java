package com.mdd.front.validate.like;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "点赞验证器")
public class LikeValidate implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "业务id不能为空")
    @ApiModelProperty(value = "业务id")
    private Integer serviceId;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型")
    private Integer type;
}
