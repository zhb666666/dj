package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 有声读物类目参数
 * @author LikeAdmin
 */
@Data
@ApiModel("有声读物类目更新参数")
public class AudioCateUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Integer id;

    @NotNull(message = "title参数缺失")
    @ApiModelProperty(value = "标题")
    private String title;

    @NotNull(message = "image参数缺失")
    @ApiModelProperty(value = "封面")
    private String image;

    @NotNull(message = "sort参数缺失")
    @ApiModelProperty(value = "排序")
    private Long sort;

}
