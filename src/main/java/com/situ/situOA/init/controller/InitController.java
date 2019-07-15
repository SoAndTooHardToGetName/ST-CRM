package com.situ.situOA.init.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.situ.situOA.sys.model.AccessModel;
import com.situ.situOA.sys.model.MenuModel;
import com.situ.situOA.sys.model.RoleModel;
import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.sys.service.IAccessService;
import com.situ.situOA.sys.service.IMenuService;
import com.situ.situOA.sys.service.IRoleService;
import com.situ.situOA.sys.service.IUserService;

import tools.MD5;

/**
 * 添加初始数据
 * 
 * 管理员帐号，基本功能等
 * 
 * @author 37803
 */
@Component
public class InitController implements ApplicationRunner {
	@Autowired
	private IUserService<UserModel> user;
	@Autowired
	private IMenuService<MenuModel> menu;
	@Autowired
	private IAccessService<AccessModel> access;
	@Autowired
	private IRoleService<RoleModel> role;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User();
		System.out.println("do user");
		Menu();
		System.out.println("do Menu");
		Access();
		System.out.println("do Access");
		Role();
		System.out.println("do Role");
	}
	/**
	 * 设置管理员帐号
	 * 
	 * @return
	 */
	private UserModel getUser() {
		UserModel userModel = new UserModel("admin", "123456", "admin", null);
		userModel.setName("管理员");
		userModel.setCode("admin");
		userModel.setUserPass(MD5.encode(userModel.getUserPass()));
		userModel.setCreateBy(0);
		userModel.setCreateTime(getTime());
		return userModel;
	}

	/**
	 * 判断数据库用户数据
	 * 
	 * @return false-有数据； true-无数据
	 */
	private boolean selectUser() {
		if (user.selectAll(new UserModel()).size() > 0)
			return false;
		return true;
	}

	/**
	 * 初始化管理员帐号
	 */
	private void User() {
		if (selectUser())
			user.insert(getUser());
	}

	private String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/**
	 * 设置初始菜单功能
	 * 
	 * @return 基础菜单集合
	 */
	private List<MenuModel> getMenus() {
		List<MenuModel> list = new ArrayList<MenuModel>();
		MenuModel menu1 = new MenuModel(getTime(), 0, 0.00d, "员工管理", "user", 0, "/situOA/page/user", null, null);
		MenuModel menu2 = new MenuModel(getTime(), 0, 0.00d, "权限管理", "access", 0, "/situOA/page/access", null, null);
		MenuModel menu3 = new MenuModel(getTime(), 0, 0.00d, "职位管理", "role", 0, "/situOA/page/role", null, null);
		MenuModel menu4 = new MenuModel(getTime(), 0, 0.00d, "菜单管理", "menu", 0, "/situOA/page/menu", null, null);
		list.add(menu1);
		list.add(menu2);
		list.add(menu3);
		list.add(menu4);
		return list;
	}

	/**
	 * 判断数据库菜单数据
	 * 
	 * @return false-有数据；true-无数据
	 */
	private boolean selectMenu() {
		if (menu.selectAll(new MenuModel()).size() > 0)
			return false;
		return true;
	}

	/**
	 * 初始化基础菜单
	 */
	private void Menu() {
		if (selectMenu())
			for (MenuModel s : getMenus())
				menu.insert(s);
	}

	/**
	 * 设置管理员帐号权限
	 * 
	 * @return
	 */
	private List<AccessModel> getAccess() {
		List<AccessModel> list = new ArrayList<AccessModel>();
		AccessModel access1 = new AccessModel(getTime(), 0, "admin", "user");
		AccessModel access2 = new AccessModel(getTime(), 0, "admin", "access");
		AccessModel access3 = new AccessModel(getTime(), 0, "admin", "role");
		AccessModel access4 = new AccessModel(getTime(), 0, "admin", "menu");
		list.add(access1);
		list.add(access2);
		list.add(access3);
		list.add(access4);
		return list;
	}

	/**
	 * 判断数据库权限数据
	 * 
	 * @return false-有数据；true-无数据
	 */
	private boolean selectAccess() {
		if (access.selectAll(new AccessModel()).size() > 0)
			return false;
		return true;
	}

	/**
	 * 初始化权限
	 */
	private void Access() {
		if (selectAccess())
			for (AccessModel s : getAccess())
				access.insert(s);
	}

	/**
	 * 设置管理员职位
	 * s
	 * @return
	 */
	private RoleModel getRole() {
		return new RoleModel("总经理", "admin");
	}

	/**
	 * 判断数据库职位数据
	 * 
	 * @return false-有数据；true-无数据
	 */
	private boolean selectRole() {
		if (role.selectAll(new RoleModel()).size() > 0)
			return false;
		return true;
	}

	/**
	 * 初始化职位
	 */
	private void Role() {
		if (selectRole())
			role.insert(getRole());
	}
}
