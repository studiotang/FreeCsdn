package com.free.csdn.util;

import java.security.MessageDigest;

/**
 * 对外提供getMD5(String)方法
 * 
 * @author randyjia
 * 
 */
public class MD5 {

	public static String getMD5(String source) throws Exception {
		String resultHash = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] result = new byte[md5.getDigestLength()];
		md5.reset();
		md5.update(source.getBytes("UTF-8"));
		result = md5.digest();

		StringBuffer buf = new StringBuffer(result.length * 2);

		for (int i = 0; i < result.length; i++) {
			int intVal = result[i] & 0xff;
			if (intVal < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}

		resultHash = buf.toString();

		return resultHash.toString();

	}
}
