/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netlink.tools.util;

import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DESUtil
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-21 20:15 fubencheng
 */
public class DESUtil {

	/**
	 * 算法
	 */
	private final static String ALGORITHM = "DES";

	/**
	 * 算法名称/加密模式/填充方式
	 */
	private final static String ALGORITHM_CBC_PADDING = "DES/CBC/PKCS5Padding";

	private final static String ALGORITHM_CBC_NOPADDING = "DES/CBC/NoPadding";

	private final static String ALGORITHM_ECB_PADDING = "DES/ECB/PKCS5Padding";

	private final static String ALGORITHM_ECB_NOPADDING = "DES/ECB/NoPadding";

	/**
	 * 生成随机DES秘钥，HEX字符串
	 *
	 * @return String
	 * @throws Exception Exception
	 */
	public static String generateRandomKey() throws Exception {
		SecureRandom sr = new SecureRandom();

		// 选择DES算法生成一个KeyGenerator对象
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(sr);

		// 生成密钥
		Key key = kg.generateKey();

		return asHex(key.getEncoded());
	}

	/**
	 * DES/ECB/Padding
	 *
	 * @param data byteArray
	 * @param key key
	 * @param noPadding noPadding
	 * @return byteArray
	 * @throws Exception Exception
	 */
	public static byte[] encryptByECB(byte[] data, byte[] key, boolean noPadding) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 用密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 默认PADDING
		Cipher cipher = Cipher.getInstance(ALGORITHM_ECB_PADDING);

		if (noPadding) {
			cipher = Cipher.getInstance(ALGORITHM_ECB_NOPADDING);
		}

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

		// 执行加密运算操作
		return cipher.doFinal(data);
	}

	/**
	 * DES/ECB/Padding
	 *
	 * @param data byteArray
	 * @param key key
	 * @param noPadding noPadding
	 * @return byteArray
	 * @throws Exception Exception
	 */
	public static byte[] decryptByECB(byte[] data, byte[] key, boolean noPadding) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 用密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 默认PADDING
		Cipher cipher = Cipher.getInstance(ALGORITHM_ECB_PADDING);

		if (noPadding) {
			cipher = Cipher.getInstance(ALGORITHM_ECB_NOPADDING);
		}

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

		// 执行解密运算操作
		return cipher.doFinal(data);
	}

	/**
	 * DES/CBC/Padding
	 *
	 * @param data byteArray
	 * @param key key
	 * @param iv iv
	 * @param noPadding noPadding
	 * @return byteArray
	 * @throws Exception Exception
	 */
	public static byte[] encryptByCBC(byte[] data, byte[] key, byte[] iv, boolean noPadding) throws Exception {
		// 用密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 默认PADDING
		Cipher cipher = Cipher.getInstance(ALGORITHM_CBC_PADDING);

		if (noPadding) {
			cipher = Cipher.getInstance(ALGORITHM_CBC_NOPADDING);
		}

		// 用密匙初始化Cipher对象
		IvParameterSpec ivParaSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParaSpec);

		// 执行加密运算操作
		return cipher.doFinal(data);
	}

	/**
	 * DES/CBC/Padding
	 *
	 * @param data byteArray
	 * @param key key
	 * @param iv iv
	 * @param noPadding noPadding
	 * @return byteArray
	 * @throws Exception Exception
	 */
	public static byte[] decryptByCBC(byte[] data, byte[] key, byte[] iv, boolean noPadding) throws Exception {
		// 用密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 默认PADDING
		Cipher cipher = Cipher.getInstance(ALGORITHM_CBC_PADDING);

		if (noPadding) {
			cipher = Cipher.getInstance(ALGORITHM_CBC_NOPADDING);
		}

		// 用密匙初始化Cipher对象
		IvParameterSpec ivParaSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParaSpec);

		// 执行解密运算操作
		return cipher.doFinal(data);
	}

	/**
	 * 字节数组转Hex字符串
	 *
	 * @param bytes byteArray
	 * @return String
	 */
	public static String asHex(byte[] bytes) {
		StringBuilder hexStringBuilder = new StringBuilder();
		for (byte b : bytes) {
			String hexString = Integer.toHexString(0x00FF & b);
			hexStringBuilder.append(hexString.length() == 1 ? "0" + hexString : hexString);
		}

		return hexStringBuilder.toString();
	}

	/**
	 * Hex字符串装字节数组
	 *
	 * @param hexString String
	 * @return byteArray
	 */
	public static byte[] fromHex(String hexString) {
		if (StringUtils.isEmpty(hexString)) {
			return new byte[0];
		}

		byte[] result = new byte[hexString.length() / 2];
		char[] charArray = hexString.toCharArray();
		for (int i = 0, c = 0; i < charArray.length; i += 2, c++) {
			result[c] = (byte) (Integer.parseInt(new String(charArray, i, 2), 16));
		}

		return result;
	}

	/**
	 * 字节数组转字符串
	 *
	 * @param buf byteArray
	 * @return String
	 */
	public static String asString(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length);

		Charset cs = Charset.forName("UTF-8");
		ByteBuffer byteBuf = ByteBuffer.allocate(buf.length);
		byteBuf.put(buf);
		byteBuf.flip();
		CharBuffer cb = cs.decode(byteBuf);

		strbuf.append(cb.array());

		return strbuf.toString();
	}

}
