/**
*Name: pt_permission
*Author: Caicai
*Date: 2019-06-05 16:09:51
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtPermissionMapper;
import com.wolf.dearcc.pojo.PtPermission;
import com.wolf.dearcc.service.PtPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PtPermissionServiceImpl extends BaseServiceImpl<PtPermission,Integer> implements PtPermissionService {
    @Autowired
    private PtPermissionMapper myMapper;


}
