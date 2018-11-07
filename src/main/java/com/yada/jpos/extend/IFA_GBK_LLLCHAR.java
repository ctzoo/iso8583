package com.yada.jpos.extend;

import org.jpos.iso.AsciiPrefixer;

public class IFA_GBK_LLLCHAR extends ISOStringFieldPackager {
	public IFA_GBK_LLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLL);
	}

	public IFA_GBK_LLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLL);
		checkLength(len, 999);
	}

	public void setLength(int len) {
		checkLength(len, 999);
		super.setLength(len);
	}
}