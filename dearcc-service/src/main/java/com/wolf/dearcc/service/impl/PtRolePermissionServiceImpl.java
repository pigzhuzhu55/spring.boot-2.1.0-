/**
*Name: pt_role_permission
*Author: Caicai
*Date: 2019-06-05 16:09:56
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtRolePermissionMapper;
import com.wolf.dearcc.pojo.PtRolePermission;
import com.wolf.dearcc.service.PtRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PtRolePermissionServiceImpl extends BaseServiceImpl<PtRolePermission,Integer> implements PtRolePermissionService {
    @Autowired
    private PtRolePermissionMapper myMapper;


}
