package com.situ.situOA.sys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.situ.situOA.sys.model.MenuModel;
import com.situ.situOA.sys.model.RoleModel;
import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.sys.service.IRoleService;
import com.situ.situOA.sys.service.IUserService;

import tools.Datacheck;
import tools.FormatPOI;
import tools.MD5;

/**
 * 用户相关操作
 * 
 * @author 37803
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private IUserService<UserModel> service;
	@Autowired
	private IRoleService<RoleModel> serviceRole;

	/**
	 * 登录
	 * 
	 * @param userModel
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	public String login(UserModel userModel, HttpServletRequest request) {
		switch (loginCheck(userModel)) {
		case 1:
			request.setAttribute("loginMessage", "请输入用户名");
			break;
		case 2:
			request.setAttribute("loginMessage", "请输入密码");
			break;
		default:
			UserModel temp = service.login(userModel);
			switch (DataBaseCheck(userModel, temp)) {
			case 3:
				request.setAttribute("loginMessage", "用户名不存在");
				break;
			case 4:
				request.setAttribute("loginMessage", "密码不正确");
				break;
			case 5:
				request.setAttribute("loginMessage", "该用户因违反相关条例已禁用");
				break;
			default:
				request.getSession().setAttribute("user", setMenu(temp));
				return "/WEB-INF/sys/index.jsp";
			}
		}
		return "/WEB-INF/login/login.jsp";
	}

	/**
	 * 设置二级菜单
	 * 
	 * @param userModel
	 * @return
	 */
	private UserModel setMenu(UserModel userModel) {
		List<MenuModel> temp = userModel.getMenus();
		List<MenuModel> menus = new ArrayList<MenuModel>();
		for (MenuModel s : temp) {
			if (s.getParentCode() == null || s.getParentCode().isEmpty()) {
				menus.add(s);
				continue;
			}
			for (int i = 0; i < menus.size(); i++) {
				if (s.getParentCode().equals(menus.get(i).getCode()))
					menus.get(i).getList().add(s);
			}
		}
		userModel.setMenus(menus);
		return userModel;
	}

	/**
	 * 登录数据检查
	 * 
	 * @param userModel
	 * @return
	 */
	private int loginCheck(UserModel userModel) {
		if (Datacheck.Str(userModel.getUserName()))
			return 1;
		if (Datacheck.Str(userModel.getUserPass()))
			return 2;
		return 0;
	}

	/**
	 * 登录帐号与数据库匹配
	 * 
	 * @param userModel
	 * @param temp
	 * @return
	 */
	private int DataBaseCheck(UserModel userModel, UserModel temp) {
		if (temp == null)
			return 3;
		if (!temp.getUserPass().equals(MD5.encode(userModel.getUserPass())))
			return 4;
		if (temp.getState() == 0)
			return 5;
		return 0;
	}

	/**
	 * 按条件查询员工
	 * 
	 * -姓名，编号，用户名，上级，角色
	 * 
	 * @param userModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selctModel(UserModel userModel, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		userModel.setSelCode(user.getCode());
		List<UserModel> list = service.selectModel(userModel);
		int count = service.count(userModel);
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("count", count);
		map.put("data", list);
		return new JSONObject(map).toString();
	}

	/**
	 * 添加修改页面跳转判断
	 * 
	 * @param userModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(UserModel userModel, HttpServletRequest request) {
		List<RoleModel> roles = serviceRole.selectAll(getRole((UserModel) request.getSession().getAttribute("user")));
		request.setAttribute("roles", roles);
		if (userModel.getId() != null) {
			request.setAttribute("temp", service.select(userModel));
			request.setAttribute("title", "员工信息维护");
		} else
			request.setAttribute("title", "员工添加");
		return "/WEB-INF/sys/user/userupd.jsp";
	}

	/**
	 * 获取当前登录帐号职位
	 * 
	 * @param userModel
	 * @return
	 */
	private RoleModel getRole(UserModel userModel) {
		RoleModel role = new RoleModel();
		role.setSelCode(userModel.getRoleCode());
		return role;
	}

	/**
	 * 添加修改判断
	 * 
	 * @param userModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String addAndUpd(UserModel userModel, HttpServletRequest request) {
		if (userModel.getId() == null)
			return add(userModel, request);
		return upd(userModel, request);
	}

	/**
	 * 添加
	 * 
	 * @param userModel
	 * @return 1-成功；null-失败
	 */
	private String add(UserModel userModel, HttpServletRequest request) {
		userModel.setCreateBy(getOperater(request));
		userModel.setUserPass(MD5.encode(userModel.getUserPass()));
		userModel.setCreateTime(nowTime());
		if (service.insert(userModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param userModel
	 * @return 1-成功;null-失败
	 */
	private String upd(UserModel userModel, HttpServletRequest request) {
		userModel.setUpdateBy(getOperater(request));
		if (!Datacheck.Str(userModel.getUserPass()))
			userModel.setUserPass(MD5.encode(userModel.getUserPass()));
		userModel.setUpdateTime(nowTime());
		if (service.update(userModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 获取添加或修改记录的人
	 * 
	 * @param request
	 * @return
	 */
	private Integer getOperater(HttpServletRequest request) {
		UserModel temp = (UserModel) request.getSession().getAttribute("user");
		return temp.getId();
	}

	/**
	 * 获取添加或修改时间
	 * 
	 * @return
	 */
	private String nowTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/**
	 * 单删除
	 * 
	 * @param userModel
	 * @param request
	 * @return 1-成功;null-失败
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(UserModel userModel, HttpServletRequest request) {
		userModel.setCreateTime(nowTime());
		userModel.setUpdateBy(getOperater(request));
		if (service.deleteModel(userModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 多删除
	 * 
	 * @param ids
	 * @param userModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String ids, HttpServletRequest request) {
		String[] id = ids.split(",");
		service.deletefake(id, nowTime(), getOperater(request));
		return "1";
	}

	/**
	 * @param userModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAll", produces = "application/json;charset=utf-8")
	public String selectAll(UserModel userModel) {
		return new JSONArray(service.selectAll(userModel)).toString();
	}

	/**
	 * 用户信息导出
	 * 
	 * @param response
	 * @param userModel
	 * @throws Exception
	 */
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response, UserModel userModel, Integer stop, HttpServletRequest request)
			throws Exception {
		List<UserModel> datalist = new ArrayList<UserModel>();
		if (stop == null) {
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			userModel.setSelCode(user.getCode());
			datalist = service.selectAll(userModel);
		}
		List<String> propList = Arrays.asList("Code", "Name", "UserName", "RoleName", "ParentName", "UserPass",
				"RoleCode", "ParentCode");
		List<String> fieldName = Arrays.asList("编号*", "姓名*", "用户名*", "职位名称", "上级", "登录密码*", "职位编号*", "上级编号*");

		Workbook wb = FormatPOI.exportExcel(datalist, propList, fieldName);

		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attchment;filename=userList.xlsx");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		wb.close();
		out.close();
	}

	/**
	 * 批量导入
	 * 
	 * @param multipartResolver
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "upload", produces = "application/json;charset=utf-8")
	public String uploadExcel(CommonsMultipartResolver multipartResolver, HttpServletRequest request)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		List<UserModel> list = new ArrayList<UserModel>();
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				list = parse(file.getInputStream(), request);
				map.put("data", list);
			}
		}
		return new JSONObject(map).toString();
	}

	/**
	 * 获取上传文件内容
	 * 
	 * @param fis
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private List<UserModel> parse(InputStream fis, HttpServletRequest request) throws IOException {
		List<UserModel> list = new ArrayList<UserModel>();
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("list");
		for (Row row : sheet) {
			if (0 == row.getRowNum())
				continue;
			UserModel userModel = new UserModel();
			userModel.setCode(getValue(row.getCell(0)));
			userModel.setName(getValue(row.getCell(1)));
			userModel.setUserName(getValue(row.getCell(2)));
			userModel.setUserPass(getValue(row.getCell(5)));
			userModel.setRoleCode(getValue(row.getCell(6)));
			userModel.setParentCode(getValue(row.getCell(7)));
			list.add(userModel);
			add(userModel, request);
		}
		workbook.close();
		fis.close();
		return list;
	}

	/**
	 * 单元格内容
	 * 
	 * @param cell
	 * @return
	 */
	private String getValue(Cell cell) {
		CellType type = cell.getCellTypeEnum();
		if (CellType.STRING.equals(type))
			return cell.getStringCellValue();
		else if (CellType.NUMERIC.equals(type))
			return String.valueOf(cell.getNumericCellValue());
		return null;
	}
}
