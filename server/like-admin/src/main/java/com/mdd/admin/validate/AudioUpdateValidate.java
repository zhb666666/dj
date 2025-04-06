package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 有声读物参数
 * @author LikeAdmin
 */
@Data
@ApiModel("有声读物更新参数")
public class AudioUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传且需大于0")
    @ApiModelProperty(value = "id")
    private Integer id;

    @NotNull(message = "cid参数缺失")
    @ApiModelProperty(value = "有声读物类目")
    private Integer cid;

    @NotNull(message = "title参数缺失")
    @ApiModelProperty(value = "文章标题")
    private String title;

    @NotNull(message = "src参数缺失")
    @ApiModelProperty(value = "音频地址")
    private String src;

    @NotNull(message = "isShow参数缺失")
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    @NotNull(message = "sort参数缺失")
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
