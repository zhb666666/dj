package com.mdd.front.validate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel("用户问卷答卷验证器")
public class InsertUserAskValidate implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("问卷id")
    private Integer questionnaireId;

    @ApiModelProperty("答案")
    @TableField(typeHandler = GsonTypeHandler.class)
    private Map<String ,Object > answerJson;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("删除时间")
    private Long deleteTime;
}
