/**
*Name: pt_permission
*Author: Caicai
*Date: 2019-06-05 16:09:51
*Description: Copyright 2019 智趣互联
*/ 
package com.wolf.dearcc.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
 
 
@Data
@Table(name = "pt_permission")
public class PtPermission {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "perm_name")
    private String permName;

    @Column(name = "perm_type")
    private byte permType;

    @Column(name = "icon")
    private String icon;

    @Column(name = "url")
    private String url;

    @Column(name = "shiro_str")
    private String shiroStr;

    @Column(name = "sort")
    private byte sort;

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
        public static String pid="pid";
        public static String permName="permName";
        public static String permType="permType";
        public static String icon="icon";
        public static String url="url";
        public static String shiroStr="shiroStr";
        public static String sort="sort";
        public static String createTime="createTime";
        public static String createUid="createUid";
        public static String modifyTime="modifyTime";
        public static String modifyUid="modifyUid";
        public static String deleteFlag="deleteFlag";
    }
}
 
