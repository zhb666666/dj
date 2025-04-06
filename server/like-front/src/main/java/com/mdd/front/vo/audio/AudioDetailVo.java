package com.mdd.front.vo.audio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "音频详情Vo")
public class AudioDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "有声读物类目")
    private Integer cid;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "音频地址")
    private String src;

    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
