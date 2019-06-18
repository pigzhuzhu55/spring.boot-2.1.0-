package com.wolf.dearcc.manager.core.shiro.bo;

import com.wolf.dearcc.pojo.PtUser;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * Session  + User Bo
 *
 */
@Data
public class UserOnlineBo extends UUser implements Serializable {

	private static final long serialVersionUID = 1L;
	public UserOnlineBo(PtUser user) {
		super(user);
	}
	public UserOnlineBo(UUser user) {
		this.setId(user.getId());
		this.setUserName(user.getUserName());
		this.setTelephone(user.getTelephone());
		this.setPassword(user.getPassword());
		this.setLastLoginTime(user.getLastLoginTime());
		this.setDeleteFlag(user.getDeleteFlag());
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
