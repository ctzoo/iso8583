package com.yada.packages.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jpos.iso.packager.GenericPackager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackagerController {
	@Resource(name="packagerMap")
	public Map<String, GenericPackager> map;
	
	@RequestMapping("/pack")
	public String pack() {
		return "";
	}
	
	@RequestMapping("/unpack")
	public Map<String, Object> unpack() {
		var retMap = new HashMap<String, Object>();
		retMap.put("testNumber", 10);
		retMap.put("testString", "is string");
		return retMap;
	}
}
