package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("党员档案搜素参数")
public class ArchivesSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idcard;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "党支部")
    private Integer department;

    @ApiModelProperty(value = "联系电话")
    private String contact;

    @ApiModelProperty(value = "婚姻状态")
    private Integer isMerry;

    @ApiModelProperty(value = "加入党组织时间")
    private Integer joinTime;

    @ApiModelProperty(value = "成为正式党员时间")
    private Integer realTime;

    @ApiModelProperty(value = "主要经历")
    private String see;

    @ApiModelProperty(value = "发展党员情况")
    private String situation;

}
