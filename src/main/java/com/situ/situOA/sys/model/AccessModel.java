package com.situ.situOA.sys.model;

import tools.base.model.BaseModel;

/**
 * 权限
 * 
 * roleCode-角色编号 menuCode-菜单编号
 * 
 * @author 37803
 *
 */
public class AccessModel extends BaseModel {
	private String roleCode;// 角色
	private String menuCode;// 菜单

	private String roleName;
	private String menuName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public AccessModel() {
		super();
	}

	public AccessModel(String createTime, Integer createBy, String roleCode, String menuCode) {
		super(createTime, createBy);
		this.roleCode = roleCode;
		this.menuCode = menuCode;
	}

}
