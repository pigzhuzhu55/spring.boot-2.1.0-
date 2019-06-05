/**
*Name: pt_role
*Author: Caicai
*Date: 2019-06-05 16:09:53
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.dao.mapper.source1;

import com.wolf.dearcc.dao.MyMapper;
import com.wolf.dearcc.pojo.PtRole;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface PtRoleMapper extends MyMapper<PtRole> {

    /**
     *
     * 根据用户ID获取角色类型的Set集合
     * @param userId
     * @return
     */
    @Select("SELECT r.role_type from pt_role r,pt_user_role ur" +
            " where ur.role_id = r.id" +
            " and ur.user_id = ${userId}")
    Set<String> findRoleByUserId(@Param("userId") Integer userId);

}
