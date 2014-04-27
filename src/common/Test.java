package common;

public class Test {

	public static void main(String[] args) {
		byte b = Byte.parseByte("1");
		Byte bb = new Byte(b);
		System.out.println(bb.toString());
		//System.out.println(Integer.toBinaryString(b));
		//NumberUtils.convert(b+"", 10, 2);
	}

}
