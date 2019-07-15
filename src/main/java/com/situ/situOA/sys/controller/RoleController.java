package com.situ.situOA.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.situOA.sys.model.RoleModel;
import com.situ.situOA.sys.service.IRoleService;

/**
 * 职位相关操作
 * 
 * @author 37803
 *
 */
@Controller
@RequestMapping("role")
public class RoleController {
	@Autowired
	private IRoleService<RoleModel> service;

	/**
	 * 按条件查询职位
	 * 
	 * -职位名，职位编号
	 * 
	 * @param roleModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selectModel(RoleModel roleModel) {
		System.out.println(roleModel.getCode() + "+__+" + roleModel.getName());
		List<RoleModel> list = service.selectModel(roleModel);
		int count = service.count(roleModel);
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("data", list);
		map.put("count", count);
		return new JSONObject(map).toString();
	}

	/**
	 * 修改添加页面判断
	 * 
	 * @param roleModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(RoleModel roleModel, HttpServletRequest request) {
		request.setAttribute("roles", service.selectAll(new RoleModel()));
		if (roleModel.getId() != null) {
			request.setAttribute("title", "职位信息--修改");
			request.setAttribute("temp", service.select(roleModel));
		} else
			request.setAttribute("title", "职位信息--添加");
		return "/WEB-INF/sys/role/roleupd.jsp";
	}

	/**
	 * 添加修改操作判断
	 * 
	 * @param roleModel
	 * @return 1-操作成功
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String submit(RoleModel roleModel) {
		if (roleModel.getId() == null)
			return add(roleModel);
		return upd(roleModel);
	}

	/**
	 * 添加
	 * 
	 * @param roleModel
	 * @return 1-添加成功;null-失败
	 */
	private String add(RoleModel roleModel) {
		if (service.insert(roleModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param roleModel
	 * @return 1-添加成功;null-失败
	 */
	private String upd(RoleModel roleModel) {
		if (service.update(roleModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 单删除
	 * 
	 * @param roleModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(RoleModel roleModel) {
		if (service.deleteModel(roleModel) > 0)
			return "1";
		return null;
	}

	@ResponseBody
	@RequestMapping("delete")
	public String delete(String vids) {
		String[] ids = vids.split(",");
		service.delete(ids);
		return "1";
	}
}
