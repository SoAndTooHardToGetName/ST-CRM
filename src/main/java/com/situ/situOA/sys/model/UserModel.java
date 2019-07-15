package com.situ.situOA.sys.model;

import java.util.List;

import tools.base.model.BaseModel;

/**
 * 用户
 * 
 * userName-用户名 userPass-密码 roleCode-角色编号 parentCode-上级编号
 * 
 * @author 37803
 *
 */
public class UserModel extends BaseModel {
	private String userName;// 用户名
	private String userPass;// 密码
	private String roleCode;// 角色编号
	private String parentCode;// 上级编号

	private List<MenuModel> menus;
	private String roleName;
	private String parentName;
	private String selCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<MenuModel> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuModel> menus) {
		this.menus = menus;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSelCode() {
		return selCode;
	}

	public void setSelCode(String selCode) {
		this.selCode = selCode;
	}

	public UserModel(String userName, String userPass, String roleCode, String parentCode) {
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.roleCode = roleCode;
		this.parentCode = parentCode;
	}

	public UserModel() {
		super();
	}

}
