package com.my.test.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

/**
 * AES工具类 使用base64AndaesEncrypt加密 使用base64AndaesDecrypt解密
 */
public class AESEncryptUtil {

	static final String CIPHER_ALGORITHM_ECB_NO = "AES/ECB/NoPadding";
	static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 *             抛出异常
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length < 2) {
			String str = "admin";
			String encry = base64AndEncrypt(str,
					"1234567890abcdef1234567890abcdef");
			System.out.println(encry);
			System.out.println(base64AndDecrypt(encry,
					"1234567890abcdef1234567890abcdef"));
		} else {
			// String str = "admin";
			String encry = base64AndEncrypt(args[0], args[1]);
			System.out.println(encry);
			System.out.println(base64AndDecrypt(encry, args[1]));
		}
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 */
	public static byte[] EncryptToBytes(String content, String encryptKey) {
		return EncryptToBytes(content.getBytes(), encryptKey);
	}

	/**
	 * AES加密
	 * 
	 * @param encryptContent
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 */
	public static byte[] EncryptToBytes(byte[] encryptContent, String encryptKey) {
		try {
			return aesByMode(encryptContent, encryptKey, Cipher.ENCRYPT_MODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 */
	public static String base64AndEncrypt(String content, String encryptKey) {
		return Base64.encodeBase64URLSafeString(EncryptToBytes(content,
				encryptKey));
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 */
	public static String DecryptByBytes(byte[] encryptBytes, String decryptKey) {
		try {
			byte[] decryptBytes = aesByMode(encryptBytes, decryptKey,
					Cipher.DECRYPT_MODE);
			return new String(decryptBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据mode选择解密或者加密
	 * 
	 * @param content
	 *            要操作的内容
	 * @param key
	 *            密钥
	 * @param MODE
	 *            选择模型 Cipher里面的常量
	 * @return 操作后的串
	 */
	private static byte[] aesByMode(byte[] content, String key, Integer MODE)
			throws Exception {

		// if (key.length() != 16) {
		// if (key.getBytes().length > 16) {
		// key = key.substring(0, 16);
		// } else {
		// key = StrUtil.prefixZero(key, 16);
		// }
		// }
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(MODE, new SecretKeySpec(Hex.decodeHex(key.toCharArray()),
				"AES"));
		System.out.println(Hex.decodeHex(key.toCharArray()).length);
		return cipher.doFinal(content);
		// KeyGenerator kegen = KeyGenerator.getInstance("AES");
		// SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// random.setSeed(key.getBytes());
		// kegen.init(128, random);
		//
		// Cipher cipher = Cipher.getInstance("AES");
		// cipher.init(MODE, new SecretKeySpec(kegen.generateKey().getEncoded(),
		// "AES"));
		// return cipher.doFinal(content);

	}

	/**
	 * AES解密
	 * 
	 * @param normalStr
	 *            带解密的串
	 * @param decryptKey
	 *            密钥
	 * @return 解密后的string
	 */
	public static String DecryptByStr(String normalStr, String decryptKey) {
		return StringUtils.isEmpty(normalStr) ? null : DecryptByBytes(
				normalStr.getBytes(), decryptKey);
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 */
	public static String base64AndDecrypt(String encryptStr, String decryptKey) {
		return StringUtils.isEmpty(encryptStr) ? null : DecryptByBytes(
				Base64.decodeBase64(encryptStr), decryptKey);
	}
}
