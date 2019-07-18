package com.situ.situOA.work.model;

import tools.base.model.BaseModel;

public class CustomerInfoModel extends BaseModel {

	private String status;
	private String email;
	private String phone;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
