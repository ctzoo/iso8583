package com.yada.jpos.extend;

import org.jpos.iso.BcdPrefixer;

public class IFB_GBK_LLCHAR extends ISOStringFieldPackager {
	public IFB_GBK_LLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LL);
	}

	public IFB_GBK_LLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), BcdPrefixer.LL);
		checkLength(len, 99);
	}

	public void setLength(int len) {
		checkLength(len, 99);
		super.setLength(len);
	}
}
