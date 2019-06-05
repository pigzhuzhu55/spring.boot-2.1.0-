package com.wolf.dearcc.manager.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 * Shiro token
 * 
 *
 * 
 */
public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;

	public ShiroToken(String account, String pswd) {
		super(account,pswd);
		this.pswd = pswd ;
	}
	
	
	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pswd ;

	public String getPswd() {
		return pswd;
	}


	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	
	
	
	
}
