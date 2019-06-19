package com.wolf.dearcc.manager.controller.none;

import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.dto.form.SignInForm;
import com.wolf.dearcc.dto.front.SignInDto;
import com.wolf.dearcc.manager.core.shiro.bo.UUser;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import com.wolf.dearcc.pojo.PtOrganization;
import com.wolf.dearcc.pojo.PtUser;
import com.wolf.dearcc.service.PtOrganizationService;
import com.wolf.dearcc.service.PtUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import com.wolf.dearcc.common.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(value="sign",tags={"1、登陆管理"},position = 1)
@RestController
@RequestMapping(value = {"/api/none/sign"})
public class SignController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private PtOrganizationService ptOrganizationService;

    @Autowired
    private PtUserService ptUserService;

    @ApiOperation(value = "登陆系统")
    @RequestMapping(value = "/in",method = RequestMethod.POST)
    @ResponseBody()
    public ApiResult<SignInDto> loginIn(@RequestBody @Valid SignInForm form) {

        UUser user = TokenManager.login(request,form.getUserName(),form.getPassword(),true);

        return ApiResult.Success(user);
    }

}
