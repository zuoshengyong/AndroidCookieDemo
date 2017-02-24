package com.example.cookietest;

/**
 * 登录结果实体
 * @author lenovo
 *
 */
public class LoginResult {

	/**状态*/
	public String status;
	/**结果码*/
	public int code;
	
	public User results;
	
	public class User {
		public String username;
		
		public String userid;
	}
}
