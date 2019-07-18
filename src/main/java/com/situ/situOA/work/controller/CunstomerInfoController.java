package com.situ.situOA.work.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.situ.situOA.work.model.CustomerInfoModel;
import com.situ.situOA.work.service.ICustomerInfoService;

import tools.FormatPOI;

/**
 * 顾客信息相关操作
 * 
 * @author 37803
 *
 */
@Controller
@RequestMapping("cust")
public class CunstomerInfoController {
	@Autowired
	private ICustomerInfoService<CustomerInfoModel> service;

	/**
	 * 添加修改页面判断
	 * 
	 * @param customerInfoModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(CustomerInfoModel customerInfoModel, HttpServletRequest request) {
		if (customerInfoModel.getId() != null) {
			request.setAttribute("temp", service.select(customerInfoModel));
			request.setAttribute("titile", "顾客信息--修改");
		} else
			request.setAttribute("title", "顾客信息--添加");
		return "/WEB-INF/work/cust/add.jsp";
	}

	/**
	 * 添加修改操作判断
	 * 
	 * @param customerInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String submit(CustomerInfoModel customerInfoModel) {
		if (customerInfoModel.getId() != null)
			return update(customerInfoModel);
		return add(customerInfoModel);
	}

	/**
	 * 添加
	 * 
	 * @param customerInfoModel
	 * @return
	 */
	private String add(CustomerInfoModel customerInfoModel) {
		if (service.insert(customerInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 单删除
	 * 
	 * @param customerInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(CustomerInfoModel customerInfoModel) {
		if (service.deleteModel(customerInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 多删除
	 * 
	 * @param vids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String vids) {
		String[] ids = vids.split(",");
		service.delete(ids);
		return "1";
	}

	/**
	 * 更新用户信息
	 * 
	 * @param customerInfoModel
	 * @return
	 */
	private String update(CustomerInfoModel customerInfoModel) {
		if (service.update(customerInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 按条件查询
	 * 
	 * @param customerInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selectModel(CustomerInfoModel customerInfoModel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("data", service.selectModel(customerInfoModel));
		map.put("count", service.count(customerInfoModel));
		return new JSONObject(map).toString();
	}

	/**
	 * 导出客户信息
	 * 
	 * @param response
	 * @param customerInfoModel
	 * @param stop
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response, CustomerInfoModel customerInfoModel, Integer stop,
			HttpServletRequest request) throws Exception {
		List<CustomerInfoModel> datalist = new ArrayList<>();
		if (stop == null) {
			datalist = service.selectAll(customerInfoModel);
		}
		List<String> propList = Arrays.asList("Code", "Name", "Status", "Email");
		List<String> fieldName = Arrays.asList("编号*", "姓名*", "身份", "email");

		Workbook wb = FormatPOI.exportExcel(datalist, propList, fieldName);

		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attchment;filename=custList.xlsx");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		wb.close();
		out.close();
	}

	/**
	 * 导入客户信息
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
		List<CustomerInfoModel> list = new ArrayList<>();
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
	private List<CustomerInfoModel> parse(InputStream fis, HttpServletRequest request) throws IOException {
		List<CustomerInfoModel> list = new ArrayList<>();
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("list");
		for (Row row : sheet) {
			if (0 == row.getRowNum())
				continue;
			CustomerInfoModel customerInfoModel = new CustomerInfoModel();
			customerInfoModel.setCode(getValue(row.getCell(0)));
			customerInfoModel.setName(getValue(row.getCell(1)));
			customerInfoModel.setStatus(getValue(row.getCell(2)));
			customerInfoModel.setEmail(getValue(row.getCell(3)));
			list.add(customerInfoModel);
			add(customerInfoModel);
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
