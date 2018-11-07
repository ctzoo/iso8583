package com.yada.jpos.extend;

import org.jpos.iso.AsciiPrefixer;

public class IFA_GBK_LLLLLCHAR extends ISOStringFieldPackager {
	public IFA_GBK_LLLLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLLLL);
	}

	public IFA_GBK_LLLLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLLLL);
		checkLength(len, 99999);
	}

	public void setLength(int len) {
		checkLength(len, 99999);
		super.setLength(len);
	}
}