/**
*Name: pt_user
*Author: Caicai
*Date: 2019-06-05 17:45:04
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.service;

import com.wolf.dearcc.pojo.PtUser;

public interface PtUserService extends BaseService<PtUser,Integer> {

    PtUser login(String email ,String pswd);
}
