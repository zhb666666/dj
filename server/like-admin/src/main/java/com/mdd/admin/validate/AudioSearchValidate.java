package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("有声读物搜素参数")
public class AudioSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "有声读物类目")
    private Integer cid;

    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    @ApiModelProperty(value = "标题")
    private String title;

}
