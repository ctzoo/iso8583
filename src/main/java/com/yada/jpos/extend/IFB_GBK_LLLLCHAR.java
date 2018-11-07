package com.yada.jpos.extend;

import org.jpos.iso.BcdPrefixer;

public class IFB_GBK_LLLLCHAR extends ISOStringFieldPackager {
	public IFB_GBK_LLLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLLL);
	}

	public IFB_GBK_LLLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LLLL);
		checkLength(len, 9999);
	}

	public void setLength(int len) {
		checkLength(len, 9999);
		super.setLength(len);
	}
}
