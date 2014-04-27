package components.internetlayer.arp;

import java.util.ArrayList;
import java.util.List;

import common.NumberUtils;
import components.PayLoad;

/**
 * The Class ArpFrame.
 * 
 * @date 2014-2-26 22:59:05
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class ArpPacket extends PayLoad {
	/** 原始bytes数据，按照网络字节序存储。. */
	private List<String> originalBytes = new ArrayList<String>();
	
	/** 硬件类型：2字节,1表示以太网数据链路.  index 0*/
	private String hardwareType = "";
	
	/** 协议类型：2字节. index 2*/
	private String protocolType = "";
	
	/** 硬件地址长度:1字节. index 4 */
	private String hardwareAddressLength = "";
	
	/** 协议类型长度:1字节. index 5*/
	private String prorocolAddressLength = "";
	
	/** 操作类型：2字节,1表示请求，2表示响应. index 6 */
	private String operation = "";
	
	/** 发送端硬件地址:6字节. index 8 */
	private String senderHardwareAddress = "";
	
	/** 发送端协议地址:4字节.  index 14*/
	private String senderProtocolAddress = "";
	
	/** 接收端硬件地址:6字节.  index 18*/
	private String targetHardwareAddress = "";
	
	/** 接收端协议地址:4字节. index 24*/
	private String targetProtocolAddress = "";

	/**
	 * Instantiates a new ArpFrame.
	 *
	 * @param bytes comments
	 */
	public ArpPacket(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			originalBytes.addAll(bytes);
			this.hardwareType = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(0),originalBytes.get(1)});
			this.protocolType = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(2),originalBytes.get(3)});
			this.hardwareAddressLength = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(4)});
			this.prorocolAddressLength = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(5)});
			this.operation = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(6),originalBytes.get(7)});
			this.senderHardwareAddress = NumberUtils.getHexAddressBySingleByte(new String[]{originalBytes.get(8),originalBytes.get(9),
					originalBytes.get(10),originalBytes.get(11),originalBytes.get(12),originalBytes.get(13)});
			this.senderProtocolAddress = NumberUtils.getIpAddressBySingleByte(new String[]{originalBytes.get(14),originalBytes.get(15),
					originalBytes.get(16),originalBytes.get(17)});
			this.targetHardwareAddress = NumberUtils.getHexAddressBySingleByte(new String[]{originalBytes.get(18),originalBytes.get(19),
					originalBytes.get(20),originalBytes.get(21),originalBytes.get(22),originalBytes.get(23)});
			this.targetProtocolAddress = NumberUtils.getIpAddressBySingleByte(new String[]{originalBytes.get(24),originalBytes.get(25),
					originalBytes.get(26),originalBytes.get(27)});
		}
	}

	public List<String> getOriginalBytes() {
		return originalBytes;
	}

	public void setOriginalBytes(List<String> originalBytes) {
		this.originalBytes = originalBytes;
	}

	public String getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getHardwareAddressLength() {
		return hardwareAddressLength;
	}

	public void setHardwareAddressLength(String hardwareAddressLength) {
		this.hardwareAddressLength = hardwareAddressLength;
	}

	public String getProrocolAddressLength() {
		return prorocolAddressLength;
	}

	public void setProrocolAddressLength(String prorocolAddressLength) {
		this.prorocolAddressLength = prorocolAddressLength;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSenderHardwareAddress() {
		return senderHardwareAddress;
	}

	public void setSenderHardwareAddress(String senderHardwareAddress) {
		this.senderHardwareAddress = senderHardwareAddress;
	}

	public String getSenderProtocolAddress() {
		return senderProtocolAddress;
	}

	public void setSenderProtocolAddress(String senderProtocolAddress) {
		this.senderProtocolAddress = senderProtocolAddress;
	}

	public String getTargetHardwareAddress() {
		return targetHardwareAddress;
	}

	public void setTargetHardwareAddress(String targetHardwareAddress) {
		this.targetHardwareAddress = targetHardwareAddress;
	}

	public String getTargetProtocolAddress() {
		return targetProtocolAddress;
	}

	public void setTargetProtocolAddress(String targetProtocolAddress) {
		this.targetProtocolAddress = targetProtocolAddress;
	}
	
	public void print(){
		System.out.println("arp 字节信息:"+originalBytes.get(0)+"---"+originalBytes.get(1));
		System.out.println("arp hardwareType:"+this.hardwareType);
		System.out.println("arp protocolType:"+this.protocolType);
		System.out.println("arp hardwareAddressLength:"+this.hardwareAddressLength);
		System.out.println("arp prorocolAddressLength:"+this.prorocolAddressLength);
		System.out.println("arp senderHardwareAddress:"+this.senderHardwareAddress);
		System.out.println("arp senderProtocolAddress:"+this.senderProtocolAddress);
		System.out.println("arp targetHardwareAddress:"+this.targetHardwareAddress);
		System.out.println("arp targetProtocolAddress:"+this.targetProtocolAddress);
	}

	@Override
	public String toString() {
		return "ArpPacket [originalBytes=" + originalBytes + ", hardwareType="
				+ hardwareType + ", protocolType=" + protocolType
				+ ", hardwareAddressLength=" + hardwareAddressLength
				+ ", prorocolAddressLength=" + prorocolAddressLength
				+ ", operation=" + operation + ", senderHardwareAddress="
				+ senderHardwareAddress + ", senderProtocolAddress="
				+ senderProtocolAddress + ", targetHardwareAddress="
				+ targetHardwareAddress + ", targetProtocolAddress="
				+ targetProtocolAddress + "]";
	}
	
}
