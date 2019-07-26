package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MD5Helper {
	private static final Log logger = LogFactory.getLog(MD5Helper.class);
	/**
	 * 用MD5算法加密
	 * @param in String : 待加密的原文
	 * @return String : 加密后的密文，如果原文为空，则返回null;
	 */
	public static String encode(final String in){
		return encode(in, "");
	}
	/**
	 * 用MD5算法加密
	 * @param in String : 待加密的原文
	 * @param charset String : 加密算法字符集
	 * @return String : 加密后的密文，若出错或原文为null，则返回null
	 */
	public static String encode(final String in, final String charset){
		if(in == null) return null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(StringHelper.isEmpty(charset)){
				md.update(in.getBytes());
			}else{
				try{
					md.update(in.getBytes(charset));
				}catch(Exception e){
					md.update(in.getBytes());
				}
			}
			byte[] digesta = md.digest();
			return StringHelper.bytes2Hex(digesta);
		}catch(java.security.NoSuchAlgorithmException ex){
			//出错
			logger.error("encode("+in+","+charset+"):NoSuchAlgorithmException -->"+ex.getMessage());
			return null;
		}
	}
	public static byte[] md5(byte[] bytes){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			byte[] ret = digest.digest();
			return ret;
		} catch (NoSuchAlgorithmException ex){
			logger.error("encode(byte[]):NoSuchAlgorithmException -->"+ex.getMessage());
			return null;
		}		
	}
	public static void main(String[] args){
		//f321b2bpaykey,,,,,,,,,,,,,,,202cb962ac59075b964b07152d234b70(123)
		System.out.println(MD5Helper.encode("123"));
	}
}
