package com.yada.jpos.extend;

import org.jpos.iso.NullPrefixer;

public class IF_GBK_CHAR extends ISOStringFieldPackager {
	public IF_GBK_CHAR() {
		super(0, null, EncodingLiteralInterpreter
				.getInterpreter("GBK"), NullPrefixer.INSTANCE);
	}

	public IF_GBK_CHAR(int len, String description) {
		super(len, description,
				EncodingLiteralInterpreter.getInterpreter("GBK"),
				NullPrefixer.INSTANCE);
	}
}