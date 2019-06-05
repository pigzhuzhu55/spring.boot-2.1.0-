/**
*Name: pt_role
*Author: Caicai
*Date: 2019-06-05 16:09:53
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
 
 
@Data
@Table(name = "pt_role")
public class PtRole {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "create_time")
    private java.util.Date createTime;

    @Column(name = "create_uid")
    private Integer createUid;

    @Column(name = "modify_time")
    private java.util.Date modifyTime;

    @Column(name = "modify_uid")
    private Integer modifyUid;

    @Column(name = "delete_flag")
    private Short deleteFlag;
    
    public static class ${
        public static String id="id";
        public static String roleName="roleName";
        public static String createTime="createTime";
        public static String createUid="createUid";
        public static String modifyTime="modifyTime";
        public static String modifyUid="modifyUid";
        public static String deleteFlag="deleteFlag";
    }
}
 
