package com.yada.jpos.extend;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.jpos.iso.ISOException;
import org.jpos.iso.Interpreter;

public class EncodingLiteralInterpreter implements Interpreter {
	private static HashMap<String, EncodingLiteralInterpreter> interpreterMap;

	static {
		interpreterMap = new HashMap<>();
	}

	public static EncodingLiteralInterpreter getInterpreter(String encoding) {
		if (interpreterMap.containsKey(encoding))
			return interpreterMap.get(encoding);
		else {
			EncodingLiteralInterpreter interpreter = new EncodingLiteralInterpreter(encoding);
			interpreterMap.put(encoding, interpreter);
			return interpreter;
		}
	}

	private String ENCODING;

	public EncodingLiteralInterpreter(String encoding) {
		ENCODING = encoding;
	}

	@Override
	public void interpret(String data, byte[] b, int offset)
			throws ISOException {
		try {
			byte[] raw = data.getBytes(ENCODING);
			System.arraycopy(raw, 0, b, offset, raw.length);
		} catch (UnsupportedEncodingException ignored) {
		}
	}

	@Override
	public String uninterpret(byte[] rawData, int offset, int length)
			throws ISOException {
		try {
			return new String(rawData, offset, length, ENCODING);
		} catch (UnsupportedEncodingException ignored) {
		}
		return null;
	}

	@Override
	public int getPackedLength(int nDataUnits) {
		return nDataUnits;
	}

	public byte[] getBytes(String data) throws UnsupportedEncodingException
	{
		return data.getBytes(ENCODING);
	}
}