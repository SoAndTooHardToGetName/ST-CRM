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

import com.situ.situOA.sys.mapper.IAccessMapper;
import com.situ.situOA.sys.model.AccessModel;
import com.situ.situOA.sys.model.MenuModel;
import com.situ.situOA.sys.model.RoleModel;
import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.sys.service.IMenuService;
import com.situ.situOA.sys.service.IRoleService;

@Controller
@RequestMapping("access")
public class AccessController {
	@Autowired
	private IAccessMapper<AccessModel> service;
	@Autowired
	private IRoleService<RoleModel> serviceRole;
	@Autowired
	private IMenuService<MenuModel> serviceMenu;

	/**
	 * 添加
	 * 
	 * @param accessModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String add(AccessModel accessModel, HttpServletRequest request) {
		if (service.selectAll(accessModel).size() > 0)
			return "2";
		accessModel.setCreateTime(setTime());
		accessModel.setCreateBy(setOpreter(request));
		if (service.insert(accessModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param accessModel
	 * @return
	 */
	private String setTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/**
	 * 查询
	 * 
	 * @param accessModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selectModel(AccessModel accessModel) {
		List<AccessModel> list = service.selectModel(accessModel);
		int count = service.count(accessModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("count", count);
		map.put("code", 0);
		return new JSONObject(map).toString();
	}

	/**
	 * 更新
	 * 
	 * @param accessModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("upd")
	public String upd(AccessModel accessModel, HttpServletRequest request) {
		if (accessModel.getState() == 1)
			accessModel.setState(0);
		else
			accessModel.setState(1);
		accessModel.setUpdateBy(setOpreter(request));
		accessModel.setUpdateTime(setTime());
		if (service.updateActive(accessModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 获取操作者Id
	 * 
	 * @param accessModel
	 * @param request
	 * @return
	 */
	private Integer setOpreter(HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		return user.getId();
	}

	/**
	 * 单删除
	 * 
	 * @param accessModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(AccessModel accessModel, HttpServletRequest request) {
		accessModel.setUpdateBy(setOpreter(request));
		accessModel.setUpdateTime(setTime());
		if (service.deleteModel(accessModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 多删除
	 * 
	 * @param vids
	 * @param request
	 * @return
	 */
	public String delete(String vids, HttpServletRequest request) {
		String[] ids = vids.split(",");
		service.deletefake(ids, setTime(), setOpreter(request));
		return "1";
	}

	/**
	 * 跳转
	 * 
	 * @param accessModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(AccessModel accessModel, HttpServletRequest request) {
		request.setAttribute("menus", serviceMenu.selectAll(new MenuModel()));
		request.setAttribute("roles", serviceRole.selectAll(new RoleModel()));
		return "/WEB-INF/sys/access/relupd.jsp";
	}

	/**
	 * 页面2跳转
	 * 
	 * @param roleCode
	 * @param request
	 * @return
	 */
	@RequestMapping("set")
	public String set(String roleCode, HttpServletRequest request) {
		request.setAttribute("roleCode", roleCode);
		return "/WEB-INF/sys/access/list2.jsp";
	}

	/**
	 * 获取当前职位的全部权限
	 * 
	 * @param accessModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAll")
	public String selectAll(AccessModel accessModel) {
		return new JSONArray(service.selectAll(accessModel)).toString();
	}
}
