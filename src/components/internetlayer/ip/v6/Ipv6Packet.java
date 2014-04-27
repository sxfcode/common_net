package components.internetlayer.ip.v6;

import java.util.ArrayList;
import java.util.List;

import components.PayLoad;

/**
 * The Class Ipv6Packet.
 * 
 * @date 2014-2-27 8:05:15
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class Ipv6Packet extends PayLoad{

	/** originalBytes. */
	private List<String> originalBytes = new ArrayList<String>();

	/** 协议版本. 4bit */
	private String version = "";

	/** 通信类别 8bit. */
	private String trafficClass = "";

	/** 流标记 20bit. */
	private String flowLabel = "";

	/** 负载长度 16bit. */
	private String payLoadLength = "";

	/** 下一个头部8bit. */
	private String nextHeader = "";

	/** 跳段数限 8bit. */
	private String hopLimit = "";

	/** 源地址 128bit. */
	private String sourceAddress = "";

	/** 目标地址 128bit. */
	private String destAddress = "";

	/**
	 * Instantiates a new Ipv6Packet.
	 * 
	 * @param bytes
	 *            comments
	 */
	public Ipv6Packet(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			originalBytes.addAll(bytes);
			String lineOne = "";
			for (int i = 0; i < 4; i++) {
				lineOne = lineOne + originalBytes.get(i);
			}
			this.version = lineOne.substring(0, 4);
			this.trafficClass = lineOne.substring(4, 12);
			this.flowLabel = lineOne.substring(12, 32);
			System.out.println("ipv6 version"+this.version);
			System.out.println("ipv6 trafficClass"+this.trafficClass);
			System.out.println("ipv6 flowLabel"+this.flowLabel);
			//printNetBytes();
		}
	}

	/**
	 * Gets originalBytes.
	 * 
	 * @return originalBytes
	 */
	public List<String> getOriginalBytes() {
		return originalBytes;
	}

	/**
	 * Sets originalBytes.
	 * 
	 * @param originalBytes
	 *            comments
	 */
	public void setOriginalBytes(List<String> originalBytes) {
		this.originalBytes = originalBytes;
	}

	/**
	 * Gets version.
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets version.
	 * 
	 * @param version
	 *            comments
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets trafficClass.
	 * 
	 * @return trafficClass
	 */
	public String getTrafficClass() {
		return trafficClass;
	}

	/**
	 * Sets trafficClass.
	 * 
	 * @param trafficClass
	 *            comments
	 */
	public void setTrafficClass(String trafficClass) {
		this.trafficClass = trafficClass;
	}

	/**
	 * Gets flowLabel.
	 * 
	 * @return flowLabel
	 */
	public String getFlowLabel() {
		return flowLabel;
	}

	/**
	 * Sets flowLabel.
	 * 
	 * @param flowLabel
	 *            comments
	 */
	public void setFlowLabel(String flowLabel) {
		this.flowLabel = flowLabel;
	}

	/**
	 * Gets payLoadLength.
	 * 
	 * @return payLoadLength
	 */
	public String getPayLoadLength() {
		return payLoadLength;
	}

	/**
	 * Sets payLoadLength.
	 * 
	 * @param payLoadLength
	 *            comments
	 */
	public void setPayLoadLength(String payLoadLength) {
		this.payLoadLength = payLoadLength;
	}

	/**
	 * Gets nextHeader.
	 * 
	 * @return nextHeader
	 */
	public String getNextHeader() {
		return nextHeader;
	}

	/**
	 * Sets nextHeader.
	 * 
	 * @param nextHeader
	 *            comments
	 */
	public void setNextHeader(String nextHeader) {
		this.nextHeader = nextHeader;
	}

	/**
	 * Gets hopLimit.
	 * 
	 * @return hopLimit
	 */
	public String getHopLimit() {
		return hopLimit;
	}

	/**
	 * Sets hopLimit.
	 * 
	 * @param hopLimit
	 *            comments
	 */
	public void setHopLimit(String hopLimit) {
		this.hopLimit = hopLimit;
	}

	/**
	 * Gets sourceAddress.
	 * 
	 * @return sourceAddress
	 */
	public String getSourceAddress() {
		return sourceAddress;
	}

	/**
	 * Sets sourceAddress.
	 * 
	 * @param sourceAddress
	 *            comments
	 */
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	/**
	 * Gets destAddress.
	 * 
	 * @return destAddress
	 */
	public String getDestAddress() {
		return destAddress;
	}

	/**
	 * Sets destAddress.
	 * 
	 * @param destAddress
	 *            comments
	 */
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public void printNetBytes() {
		int count = 0;
		int number = 0;
		for (String m : this.originalBytes) {
			if (count == 0) {
				System.out.println("ip6行" + number + ":");
			}
			System.out.print(m + "   ");
			count++;
			number++;
			if (count == 4) {
				count = 0;
				System.out.print("\n");
			}
		}
	}

}
