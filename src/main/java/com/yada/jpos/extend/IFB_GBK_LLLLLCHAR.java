package com.yada.jpos.extend;

import org.jpos.iso.BcdPrefixer;

public class IFB_GBK_LLLLLCHAR extends ISOStringFieldPackager {
	public IFB_GBK_LLLLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLLLL);
	}

	public IFB_GBK_LLLLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLLLL);
		checkLength(len, 99999);
	}

	public void setLength(int len) {
		checkLength(len, 99999);
		super.setLength(len);
	}
}
