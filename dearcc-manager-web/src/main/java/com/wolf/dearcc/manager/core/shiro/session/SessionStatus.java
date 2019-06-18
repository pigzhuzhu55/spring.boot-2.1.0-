package com.wolf.dearcc.manager.core.shiro.session;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;

/**
 * Session 状态 VO
 *
 */
@Data
public class SessionStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	//是否踢出 true:有效，false：踢出。
	private Boolean onlineStatus = Boolean.TRUE;

	public Boolean isOnlineStatus(){
		return onlineStatus;
	}

}
