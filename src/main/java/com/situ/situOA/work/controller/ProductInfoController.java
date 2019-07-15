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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.situ.situOA.work.model.ProductInfoModel;
import com.situ.situOA.work.service.IProductInfoService;

import tools.FormatPOI;

/**
 * 商品相关操作
 * 
 * @author 37803
 *
 */
@Controller
@RequestMapping("prod")
public class ProductInfoController {
	@Autowired
	private IProductInfoService<ProductInfoModel> service;

	/**
	 * 添加修改页面判断
	 * 
	 * @param productInfoModel
	 * @param request
	 * @return
	 */
	@RequestMapping("about")
	public String about(ProductInfoModel productInfoModel, HttpServletRequest request) {
		if (productInfoModel.getId() != null) {
			request.setAttribute("title", "商品信息--修改");
			request.setAttribute("temp", service.select(productInfoModel));
		} else
			request.setAttribute("title", "商品信息--添加");
		return "/WEB-INF/work/prod/add.jsp";
	}

	/**
	 * 添加修改判断
	 * 
	 * @param productInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String submit(ProductInfoModel productInfoModel) {
		if (productInfoModel.getId() == null)
			return add(productInfoModel);
		return update(productInfoModel);
	}

	/**
	 * 添加
	 * 
	 * @param productInfoModel
	 * @return
	 */
	private String add(ProductInfoModel productInfoModel) {
		if (service.insert(productInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 更新
	 * 
	 * @param productInfoModel
	 * @return
	 */
	private String update(ProductInfoModel productInfoModel) {
		if (service.update(productInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 单删除
	 * 
	 * @param productInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(ProductInfoModel productInfoModel) {
		if (service.deleteModel(productInfoModel) > 0)
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
	 * 按条件查询商品
	 * 
	 * @param productInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectModel", produces = "application/json;charset=utf-8")
	public String selectModel(ProductInfoModel productInfoModel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("data", service.selectModel(productInfoModel));
		map.put("count", service.count(productInfoModel));
		return new JSONObject(map).toString();
	}

	/**
	 * 导出商品信息
	 * 
	 * @param response
	 * @param productInfoModel
	 * @param stop
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response, ProductInfoModel productInfoModel, Integer stop,
			HttpServletRequest request) throws Exception {
		List<ProductInfoModel> datalist = new ArrayList<>();
		if (stop == null) {
			datalist = service.selectAll(productInfoModel);
		}
		List<String> propList = Arrays.asList("Code", "Name", "Sum", "Cost");
		List<String> fieldName = Arrays.asList("编号*", "商品名", "销售量", "单价");

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
		List<ProductInfoModel> list = new ArrayList<>();
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
	private List<ProductInfoModel> parse(InputStream fis, HttpServletRequest request) throws IOException {
		List<ProductInfoModel> list = new ArrayList<>();
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("list");
		for (Row row : sheet) {
			if (0 == row.getRowNum())
				continue;
			ProductInfoModel productInfoModel = new ProductInfoModel();
			productInfoModel.setCode(getValue(row.getCell(0)));
			productInfoModel.setName(getValue(row.getCell(1)));
			productInfoModel.setSum(getValue(row.getCell(2)));
			productInfoModel.setCost(getValue(row.getCell(3)));
			list.add(productInfoModel);
			add(productInfoModel);
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

	/**
	 * 获取全部商品信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAll")
	public String selectAll() {
		return new JSONArray(service.selectAll(new ProductInfoModel())).toString();
	}
}
