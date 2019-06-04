package com.wolf.dearcc.manager.controller.none;

import com.wolf.dearcc.dto.form.SignInForm;
import com.wolf.dearcc.dto.front.SignInDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.wolf.dearcc.common.model.*;

import javax.validation.Valid;

@Api(value="sign",tags={"2、登陆管理"},position = 2)
@RestController
@RequestMapping(value = {"/api/none/sign"})
public class SignController {

    @ApiOperation(value = "登陆系统")
    @RequestMapping(value = "/in",method = RequestMethod.POST)
    @ResponseBody()
    public ApiResult<SignInDto> LoginIn(@RequestBody @Valid SignInForm form) {
        return ApiResult.Success();
    }
}
