package com.portal.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encrypt {
	/**
	 * Used building output as Hex
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 
	 * 
	 * @param text
	 *         
	 * 
	 * @return 
	 */
	public static String md5(String text) {
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}

		try {
			msgDigest.update(text.getBytes("GBK"));

		} catch (UnsupportedEncodingException e) {

			throw new IllegalStateException("System doesn't support your  EncodingException.");

		}

		byte[] bytes = msgDigest.digest();

		String md5Str = new String(encodeHex(bytes));

		return md5Str;
	}

	public static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}

	public static String md5Hex(File file) throws NoSuchAlgorithmException, IOException {
		FileInputStream is = new FileInputStream(file);  
		StringBuffer md5 = new StringBuffer();  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        byte[] dataBytes = new byte[1024];  
          
        int nread = 0;   
        while ((nread = is.read(dataBytes)) != -1) {  
            md.update(dataBytes, 0, nread);  
        };  
        byte[] mdbytes = md.digest();  
          
        // convert the byte to hex format  
        for (int i = 0; i < mdbytes.length; i++) {  
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));  
        }  
        return md5.toString();  
	}
	
	public static void main(String[] args){
		System.out.println(Md5Encrypt.md5("11uniplay$shop"));
	}
}