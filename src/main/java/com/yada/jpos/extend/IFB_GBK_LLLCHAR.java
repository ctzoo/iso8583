package com.yada.jpos.extend;

import org.jpos.iso.BcdPrefixer;

public class IFB_GBK_LLLCHAR extends ISOStringFieldPackager {
	public IFB_GBK_LLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLL);
	}

	public IFB_GBK_LLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLL);
		checkLength(len, 999);
	}

	public void setLength(int len) {
		checkLength(len, 999);
		super.setLength(len);
	}
}