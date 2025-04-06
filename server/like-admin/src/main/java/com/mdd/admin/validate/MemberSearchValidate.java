package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("党员搜素参数")
public class MemberSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "党员级别")
    private Integer level;

    @ApiModelProperty(value = "党支部")
    private Integer department;

    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

}
