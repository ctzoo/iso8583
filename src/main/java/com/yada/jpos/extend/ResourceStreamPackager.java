package com.yada.jpos.extend;

import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

public class ResourceStreamPackager extends GenericPackager {

	public ResourceStreamPackager(String resourceName) throws ISOException {
		super(ResourceStreamPackager.class.getResourceAsStream(resourceName));
	}

}
