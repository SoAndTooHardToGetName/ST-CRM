package com.situ.situOA.work.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.work.model.CustomerInfoModel;
import com.situ.situOA.work.model.OrderInfoModel;
import com.situ.situOA.work.model.ProductInfoModel;
import com.situ.situOA.work.service.ICustomerInfoService;
import com.situ.situOA.work.service.IOrderInfoService;
import com.situ.situOA.work.service.IProductInfoService;

@Controller
@RequestMapping("order")
public class OrderInfoController {
	@Autowired
	private IOrderInfoService<OrderInfoModel> service;
	@Autowired
	private IProductInfoService<ProductInfoModel> serviceProd;
	@Autowired
	private ICustomerInfoService<CustomerInfoModel> serviceCust;

	/**
	 * 添加页面
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	@RequestMapping("about")
	public String about(OrderInfoModel orderInfoModel, HttpServletRequest request) {
		request.setAttribute("custs", serviceCust.selectAll(new CustomerInfoModel()));
		request.setAttribute("prods", serviceProd.selectAll(new ProductInfoModel()));
		return "/WEB-INF/work/order/add.jsp";
	}

	/**
	 * 添加
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submit")
	public String submit(OrderInfoModel orderInfoModel, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		orderInfoModel.setUserCode(user.getCode());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		orderInfoModel.setCreateTime(dateFormat.format(new Date()));
		return add(orderInfoModel);
	}

	/**
	 * 添加
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	private String add(OrderInfoModel orderInfoModel) {
		if (service.insert(orderInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 更新
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String update(OrderInfoModel orderInfoModel) {
		switch (orderInfoModel.getStatus()) {
		case 0:
			orderInfoModel.setStatus(1);
			break;
		case 1:
			orderInfoModel.setStatus(2);
			ProductInfoModel prod = new ProductInfoModel();
			prod.setCode(orderInfoModel.getProdCode());
			prod = serviceProd.select(prod);
			prod.setSum((Integer.parseInt(prod.getSum()) + Integer.parseInt(orderInfoModel.getCount())) + "");
			serviceProd.update(prod);
			break;
		case 2:
			orderInfoModel.setStatus(3);
			break;
		case 3:
			orderInfoModel.setStatus(4);
			break;
		}
		if (service.updateActive(orderInfoModel) > 0)
			return "1";
		return null;
	}

	/**
	 * 查询
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectModel")
	public String selectModel(OrderInfoModel orderInfoModel, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if ("empSell".equals(user.getRoleCode()))
			orderInfoModel.setUserCode(user.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("count", service.count(orderInfoModel));
		map.put("data", service.selectModel(orderInfoModel));
		return new JSONObject(map).toString();
	}

	/**
	 * 单删除
	 * 
	 * @param orderInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteModel")
	public String deleteModel(OrderInfoModel orderInfoModel) {
		if (service.deleteModel(orderInfoModel) > 0)
			return "1";
		return null;
	}
}
