package com.situ.situOA.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.situOA.sys.model.MenuModel;
import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.sys.service.IMenuService;

/**
 * 
 * 菜单相关操作
 * 
 * @author 37803
 *
 */
@Controller
@RequestMapping("menu")
public class MenuController {

	@Autowired
	private IMenuService<MenuModel> service;

	/**
	 * 添加修改操作判断
	 * 
	 * @param menuModel
	 * @return 1-成功;null-失败
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String submit(MenuModel menuModel) {
		if (menuModel.getId() == null)
			return add(menuModel);
		return upd(menuModel);
	}

	/**
	 * 添加
	 * 
	 * @param menuModel
	 * @return
	 */
	private String add(MenuModel menuModel) {
		if (menuModel.getParentCode() != null)
			menuModel.setOrder(1.00d);
		if (service.insert(menuModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param menuModel
	 * @return
	 */
	private String upd(MenuModel menuModel) {
		if (service.update(menuModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 单删除
	 * 
	 * @param menuModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(MenuModel menuModel, HttpServletRequest request) {
		menuModel.setUpdateTime(nowTime());
		menuModel.setUpdateBy(getId(request));
		if (service.deleteModel(menuModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	private String nowTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/**
	 * 获取操作者的id
	 * 
	 * @param request
	 * @return
	 */
	private Integer getId(HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		return user.getId();
	}

	/**
	 * 多删除
	 * 
	 * @param vids
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String vids, HttpServletRequest request) {
		String[] ids = vids.split(",");
		service.deletefake(ids, nowTime(), getId(request));
		return "1";
	}

	/**
	 * 按条件查询
	 * 
	 * @param menuModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selectModel(MenuModel menuModel) {
		System.out.println(menuModel.getOrder());
		List<MenuModel> list = service.selectModel(menuModel);
		int count = service.count(menuModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("count", count);
		map.put("code", 0);
		return new JSONObject(map).toString();
	}

	/**
	 * 添加修改页面跳转判断
	 * 
	 * @param menuModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(MenuModel menuModel, HttpServletRequest request) {
		request.setAttribute("menus",
				service.selectModel(new MenuModel(null, null, 0.00, null, null, null, null, null, null)));
		if (menuModel.getId() != null) {
			request.setAttribute("title", "菜单信息--修改");
			request.setAttribute("temp", service.select(menuModel));
		} else
			request.setAttribute("title", "菜单信息--添加");
		return "/WEB-INF/sys/menu/menuupd.jsp";
	}

	/**
	 * 获取全部菜单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAll")
	public String selectAll() {
		return new JSONArray(service.selectAll(new MenuModel())).toString();
	}
}
