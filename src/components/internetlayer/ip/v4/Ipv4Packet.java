package components.internetlayer.ip.v4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.NumberUtils;
import components.PayLoad;
import components.ProtocolConstans;
import components.transportlayer.TcpPacket;
import components.transportlayer.UdpPacket;

/**
 * The Class Ipv4Packet.
 * IP首部中的字段均以大端序包装
 * 
 * @date 2014-2-27 8:05:11
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class Ipv4Packet extends PayLoad {

	/** originalBytes. */
	private List<String> originalBytes = new ArrayList<String>();
	
	/** 版本4bit，index 0. */
	private String version = "";
	
	/** 首部长度4bit，index 4.  首部长度为n,则表示首部有n个32位*/
	private String headerLength = "";
	
	/** 服务类型dscp:6bit, index 8 . */
	private String diffServ = "";
	
	/** 显示阻塞通告ECN 2bit,index 14. */
	private String explicitCongestionNotification = "";
	
	/** 全长 2字节, 全长的长度中包含了首部的长度,长度单位为字节. */
	private String totalLength = "";
	
	/** 标识符:2字节. */
	private String id  = "";
	
	/** 标志:3bit. */
	private String flag = "";
	
	/** 分片便宜:13bit. */
	private String fragmentOffset = "";
	
	/** 
	 * 存活时间ttl:1字节.
	 * 存活时间以秒为单位，但小于一秒的时间均向上取整到一秒。
	 * 在现实中，这实际上成了一个跳数计数器：报文经过的每个路由器都将此字段减一，当此字段等于0时，报文不再向下一跳传送并被丢弃。
	 * 常规地，一份ICMP报文被发回报文发送端说明其发送的报文已被丢弃。这也是traceroute的核心原理。
	 *  */
	private String timeToLive = "";
	
	/** 协议:1字节. */
	private String protocol = "";
	
	/** 首部校验和. */
	private String headerChecksum = "";
	
	/** 源ip. */
	private String sourceIp = "";
	
	/** 目标id. */
	private String destIp = "";
	
	/** 选项:4字节，可选. */
	private String option = "";
	
	/** payLoad. */
	private PayLoad payLoad = null;
	
	private boolean isTcp = false;
	
	private boolean isUdp = false;

	/**
	 * Instantiates a new Ipv4Packet.
	 * 
	 * @param bytes
	 *            comments
	 */
	public Ipv4Packet(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			originalBytes.addAll(bytes);
			this.version = NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(0)).substring(0,4), 2, 10);
			this.headerLength = NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(0)).substring(4,8), 2, 10);
			this.diffServ = NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(1)).substring(0,6), 2, 10);
			this.explicitCongestionNotification = NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(1)).substring(6,7), 2, 10);
			this.totalLength =  NumberUtils.getDecimalByBigEndian(new String[]{originalBytes.get(2),originalBytes.get(3)});
			this.id = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(4),originalBytes.get(5)})+"---"+ NumberUtils.getDecimalByBigEndian(new String[]{originalBytes.get(4),originalBytes.get(5)});
			this.flag = NumberUtils.convert((NumberUtils.reverserStr(originalBytes.get(6))+NumberUtils.reverserStr(originalBytes.get(7))).substring(0,3), 2, 10);
			this.fragmentOffset = NumberUtils.convert((NumberUtils.reverserStr(originalBytes.get(6))+NumberUtils.reverserStr(originalBytes.get(7))).substring(3,15), 2, 10);
			this.timeToLive =  NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(8)), 2, 10);
			this.protocol =  NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(9)), 2, 10);
			String tempProtocol = this.protocol;
			HashMap<String,String> ipProtocolNumbersDecimal  = ProtocolConstans.getIpprotocolnumbersdecimal();
			if(ipProtocolNumbersDecimal.keySet().contains(this.protocol)){
				this.protocol = this.protocol +","+ipProtocolNumbersDecimal.get(this.protocol);
			}else{
				this.protocol = this.protocol +"未知协议";
			}
			this.headerChecksum =  NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(10),originalBytes.get(11)});
			this.sourceIp = NumberUtils.getIpAddressBySingleByte(new String[]{originalBytes.get(12),originalBytes.get(13),
					originalBytes.get(14),originalBytes.get(15)});
			this.destIp = NumberUtils.getIpAddressBySingleByte(new String[]{originalBytes.get(16),originalBytes.get(17),
					originalBytes.get(18),originalBytes.get(19)});
			int headerLength10 = Integer.parseInt(headerLength);
			if(headerLength10>5){
				String tempOp = "";
				for (int i = 20; i < originalBytes.size()&&i<headerLength10*4; i++) {
					tempOp = tempOp+originalBytes.get(i);
				}
				this.option =tempOp;
			}
			if(tempProtocol.equals("6")){
				this.isTcp = true;
				 this.payLoad = new TcpPacket(originalBytes.subList(headerLength10*4,originalBytes.size()));
			}else if(tempProtocol.equals("17")){
				this.isUdp = true;
				this.payLoad = new UdpPacket(originalBytes.subList(headerLength10*4,originalBytes.size()));
			}
			System.out.println();
			System.out.println(this.toString());
		}
	}

	public List<String> getOriginalBytes() {
		return originalBytes;
	}

	public void setOriginalBytes(List<String> originalBytes) {
		this.originalBytes = originalBytes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(String headerLength) {
		this.headerLength = headerLength;
	}

	public String getDiffServ() {
		return diffServ;
	}

	public void setDiffServ(String diffServ) {
		this.diffServ = diffServ;
	}

	public String getExplicitCongestionNotification() {
		return explicitCongestionNotification;
	}

	public void setExplicitCongestionNotification(
			String explicitCongestionNotification) {
		this.explicitCongestionNotification = explicitCongestionNotification;
	}

	public String getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(String totalLength) {
		this.totalLength = totalLength;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFragmentOffset() {
		return fragmentOffset;
	}

	public void setFragmentOffset(String fragmentOffset) {
		this.fragmentOffset = fragmentOffset;
	}

	public String getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHeaderChecksum() {
		return headerChecksum;
	}

	public void setHeaderChecksum(String headerChecksum) {
		this.headerChecksum = headerChecksum;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public PayLoad getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(PayLoad payLoad) {
		this.payLoad = payLoad;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void printNetBytes(){
		int count = 0;
		int number = 0;
		for (String m : this.originalBytes) {
			if(count==0){
				System.out.println("ip4行"+number+":");
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
	
	@Override
	public String toString() {
		return "Ipv4Packet [version="
				+ version + ", headerLength=" + headerLength + ", diffServ="
				+ diffServ + ", explicitCongestionNotification="
				+ explicitCongestionNotification + ", totalLength="
				+ totalLength + ", id=" + id + ", flag=" + flag
				+ ", fragmentOffset=" + fragmentOffset + ", timeToLive="
				+ timeToLive + ", protocol=" + protocol + ", headerChecksum="
				+ headerChecksum + ", sourceIp=" + sourceIp + ", destIp="
				+ destIp + ", option=" + option + ", payLoad=" + payLoad + "]";
	}

	public boolean isTcp() {
		return isTcp;
	}

	public void setTcp(boolean isTcp) {
		this.isTcp = isTcp;
	}

	public boolean isUdp() {
		return isUdp;
	}

	public void setUdp(boolean isUdp) {
		this.isUdp = isUdp;
	}
	

}
