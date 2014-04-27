package components;

import java.util.HashMap;

/**
 * The Class ProtocolConstans.
 *
 * @date 2014-2-28 19:45:02
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class ProtocolConstans {
	
	/** ip协议支持的协议号. */
	private static final HashMap<String,String> ipProtocolNumbersDecimal = new HashMap<String,String>();
	private static final HashMap<String,String> tcpProtocolNumbersDecimal = new HashMap<String,String>();
	static{
		ipProtocolNumbersDecimal.put("0", "HOPOPT,IPv6 Hop-by-Hop Option");
		ipProtocolNumbersDecimal.put("1", "ICMP,Internet Control Message Protocol");
		ipProtocolNumbersDecimal.put("2", "IGMP,Internet Group Management Protocol");
		ipProtocolNumbersDecimal.put("6", "TCP,Transmission Control Protocol");
		ipProtocolNumbersDecimal.put("17", "UDP,User Datagram Protocol");
		ipProtocolNumbersDecimal.put("41", "IPv6,IPv6 Encapsulation");
		
		tcpProtocolNumbersDecimal.put("20", "FTP,File Transfer Protocol,20");
		tcpProtocolNumbersDecimal.put("21", "FTP,File Transfer Protocol,21");
		tcpProtocolNumbersDecimal.put("22", "SSH,Secure Shell");
		tcpProtocolNumbersDecimal.put("23", "TELNET,remote login service");
		tcpProtocolNumbersDecimal.put("25", "SMTP,Simple Mail Transfer Protocol");
		tcpProtocolNumbersDecimal.put("53", "DNS,Domain Name System ");
		tcpProtocolNumbersDecimal.put("80", "HTTP,Hypertext Transfer Protocol");
		tcpProtocolNumbersDecimal.put("110", "POP3,Post Office Protocol");
		tcpProtocolNumbersDecimal.put("119", "NNTP,Network News Transfer Protocol ");
		tcpProtocolNumbersDecimal.put("143", "IMAP,Internet Message Access Protocol");
		tcpProtocolNumbersDecimal.put("161", "SNMP, Simple Network Management Protocol");
		tcpProtocolNumbersDecimal.put("194", "IRC,Internet Relay Chat");
		tcpProtocolNumbersDecimal.put("443", "HTTPS,HTTP Secure");
		tcpProtocolNumbersDecimal.put("465", "SMTPS,SMTP Secure");
		
		
		
	}
	public static HashMap<String, String> getIpprotocolnumbersdecimal() {
		return ipProtocolNumbersDecimal;
	}
	public static HashMap<String, String> getTcpprotocolnumbersdecimal() {
		return tcpProtocolNumbersDecimal;
	}
	
}
