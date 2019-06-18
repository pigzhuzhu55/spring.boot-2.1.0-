package com.wolf.dearcc.manager.core.shiro.bo;

import com.wolf.dearcc.pojo.PtUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public UUser() {
    }

    public UUser(PtUser user) {
        this.setId(user.getId());
        this.setUserName(user.getUserName());
        this.setTelephone(user.getTelephone());
        this.setPassword(user.getPassword());
        this.setLastLoginTime(user.getLastLoginTime());
        this.setDeleteFlag(user.getDeleteFlag());
    }

    private Integer id;

    private String userName;

    private String password;

    private String telephone;

    private java.util.Date lastLoginTime;

    private Short deleteFlag;

    private Set<String> roles;

    private Set<String> stringPermissions;
}
