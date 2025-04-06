package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 党员参数
 * @author LikeAdmin
 */
@Data
@ApiModel("党员更新参数")
public class MemberUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传且需大于0")
    @ApiModelProperty(value = "id")
    private Integer id;

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
