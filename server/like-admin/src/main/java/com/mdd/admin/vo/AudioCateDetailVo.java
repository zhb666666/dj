package com.mdd.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("有声读物类目详情Vo")
public class AudioCateDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String image;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "是否显示")
    private Integer isShow;


}
