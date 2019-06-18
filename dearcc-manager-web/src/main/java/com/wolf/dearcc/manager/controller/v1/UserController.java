package com.wolf.dearcc.manager.controller.v1;

import com.wolf.dearcc.common.model.ApiResult;
import com.wolf.dearcc.dto.form.SignInForm;
import com.wolf.dearcc.dto.front.SignInDto;
import com.wolf.dearcc.pojo.PtUser;
import com.wolf.dearcc.service.PtOrganizationService;
import com.wolf.dearcc.service.PtUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value="user",tags={"2、用户认证"},position = 2)
@RestController
@RequestMapping(value = {"/api/v1/user"})
public class UserController {

    @Autowired
    private PtUserService ptUserService;

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    @ResponseBody()
    @RequiresPermissions("lesson:curriculum:add")
    public ApiResult<PtUser> one() {

        PtUser user = ptUserService.queryOneByPrimaryKey(1);

        //PtOrganization obj =   ptOrganizationService.queryOneByPrimaryKey(1);

        return ApiResult.Success(user);
    }
}
