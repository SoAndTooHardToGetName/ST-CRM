package com.situ.situOA.work.model;

import com.situ.situOA.sys.model.UserModel;

import tools.base.model.BaseModel;

public class OrderInfoModel extends BaseModel {

	private String userCode;
	private String custCode;
	private String prodCode;
	private String count;
	private Integer status = 0;

	private UserModel userModel;
	private CustomerInfoModel customerInfoModel;
	private ProductInfoModel productInfoModel;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public CustomerInfoModel getCustomerInfoModel() {
		return customerInfoModel;
	}

	public void setCustomerInfoModel(CustomerInfoModel customerInfoModel) {
		this.customerInfoModel = customerInfoModel;
	}

	public ProductInfoModel getProductInfoModel() {
		return productInfoModel;
	}

	public void setProductInfoModel(ProductInfoModel productInfoModel) {
		this.productInfoModel = productInfoModel;
	}

}
