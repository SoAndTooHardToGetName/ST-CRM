package com.situ.situOA.sys.model;

import tools.base.model.BaseModel;

/**
 * 角色
 * 
 * code-编号 name-角色名
 * 
 * @author 37803
 *
 */
public class RoleModel extends BaseModel {

	private String parentCode;
	private String selCode;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getSelCode() {
		return selCode;
	}

	public void setSelCode(String selCode) {
		this.selCode = selCode;
	}

	public RoleModel() {
		super();
	}

	public RoleModel(String name, String code) {
		super(name, code);
	}

}
