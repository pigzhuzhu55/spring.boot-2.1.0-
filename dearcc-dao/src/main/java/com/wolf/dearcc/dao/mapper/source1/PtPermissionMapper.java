/**
*Name: pt_permission
*Author: Caicai
*Date: 2019-06-05 16:09:51
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.dao.mapper.source1;

import com.wolf.dearcc.dao.MyMapper;
import com.wolf.dearcc.pojo.PtPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface PtPermissionMapper extends MyMapper<PtPermission> {

    /**
     * 根据用户ID获取权限的Set集合
     * @param userId
     * @return
     */
    @Select("select p.shiro_str from pt_permission p, pt_role_permission rp, pt_user_role ur " +
            " where ur.user_id = ${userId}" +
            " and p.id = rp.permission_id" +
            " and rp.role_id = ur.role_id")
     Set<String> findPermissionByUserId(@Param("userId") Integer userId);
}
