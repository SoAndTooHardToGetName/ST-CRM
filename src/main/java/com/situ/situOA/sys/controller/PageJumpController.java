package com.situ.situOA.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.situOA.sys.model.MenuModel;
import com.situ.situOA.sys.model.RoleModel;
import com.situ.situOA.sys.service.IMenuService;
import com.situ.situOA.sys.service.IRoleService;
import com.situ.situOA.work.model.CustomerInfoModel;
import com.situ.situOA.work.service.ICustomerInfoService;

@Controller
@RequestMapping("page")
public class PageJumpController {
	@Autowired
	private IRoleService<RoleModel> role;
	@Autowired
	private IMenuService<MenuModel> menu;
	@Autowired
	private ICustomerInfoService<CustomerInfoModel> cust;

	@RequestMapping("login")
	public String login() {
		return "/WEB-INF/login/login.jsp";
	}

	@RequestMapping("user")
	public String user() {
		return "/WEB-INF/sys/user/list.jsp";
	}

	@RequestMapping("access")
	public String access(HttpServletRequest request) {
		request.setAttribute("roles", role.selectAll(new RoleModel()));
		request.setAttribute("menus", menu.selectAll(new MenuModel()));
		return "/WEB-INF/sys/access/list.jsp";
	}

	@RequestMapping("role")
	public String role() {
		return "/WEB-INF/sys/role/list.jsp";
	}

	@RequestMapping("menu")
	public String menu() {
		return "/WEB-INF/sys/menu/list.jsp";
	}

	@RequestMapping("index")
	public String index() {
		return "/WEB-INF/sys/index.jsp";
	}

	@RequestMapping("cust")
	public String cust() {
		return "/WEB-INF/work/cust/list.jsp";
	}

	@RequestMapping("mailRecives")
	public String mailRecives() {
		return "/WEB-INF/work/mail/recives.jsp";
	}

	@RequestMapping("mailList")
	public String mailList() {
		return "/WEB-INF/work/mail/list.jsp";
	}

	@RequestMapping("mailSend")
	public String mailSend(HttpServletRequest request) {
		request.setAttribute("custs", cust.selectAll(new CustomerInfoModel()));
		return "/WEB-INF/work/mail/send.jsp";
	}

	@RequestMapping("prod")
	public String prod() {
		return "/WEB-INF/work/prod/list.jsp";
	}

	@RequestMapping("order")
	public String order() {
		return "/WEB-INF/work/order/list.jsp";
	}

	@RequestMapping("report")
	public String report() {
		return "/WEB-INF/work/report/report.jsp";
	}
	
	@RequestMapping("fanyi")
	public String fanyi() {
		return "/WEB-INF/work/other/FanYi.jsp";
	}
}
