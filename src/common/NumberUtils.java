package common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Class NumberUtils. 数字工具类
 * 
 * @date 2013-11-13 17:42:54
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_tools 1.0
 */
public class NumberUtils {

	/**
	 * 进制转换。 将某种进制的数字转换成指定进制， 并以字符串的形式输出转换结果。 例如：convert("11", 10, 16)=b
	 * 将10进制的"11"转换成16进制的数字.
	 * 
	 * @param number
	 *            需要处理的数字
	 * @param sourceRadix
	 *            数字的当前进制
	 * @param targetRadix
	 *            需要转换成的进制
	 * @return String
	 */
	public static String convert(String number, int sourceRadix, int targetRadix) {
		Long l = Long.parseLong(number, sourceRadix);
		String result = Long.toString(l, targetRadix);
		if (targetRadix == 16) {
			// 补前缀0
			if (result.length() % 2 == 1) {
				result = "0" + result;
			} 
		}
		return result;
	}
	
	/**
	 * 进制转换。 将某种进制的数字转换成指定进制， 并以字符串的形式输出转换结果。 例如：convert("11", 10, 16)=b
	 * 将10进制的"11"转换成16进制的数字.
	 *
	 * @param number 需要处理的数字
	 * @param sourceRadix 数字的当前进制
	 * @param targetRadix 需要转换成的进制
	 * @param addHexFlag 是否添加16进制标记"0x",只有在targetRadix=16时，该参数才有效
	 * @return String
	 */
	public static String convert(String number, int sourceRadix, int targetRadix,boolean addHexFlag) {
		Long l = Long.parseLong(number, sourceRadix);
		String result = Long.toString(l, targetRadix);
		if (targetRadix == 16) {
			if (result.length() % 2 == 1) {
				result = "0" + result;
			} 
			if(addHexFlag){
				result = "0x"+result;
			}
		}
		return result;
	}
	
	

	/**
	 * 获取一个int类型随机数，根据当前系统的毫秒数.
	 * 
	 * @return random
	 */
	public static int getRandom() {
		Random r = new Random();
		return r.nextInt();
	}

	/**
	 * 注意这里输出的是在内存中实际的位值. (负数在内存中是已补码来保存的)。
	 * 
	 * @param b
	 *            comments
	 * @return String
	 */
	public static String byteToBitString(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}

	/**
	 * 高低位反转.
	 * 
	 * @param b
	 *            comments
	 * @return String
	 */
	public static String byteToReverseBitString(byte b) {
		return "" + (byte) ((b >> 0) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 2) & 0x1) + (byte) ((b >> 3) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 6) & 0x1) + (byte) ((b >> 7) & 0x1);
	}
	
	/**
	 * source为高位在前，低位在后，按照实际存储进行.
	 *
	 * @param source comments
	 * @return byte
	 */
	public static byte bitStringToByte(String source){
		int a = 0xFF;
		int b  =0xFE;
		int c  =0x01;
		byte result = 0x00;
		for (int i = 0; i < source.length(); i++) {
			String temp = source.substring(i,i+1);
			if(temp.equals("1")){
				result = (byte)(result|c);
				result = (byte)(result&a);
			}else{
				result = (byte)(result&b);
			}
			// 最后一次循环，第0位不执行位移
			if(i!=7){
				result = (byte)(result<<1);
			}
		}
		return result;
	}

	/**
	 * 目前只支持8位和以下二进制位数。 注意bitString是二进制的书写格式， 即高位在前，地位在后的格式，例如"0000 0001".表示数字1.
	 * 
	 * @param bitString
	 *            comments
	 * @return String
	 */
	public static String bitToHex(String bitString) {
		String result = "";
		int number10 = 0;
		String source = reverserStr(bitString);
		for (int i = 0; i < source.length(); i++) {
			char a = source.charAt(i);
			if (a == '1') {
				number10 = number10 + pow(2, i);
			}
		}
		result = convert(number10 + "", 10, 16);
		if (result.length() % 2 == 1) {
			result = "0" + result;
		}
		result = result.toUpperCase();
		return result;
	}

	/**
	 * reverserStr.
	 * 
	 * @param source
	 *            comments
	 * @return String
	 */
	public static String reverserStr(String source) {
		StringBuilder sb = new StringBuilder(source);
		return sb.reverse().toString();
	}

	/**
	 * 求base的p次方, 仅限制在int的数值范围内。.
	 * 
	 * @param base
	 *            comments
	 * @param p
	 *            comments
	 * @return int
	 */
	public static int pow(int base, int p) {
		int result = 1;
		if (p == 0) {
			return 1;
		}
		for (int i = 0; i < p; i++) {
			result = result * base;
		}
		return result;
	}

	/**
	 * 使用大端字节序，把按网络字节序排列的多个二进制数转换成16进制数.
	 *
	 * @param sourceArray comments
	 * @return hexByBigEndian
	 */
	public static String getHexByBigEndian(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = 0; i < sourceArray.length; i++) {
				source = source + NumberUtils.reverserStr(sourceArray[i]);
			}
			return NumberUtils.convert(source, 2, 16,true);
		} else {
			return "0x";
		}
	}
	
	
	/**
	 * 使用小端字节序，把按网络字节序排列的多个二进制数转换成16进制数..
	 *
	 * @param sourceArray comments
	 * @return hexByLittleEndian
	 */
	public static String getHexByLittleEndian(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = sourceArray.length-1; i >=0; i--) {
				source = source + NumberUtils.reverserStr(sourceArray[i]);
			}
			return NumberUtils.convert(source, 2, 16,true);
		} else {
			return "0x";
		}
	}
	
	/**
	 * Gets decimalByBigEndian.
	 *
	 * @param sourceArray comments
	 * @return decimalByBigEndian
	 */
	public static String getDecimalByBigEndian(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = 0; i < sourceArray.length; i++) {
				source = source + NumberUtils.reverserStr(sourceArray[i]);
			}
			return NumberUtils.convert(source, 2, 10);
		} else {
			return "0x";
		}
	}
	
	
	/**
	 * 使用小端字节序，把按网络字节序排列的多个二进制数转换成16进制数..
	 *
	 * @param sourceArray comments
	 * @return hexByLittleEndian
	 */
	public static String getDecimalByLittleEndian(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = sourceArray.length-1; i >=0; i--) {
				source = source + NumberUtils.reverserStr(sourceArray[i]);
			}
			return NumberUtils.convert(source, 2, 10);
		} else {
			return "0x";
		}
	}
	
	
	/**
	 * 按单个字节顺序解析,获取16进制格式地址信息.
	 * 主要用于获取类似mac地址的数据
	 *
	 * @param sourceArray comments
	 * @return hexByBigEndian
	 */
	public static String getHexAddressBySingleByte(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = 0; i < sourceArray.length; i++) {
				String numstr = NumberUtils.reverserStr(sourceArray[i]);
				String hex = NumberUtils.convert(numstr, 2, 16);
				source = source + hex +":";
			}
			source = source.substring(0,source.length()-1);
			return source;
		} else {
			return "0x";
		}
	}
	
	/**
	 * Gets ipAddressBySingleByte.
	 *
	 * @param sourceArray comments
	 * @return ipAddressBySingleByte
	 */
	public static String getIpAddressBySingleByte(String[] sourceArray) {
		if (sourceArray != null && sourceArray.length>0) {
			String source = "";
			for (int i = 0; i < sourceArray.length; i++) {
				String numstr = NumberUtils.reverserStr(sourceArray[i]);
				String dec = NumberUtils.convert(numstr, 2, 10);
				source = source + dec +".";
			}
			source = source.substring(0,source.length()-1);
			return source;
		} else {
			return "0x";
		}
	}
	
	public static List<String> readBytes(byte[] buffer){
		List<String> result = new ArrayList<String>();
		BufferedReader   reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
		try {
			String temp = "";
			while ((temp= reader.readLine())!=null) {
				result.add(temp);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		//System.out.println(NumberUtils.convert("11", 10, 16));
		// System.out.println(NumberUtils.getRandom());
		// System.out.println(NumberUtils.bitToHexString("10001111"));
//		System.out.println(NumberUtils.convert("f1", 16, 2));
//		System.out.println(NumberUtils.reverserStr("12"));
//		System.out.println("line 0 ---:FF:FF:FF:FF:FF:FF");
//		System.out.println("line 1 ---:50:3D:E5:A8:03:3F");
//		System.out.println(NumberUtils.convert("0800", 16, 10));
		// System.out.println(NumberUtils.convert("0000000100000000", 2, 16));
//		System.out.println(NumberUtils.convert("0000100000000000", 2, 16));
		
		//byte b = Byte.parseByte("00000101",2);
		
		System.out.println(NumberUtils.byteToBitString(NumberUtils.bitStringToByte("10010001")));
	}
}
