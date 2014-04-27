package components.linklayer;

import java.util.ArrayList;
import java.util.List;

import common.NumberUtils;

/**
 * 局域网数据帧头部.
 * 
 * @date 2014-2-18 17:07:50
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class LanFrameHeader {

	/** header. */
	private List<String> header = new ArrayList<String>();

	/** 来源地址：6字节，字符串类型，顺序解析. */
	private String sourceMac = "";

	/** 目标地址：6字节，字符串类型，顺序解析. */
	private String destMac = "";

	/** 协议类型：2字节，该字段为数字类型,按数字进行转换. */
	private String protocol = "";

	/** isArp. */
	private boolean isArp = false;
	
	/** isIpv6. */
	private boolean isIpv6 = false;
	
	/** isIpv4. */
	private boolean isIpv4 = false;

	/**
	 * header长度范围(14,16,17,22),14为目前实际标准中的帧Ethernet v2标准中的长度。.
	 * 
	 * @param header
	 *            comments
	 */
	public LanFrameHeader(List<String> header) {
		if (header != null && header.size() > 0) {
			this.header.addAll(header);
			// get destMac
			for (int i = 0; i < 6; i++) {
				if (destMac.equals("")) {
					// 转换成书写顺序后，转换成16进制
					destMac = NumberUtils.bitToHex(NumberUtils
							.reverserStr(header.get(i)));
				} else {
					// 转换成书写顺序后，转换成16进制
					destMac = destMac
							+ "："
							+ NumberUtils.bitToHex(NumberUtils
									.reverserStr(header.get(i)));
				}
			}
			// get sourceMac
			for (int i = 6; i < 12; i++) {
				if (sourceMac.equals("")) {
					sourceMac = NumberUtils.bitToHex(NumberUtils
							.reverserStr(header.get(i)));
				} else {
					sourceMac = sourceMac
							+ "："
							+ NumberUtils.bitToHex(NumberUtils
									.reverserStr(header.get(i)));
				}
			}
			// 获取源地址后的2个字节数据
			String numberStr = NumberUtils.reverserStr(header.get(12))
					+ NumberUtils.reverserStr(header.get(13));
			this.protocol = NumberUtils.convert(numberStr, 2, 16,true);
			// System.out.println("第12，13字节"+header.get(12)+"----"+header.get(13));
			// System.out.println("第12，13字节"+NumberUtils.reverserStr(header.get(13))+"----"+NumberUtils.reverserStr(header.get(12)));
			// System.out.println("type:" + numberStr);
			
//			if (this.protocol.length() == 3) {
//				this.protocol = "0x0" + this.protocol;
//			} else if (this.protocol.length() == 4) {
//				this.protocol = "0x" + this.protocol;
//			}
			Integer number = Integer.parseInt(NumberUtils.convert(numberStr, 2,
					10));
			if (number < 1600) {
				System.out
						.println("This packet is not Ethernet V2(ARPA) packet");
			} else {
				System.out.println("This packet is Ethernet V2(ARPA) packet");
			}
			if("0x0800".equals(this.protocol)){
				this.isIpv4 = true;
			}else if("0x0806".equals(this.protocol)){
				this.isArp = true;
			}else if("0x86dd".equals(this.protocol)||"0x86DD".equals(this.protocol)){
				this.isIpv6 = true;
			}else{
				System.out.println("frame type is "+ this.protocol);
			}
		}
	}

	/**
	 * Gets sourceMac.
	 * 
	 * @return sourceMac
	 */
	public String getSourceMac() {
		return sourceMac;
	}

	/**
	 * Sets sourceMac.
	 * 
	 * @param sourceMac
	 *            comments
	 */
	public void setSourceMac(String sourceMac) {
		this.sourceMac = sourceMac;
	}

	/**
	 * Gets destMac.
	 * 
	 * @return destMac
	 */
	public String getDestMac() {
		return destMac;
	}

	/**
	 * Sets destMac.
	 * 
	 * @param destMac
	 *            comments
	 */
	public void setDestMac(String destMac) {
		this.destMac = destMac;
	}

	/**
	 * Gets protocol.
	 * 
	 * @return protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets protocol.
	 * 
	 * @param protocol
	 *            comments
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * Gets header.
	 * 
	 * @return header
	 */
	public List<String> getHeader() {
		return header;
	}

	/**
	 * Sets header.
	 * 
	 * @param header
	 *            comments
	 */
	public void setHeader(List<String> header) {
		this.header = header;
	}

	/**
	 * Checks if is arp.
	 *
	 * @return true, if is arp
	 */
	public boolean isArp() {
		return isArp;
	}

	/**
	 * Sets arp.
	 *
	 * @param isArp comments
	 */
	public void setArp(boolean isArp) {
		this.isArp = isArp;
	}

	/**
	 * Checks if is ipv6.
	 *
	 * @return true, if is ipv6
	 */
	public boolean isIpv6() {
		return isIpv6;
	}

	/**
	 * Sets ipv6.
	 *
	 * @param isIpv6 comments
	 */
	public void setIpv6(boolean isIpv6) {
		this.isIpv6 = isIpv6;
	}

	/**
	 * Checks if is ipv4.
	 *
	 * @return true, if is ipv4
	 */
	public boolean isIpv4() {
		return isIpv4;
	}

	/**
	 * Sets ipv4.
	 *
	 * @param isIpv4 comments
	 */
	public void setIpv4(boolean isIpv4) {
		this.isIpv4 = isIpv4;
	}
	
	

}
