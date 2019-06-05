/**
*Name: pt_user_role
*Author: Caicai
*Date: 2019-06-05 16:10:01
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtUserRoleMapper;
import com.wolf.dearcc.pojo.PtUserRole;
import com.wolf.dearcc.service.PtUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PtUserRoleServiceImpl extends BaseServiceImpl<PtUserRole,Integer> implements PtUserRoleService {
    @Autowired
    private PtUserRoleMapper myMapper;


}
