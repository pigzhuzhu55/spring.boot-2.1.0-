package com.wolf.dearcc.manager.core.shiro.bo;


import com.wolf.dearcc.pojo.PtUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Session  + User Bo
 * @author sojson.com
 *
 */
@Data
public class UserOnlineBo extends PtUser implements Serializable {

	private static final long serialVersionUID = 1L;
	public UserOnlineBo(PtUser user) {
		this.setId(user.getId());
		this.setUserName(user.getUserName());
		this.setTelephone(user.getTelephone());
		this.setPassword(user.getPassword());
		this.setCreateTime(user.getCreateTime());
		this.setLastLoginTime(user.getLastLoginTime());
	}
	//Session Id
	private String sessionId;
	//Session Host
	private String host;
	//Session创建时间
	private Date startTime;
	//Session最后交互时间
	private Date lastAccess;
	//Session timeout
	private long timeout;
	//session 是否踢出
	private boolean sessionStatus = Boolean.TRUE;
	

	
	
	

}
