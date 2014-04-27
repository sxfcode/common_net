package components.linklayer;

import java.util.ArrayList;
import java.util.List;

import common.NumberUtils;
import components.PayLoad;
import components.internetlayer.arp.ArpPacket;
import components.internetlayer.ip.v4.Ipv4Packet;
import components.internetlayer.ip.v6.Ipv6Packet;

/**
 * 
 * 
 * 局域网数据帧
 * 
 * The Class FramePacket. 关于网络传输顺序。 a)
 * Little-Endian就是低位字节排放在内存的低地址端，高位字节排放在内存的高地址端。 b)
 * Big-Endian就是高位字节排放在内存的低地址端，低位字节排放在内存的高地址端。 c) 网络字节序：4个字节的32
 * bit值以下面的次序传输：首先是0～7bit，其次8～15bit，然后16～23bit，最后是24~31bit。这种传输次序称作大端字节序。
 * 
 * @date 2014-2-18 17:23:34
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class LanFramePacket {

	/** 原始bytes数据，按照网络字节序存储。. */
	private List<String> originalBytes = new ArrayList<String>();

	/** header. */
	private LanFrameHeader header = null;
	/** 来源地址：6字节，字符串类型，顺序解析. */
	private String sourceMac = "";

	/** 目标地址：6字节，字符串类型，顺序解析. */
	private String destMac = "";

	/** 协议类型：2字节，该字段为数字类型,按数字进行转换. */
	private String protocol = "";
	
	/** 负载数据. */
	private PayLoad payLoad = new PayLoad();

	/** 校验和. */
	private String checksum = "";

	/**
	 * Instantiates a new LanFramePacket.
	 * 
	 * @param bytes
	 *            comments
	 */
	public LanFramePacket(List<String> bytes) {
		init(bytes);
	}

	/**
	 * Instantiates a new LanFramePacket.
	 * 
	 * @param bytes
	 *            comments
	 */
	public LanFramePacket(byte[] bytes) {
		init(parseBytes(bytes));
	}

	/**
	 * init.
	 *
	 * @param bytes comments
	 */
	public void init(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			this.originalBytes.addAll(bytes);
			// 默认以太帧
			this.header = new LanFrameHeader(bytes.subList(0, 14));
			this.sourceMac = header.getSourceMac();
			this.destMac = header.getDestMac();
			this.protocol = header.getProtocol();
			if(this.header.isArp()){
				this.payLoad = new ArpPacket(bytes.subList(14,bytes.size()-4));
			}else if(this.header.isIpv4()){
				this.payLoad = new Ipv4Packet(bytes.subList(14,bytes.size()-4));
			}else if(this.header.isIpv6()){
				this.payLoad = new Ipv6Packet(bytes.subList(14,bytes.size()-4));
			}
			System.out.println();
			System.out.println(this.toString());
		}
	}

	/**
	 * parseBytes.
	 * 
	 * @param buffer
	 *            comments
	 * @return List
	 */
	public static List<String> parseBytes(byte[] buffer) {
		List<String> bytes = new ArrayList<String>();
		for (byte b : buffer) {
			bytes.add(NumberUtils.byteToReverseBitString(b));
		}
		return bytes;
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
	 * Gets header.
	 * 
	 * @return header
	 */
	public LanFrameHeader getHeader() {
		return header;
	}

	/**
	 * Sets header.
	 * 
	 * @param header
	 *            comments
	 */
	public void setHeader(LanFrameHeader header) {
		this.header = header;
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



	@Override
	public String toString() {
		return "LanFramePacket [header="
				+ header + ", sourceMac=" + sourceMac + ", destMac=" + destMac
				+ ", protocol=" + protocol + ", payLoad=" + payLoad
				+ ", checksum=" + checksum + "]";
	}

	/**
	 * Gets checksum.
	 * 
	 * @return checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * Sets checksum.
	 * 
	 * @param checksum
	 *            comments
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public PayLoad getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(PayLoad payLoad) {
		this.payLoad = payLoad;
	}
	
	public void printNetBytes(){
		int count = 0;
		int number = 0;
		for (String m : this.originalBytes) {
			if(count==0){
				System.out.println("frame行"+number+":");
			}
			System.out.print(m+"   ");
			count++;
			number ++;
			if(count==4){
				count=0;
				System.out.print("\n");
			}
		}
	}
}
