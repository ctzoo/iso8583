package com.yada.jpos.extend;


import java.io.IOException;
import java.io.InputStream;

import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.NullPrefixer;
import org.jpos.iso.Prefixer;

public class ISOStringFieldPackager extends ISOFieldPackager {
	private EncodingLiteralInterpreter interpreter;
	private Prefixer prefixer;

	public ISOStringFieldPackager() {
		super();
		this.interpreter = EncodingLiteralInterpreter.getInterpreter("GBK");
		this.prefixer = NullPrefixer.INSTANCE;
	}

	public ISOStringFieldPackager(EncodingLiteralInterpreter interpreter, Prefixer prefixer) {
		super();
		this.interpreter = interpreter;
		this.prefixer = prefixer;
	}

	public ISOStringFieldPackager(int maxLength, String description, EncodingLiteralInterpreter interpreter,
			Prefixer prefixer) {
		super(maxLength, description);
		this.interpreter = interpreter;
		this.prefixer = prefixer;
	}

	public void setInterpreter(EncodingLiteralInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	public void setPrefixer(Prefixer prefixer) {
		this.prefixer = prefixer;
	}

	public int getMaxPackedLength() {
		return prefixer.getPackedLength() + interpreter.getPackedLength(getLength());
	}

	private String makeExceptionMessage(ISOComponent c, String operation) {
		Object fieldKey = "unknown";
		if (c != null) {
			try {
				fieldKey = c.getKey();
			} catch (Exception ignore) {
			}
		}
		return this.getClass().getName() + ": Problem " + operation + " field " + fieldKey;
	}

	public byte[] pack(ISOComponent c) throws ISOException {
		try {
			byte[] rawData;

			if (c.getValue() instanceof byte[])
				rawData = (byte[]) c.getValue();
			else
				rawData = interpreter.getBytes((String) c.getValue());

			if (rawData.length > getLength()) {
				throw new ISOException("Field length " + rawData.length + " too long. Max: " + getLength());
			}

			if (prefixer == NullPrefixer.INSTANCE) {
				byte space = ' ';
				byte[] retData = new byte[getLength()];
				System.arraycopy(rawData, 0, retData, 0, rawData.length);
				for (int i = rawData.length; i < getLength(); i++) {
					retData[i] = space;
				}

				return retData;
			} else {

				byte[] retData = new byte[prefixer.getPackedLength() + rawData.length];
				prefixer.encodeLength(rawData.length, retData);
				System.arraycopy(rawData, 0, retData, prefixer.getPackedLength(), rawData.length);
				return retData;
			}
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "packing"), e);
		}
	}

	public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
		try {
			int len = prefixer.decodeLength(b, offset);
			if (len == -1) {
				len = getLength();
			} else if (getLength() > 0 && len > getLength())
				throw new ISOException("Field length " + len + " too long. Max: " + getLength());

			int lenLen = prefixer.getPackedLength();
			String unpacked = interpreter.uninterpret(b, offset + lenLen, len);

			if (prefixer == NullPrefixer.INSTANCE) {
				int strLen = unpacked.length();
				for (int i = strLen; i > 0; i--) {
					if (unpacked.charAt(i - 1) != ' ') {
						unpacked = unpacked.substring(0, i);
						break;
					}
				}
			}

			c.setValue(unpacked);
			return lenLen + interpreter.getPackedLength(len);
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}

	public void unpack(ISOComponent c, InputStream in) throws IOException, ISOException {
		try {
			int lenLen = prefixer.getPackedLength();
			int len;
			if (lenLen == 0) {
				len = getLength();
			} else {
				len = prefixer.decodeLength(readBytes(in, lenLen), 0);
				if (getLength() > 0 && len > 0 && len > getLength())
					throw new ISOException("Field length " + len + " too long. Max: " + getLength());
			}
			int packedLen = interpreter.getPackedLength(len);
			String unpacked = interpreter.uninterpret(readBytes(in, packedLen), 0, len);
			c.setValue(unpacked);
		} catch (ISOException e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}

	protected void checkLength(int len, int maxLength) throws IllegalArgumentException {
		if (len > maxLength) {
			throw new IllegalArgumentException("Length " + len + " too long for " + getClass().getName());
		}
	}
}
