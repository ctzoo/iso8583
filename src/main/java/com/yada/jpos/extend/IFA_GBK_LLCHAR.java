package com.yada.jpos.extend;

import org.jpos.iso.AsciiPrefixer;

public class IFA_GBK_LLCHAR extends ISOStringFieldPackager {
	public IFA_GBK_LLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LL);
	}

	public IFA_GBK_LLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LL);
		checkLength(len, 99);
	}

	public void setLength(int len) {
		checkLength(len, 99);
		super.setLength(len);
	}
}
