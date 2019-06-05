/**
*Name: pt_user
*Author: Caicai
*Date: 2019-06-05 17:45:04
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service.impl;

import com.wolf.dearcc.dao.mapper.source1.PtUserMapper;
import com.wolf.dearcc.pojo.PtUser;
import com.wolf.dearcc.service.PtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class PtUserServiceImpl extends BaseServiceImpl<PtUser,Integer> implements PtUserService {
    @Autowired
    private PtUserMapper myMapper;


    public PtUser login(String account , String password) {

        Example example = new Example(PtUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(PtUser.$.account,account);
        criteria.andEqualTo(PtUser.$.password,password);

        PtUser user = myMapper.selectOneByExample(example);
        
        return user;
    }
}
