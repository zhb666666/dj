package com.mdd.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("党员档案详情Vo")
public class ArchivesDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "党员id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idcard;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    private String birthdayTime;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "毕业院校")
    private String college;

    @ApiModelProperty(value = "党籍状态")
    private Integer archivesStatus;

    @ApiModelProperty(value = "党支部")
    private Integer department;
    @ApiModelProperty(value = "缴纳党费金额")
    private BigDecimal partyFee;

    @ApiModelProperty(value = "联系电话")
    private String contact;

    @ApiModelProperty(value = "是否为流动党员")
    private Integer isFlow;

    @ApiModelProperty(value = "婚姻状态")
    private Integer isMerry;

    @ApiModelProperty(value = "加入党组织时间")
    private String joinTime;

    @ApiModelProperty(value = "成为正式党员时间")
    private String realTime;

    @ApiModelProperty(value = "主要经历")
    private String see;

    @ApiModelProperty(value = "发展党员情况")
    private String situation;


}
