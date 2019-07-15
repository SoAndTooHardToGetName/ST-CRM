package com.situ.situOA.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.situOA.work.other.FanYi;

import tools.Datacheck;

@Controller
@RequestMapping("fanyi")
public class FanYiController {

	@ResponseBody
	@RequestMapping("test")
	public String item(String item) {
		System.out.println(FanYi.baseUrl + item);
		if (Datacheck.Str(item))
			return "";
		return new FanYi().getContent(FanYi.baseUrl + item);
	}
}
