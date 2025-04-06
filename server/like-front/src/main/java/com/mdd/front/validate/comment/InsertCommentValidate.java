package com.mdd.front.validate.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "添加评论验证器")
public class InsertCommentValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    @ApiModelProperty("服务id")
    private Integer serviceId;

    @NotNull
    @Length(min = 4,max = 191,message = "评论内容长度在4~191个字符之间")
    @ApiModelProperty("评论内容")
    private String content;

    @NotNull
    @ApiModelProperty("评论类型")
    private Integer type;

}
