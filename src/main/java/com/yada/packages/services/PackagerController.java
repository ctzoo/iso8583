package com.yada.packages.services;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackagerController {
	String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
	@Resource(name = "packagerMap")
	public Map<String, GenericPackager> map;

	@RequestMapping(path = "/pack/{packerName}/{version}", method = RequestMethod.POST)
	public String pack(@PathVariable String packerName, @PathVariable String version,
			@RequestBody(required = false) Map<String, String> fieldMap) throws ISOException {
		var packer = map.get(packerName + "/" + version);
		var msg = new ISOMsg();
		msg.setPackager(packer);
		msg.setMTI("0200");

		for (var key : fieldMap.keySet()) {
			if (key == "MTI") {
				msg.setMTI(fieldMap.get(key));
			} else {
				var value = fieldMap.get(key);
				var ik = Integer.parseInt(key);
				if (Pattern.matches(base64Pattern, value)) {
					msg.set(ik, Base64.getDecoder().decode(value));
				} else {
					msg.set(ik, value);
				}
			}
		}
		return Base64.getEncoder().encodeToString(msg.pack());
	}

	@RequestMapping(path = "/unpack/{packerName}/{version}", method = RequestMethod.POST)
	public Map<String, String> unpack(@PathVariable String packerName, @PathVariable String version,
			@RequestBody String base64Package) throws ISOException {
		var packer = map.get(packerName + "/" + version);
		var pack = Base64.getDecoder().decode(base64Package);
		var msg = new ISOMsg();
		msg.setPackager(packer);
		msg.unpack(pack);
		var retMap = new HashMap<String, String>();

		retMap.put("MTI", msg.getMTI());

		for (var i = 2; i <= msg.getMaxField(); i++) {
			if (msg.hasField(i)) {
				var v = msg.getValue(i);
				if(v instanceof String)
					retMap.put(Integer.toString(i), msg.getString(i));
				else
					retMap.put(Integer.toString(i), Base64.getEncoder().encodeToString((byte [])v));
			}
		}
		return retMap;
	}
}
