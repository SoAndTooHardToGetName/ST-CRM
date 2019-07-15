package com.situ.situOA.sys.model;

import java.util.List;

import tools.base.model.BaseModel;

/**
 * 菜单
 * 
 * menuUrl-地址 parengCode-上级菜单编号 imgUrl-图片
 * 
 * @author 37803
 *
 */
public class MenuModel extends BaseModel {
	private String menuUrl;// 地址
	private String parentCode;// 上级菜单
	private String imgUrl;// 图片

	private List<MenuModel> list;

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<MenuModel> getList() {
		return list;
	}

	public void setList(List<MenuModel> list) {
		this.list = list;
	}

	public MenuModel() {
		super();
	}

	public MenuModel(String createTime, Integer createBy, Double order, String name, String code, Integer type,
			String menuUrl, String parentCode, String imgUrl) {
		super(createTime, createBy, order, name, code, type);
		this.menuUrl = menuUrl;
		this.parentCode = parentCode;
		this.imgUrl = imgUrl;

	}

}
