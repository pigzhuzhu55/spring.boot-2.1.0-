/**
*Name: pt_role_permission
*Author: Caicai
*Date: 2019-06-05 16:09:56
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
 
 
@Data
@Table(name = "pt_role_permission")
public class PtRolePermission {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "permission_id")
    private Integer permissionId;
    
    public static class ${
        public static String id="id";
        public static String roleId="roleId";
        public static String permissionId="permissionId";
    }
}
 
