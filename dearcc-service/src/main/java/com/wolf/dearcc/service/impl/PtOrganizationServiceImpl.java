/**
*Name: pt_organization
*Author: Caicai
*Date: 2019-06-05 16:10:04
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtOrganizationMapper;
import com.wolf.dearcc.pojo.PtOrganization;
import com.wolf.dearcc.service.PtOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PtOrganizationServiceImpl extends BaseServiceImpl<PtOrganization,Integer> implements PtOrganizationService {
    @Autowired
    private PtOrganizationMapper myMapper;


}
