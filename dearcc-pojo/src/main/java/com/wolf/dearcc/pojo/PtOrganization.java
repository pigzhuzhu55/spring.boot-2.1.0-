/**
 *Name: pt_organization
 *Author: Caicai
 *Date: 2019-06-04 22:09:22
 *Description: Copyright 2019 智趣
 */
package com.wolf.dearcc.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;


@Data
@Table(name = "pt_organization")
public class PtOrganization {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_type")
    private byte orgType;

    @Column(name = "org_suffix")
    private String orgSuffix;

    @Column(name = "logo")
    private String logo;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "max_campus")
    private Integer maxCampus;

    @Column(name = "max_account")
    private Integer maxAccount;

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
        public static String orgName="orgName";
        public static String orgType="orgType";
        public static String orgSuffix="orgSuffix";
        public static String logo="logo";
        public static String telephone="telephone";
        public static String maxCampus="maxCampus";
        public static String maxAccount="maxAccount";
        public static String createTime="createTime";
        public static String createUid="createUid";
        public static String modifyTime="modifyTime";
        public static String modifyUid="modifyUid";
        public static String deleteFlag="deleteFlag";
    }
}

