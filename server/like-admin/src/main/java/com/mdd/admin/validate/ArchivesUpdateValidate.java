package com.mdd.admin.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 党员档案参数
 * @author LikeAdmin
 */
@Data
@ApiModel("党员档案更新参数")
public class ArchivesUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传且需大于0")
    @ApiModelProperty(value = "党员id")
    private Integer id;

    @NotNull(message = "userId参数缺失")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotNull(message = "idcard参数缺失")
    @ApiModelProperty(value = "身份证")
    private String idcard;

    @NotNull(message = "gender参数缺失")
    @ApiModelProperty(value = "性别")
    private Integer gender;

    @NotNull(message = "birthdayTime参数缺失")
    @ApiModelProperty(value = "出生日期")
    private String birthdayTime;

    @NotNull(message = "nation参数缺失")
    @ApiModelProperty(value = "民族")
    private String nation;

    @NotNull(message = "nativePlace参数缺失")
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @NotNull(message = "address参数缺失")
    @ApiModelProperty(value = "地址")
    private String address;

    @NotNull(message = "education参数缺失")
    @ApiModelProperty(value = "学历")
    private String education;

    @NotNull(message = "college参数缺失")
    @ApiModelProperty(value = "毕业院校")
    private String college;

    @NotNull(message = "archivesStatus参数缺失")
    @ApiModelProperty(value = "党籍状态")
    private Integer archivesStatus;

    @NotNull(message = "department参数缺失")
    @ApiModelProperty(value = "党支部")
    private Integer department;

    @ApiModelProperty(value = "缴纳党费金额")
    private BigDecimal partyFee;

    @NotNull(message = "contact参数缺失")
    @ApiModelProperty(value = "联系电话")
    private String contact;

    @NotNull(message = "isFlow参数缺失")
    @ApiModelProperty(value = "是否为流动党员")
    private Integer isFlow;

    @NotNull(message = "isMerry参数缺失")
    @ApiModelProperty(value = "婚姻状态")
    private Integer isMerry;

    @NotNull(message = "joinTime参数缺失")
    @ApiModelProperty(value = "加入党组织时间")
    private String joinTime;

    @NotNull(message = "realTime参数缺失")
    @ApiModelProperty(value = "成为正式党员时间")
    private String realTime;

    @NotNull(message = "see参数缺失")
    @ApiModelProperty(value = "主要经历")
    private String see;

    @NotNull(message = "situation参数缺失")
    @ApiModelProperty(value = "发展党员情况")
    private String situation;

}
