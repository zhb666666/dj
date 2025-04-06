package com.mdd.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("党员档案列表Vo")
public class ArchivesListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "党员id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "党支部")
    private Integer department;

    @ApiModelProperty(value = "联系电话")
    private String contact;

    @ApiModelProperty(value = "婚姻状态")
    private Integer isMerry;

    @ApiModelProperty(value = "加入党组织时间")
    private String joinTime;

    @ApiModelProperty(value = "党籍状态")
    private Integer archivesStatus;

    @ApiModelProperty(value = "成为正式党员时间")
    private String realTime;

    @ApiModelProperty(value = "主要经历")
    private String see;

    @ApiModelProperty(value = "发展党员情况")
    private String situation;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "用户sn")
    private String userSn;
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "党支部")
    private String dName;

}
