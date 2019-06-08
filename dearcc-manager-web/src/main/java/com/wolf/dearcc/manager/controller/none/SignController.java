package com.wolf.dearcc.manager.controller.none;

import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.dto.form.SignInForm;
import com.wolf.dearcc.dto.front.SignInDto;
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

import javax.validation.Valid;

@Api(value="sign",tags={"2、登陆管理"},position = 2)
@RestController
@RequestMapping(value = {"/api/none/sign"})
public class SignController {

    @Autowired
    @Qualifier("redisTemplate2")
    private RedisTemplate redis2;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redis1;


    @Autowired
    private PtOrganizationService ptOrganizationService;

    @Autowired
    private PtUserService ptUserService;

    @ApiOperation(value = "登陆系统")
    @RequestMapping(value = "/in",method = RequestMethod.POST)
    @ResponseBody()
    public ApiResult<SignInDto> LoginIn(@RequestBody @Valid SignInForm form) {


        ValueOperations<String,Object> operations = redis2.opsForValue();
        operations.set("test1","12321");

        Object o = operations.get("test1");

         PtUser user = ptUserService.queryOneByPrimaryKey(1);

        //byte[] tt =  ProtostuffUtil.serializer(user);

         user = TokenManager.login(user, Boolean.TRUE);

        //PtOrganization obj =   ptOrganizationService.queryOneByPrimaryKey(1);

        return ApiResult.Success(user);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    @ResponseBody()
    public ApiResult<PtUser> oNE() {

        PtUser user = ptUserService.queryOneByPrimaryKey(1);

        //PtOrganization obj =   ptOrganizationService.queryOneByPrimaryKey(1);

        return ApiResult.Success(user);
    }
}
