/**
*Name: pt_user_role
*Author: Caicai
*Date: 2019-06-05 16:10:01
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
 
 
@Data
@Table(name = "pt_user_role")
public class PtUserRole {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
    
    public static class ${
        public static String id="id";
        public static String userId="userId";
        public static String roleId="roleId";
    }
}
 
