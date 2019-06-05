/**
*Name: pt_role
*Author: Caicai
*Date: 2019-06-05 16:09:53
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service;

import com.wolf.dearcc.pojo.PtRole;

import java.util.Set;

public interface PtRoleService extends BaseService<PtRole,Integer> {

    //根据用户ID查询角色（role），放入到Authorization里。
    Set<String> findRoleByUserId(Integer userId);
}
