/**
*Name: pt_role
*Author: Caicai
*Date: 2019-06-05 16:09:53
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtRoleMapper;
import com.wolf.dearcc.pojo.PtRole;
import com.wolf.dearcc.service.PtRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PtRoleServiceImpl extends BaseServiceImpl<PtRole,Integer> implements PtRoleService {
    @Autowired
    private PtRoleMapper myMapper;

    public Set<String> findRoleByUserId(Integer userId) {

        return myMapper.findRoleByUserId(userId);
    }
}
