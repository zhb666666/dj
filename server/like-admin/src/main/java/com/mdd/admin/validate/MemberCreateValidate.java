package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;

@Data
@ApiModel("党员创建参数")
public class MemberCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotNull(message = "avatar参数缺失")
    @ApiModelProperty(value = "党员头像")
    private String avatar;

    @NotNull(message = "level参数缺失")
    @ApiModelProperty(value = "党员级别")
    private Integer level;

    @NotNull(message = "department参数缺失")
    @ApiModelProperty(value = "党支部")
    private Integer department;

    @NotNull(message = "description参数缺失")
    @ApiModelProperty(value = "个人简介")
    private String description;

    @NotNull(message = "isShow参数缺失")
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    @NotNull(message = "sort参数缺失")
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
