package com.wolf.dearcc.dto.front;

import io.swagger.annotations.ApiModelProperty;

public class SignInDto {

    @ApiModelProperty(name = "ssid", value = "用户唯一标识")
    private String ssid;

    @ApiModelProperty(name = "token", value = "登陆返回token")
    private String token;
}
