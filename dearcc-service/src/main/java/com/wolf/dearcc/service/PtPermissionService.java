/**
*Name: pt_permission
*Author: Caicai
*Date: 2019-06-05 16:09:51
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service;

import com.wolf.dearcc.pojo.PtPermission;

import java.util.Set;

public interface PtPermissionService extends BaseService<PtPermission,Integer> {

    //根据用户ID查询权限（permission），放入到Authorization里。
    Set<String> findPermissionByUserId(Integer userId);
}
