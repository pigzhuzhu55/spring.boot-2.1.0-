package com.wolf.dearcc.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class SignInForm {
    @NotBlank(message = "用户名不能为空哟")
    @JsonProperty("userName")
    @ApiModelProperty(name = "userName", value = "用户名",required = true)
    private String userName;

    @NotBlank(message = "密码不能为空哟")
    @JsonProperty("password")
    @ApiModelProperty(name = "password", value = "密码",position = 1,required = true)
    private String password;

    @JsonProperty("type")
    @ApiModelProperty(name = "type", value = "登陆方式 account",position = 2)
    private String type;
}
