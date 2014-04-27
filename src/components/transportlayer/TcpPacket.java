package components.transportlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import common.NumberUtils;
import components.PayLoad;
import components.ProtocolConstans;
import components.applicationlayer.HttpPacket;

/**
 * The Class TCPPacket.
 *
 * @date 2014-3-1 10:47:20
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class TcpPacket extends PayLoad {

	/** originalBytes. */
	private List<String> originalBytes = new ArrayList<String>();
    
	/** 源端口，2字节. */
	private String sourcePort = "";
	
	/** 目标端口，2字节. */
	private String destPort = "";
	
	/** 序列号，4字节. */
	private String seqNumber = "";
	
	/** 确认号，4字节. */
	private String ackNumber = "";
	
	/** 头部长度,,4bit. */
	private String headerLength = "";
	
	/** 预留 . */
	private String reserved = "";
	
	/** 标志位，9bit. */
	private String flags = "";
	
	/** 窗口大小. */
	private String windowSize = "";
	
	/** 校验和. */
	private String checkSum = "";
	
	/** 紧急指针. */
	private String urgentPointer = "";
	/** 可选项. */
	private String option = "";
	
	@SuppressWarnings("unused")
	private String protocolName = "Not CommonProtocol ";
	
	/** payload. */
	private PayLoad payload = null;

	/**
	 * Instantiates a new TCPPacket.
	 *
	 * @param bytes comments
	 */
	public TcpPacket(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			this.originalBytes.addAll(bytes);
			if(originalBytes.size()<20){
				System.out.println("发现异常数据包");
				return;
			}
			this.sourcePort =  NumberUtils.getDecimalByBigEndian(new String[]{originalBytes.get(0),originalBytes.get(1)});
			this.destPort =  NumberUtils.getDecimalByBigEndian(new String[]{originalBytes.get(2),originalBytes.get(3)});
			this.seqNumber =   NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(4),originalBytes.get(5),originalBytes.get(6),originalBytes.get(7)});
			this.ackNumber =   NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(8),originalBytes.get(9),originalBytes.get(10),originalBytes.get(11)});
			this.headerLength = NumberUtils.convert(NumberUtils.reverserStr(originalBytes.get(12)).substring(0,4), 2, 10);
			this.flags = NumberUtils.reverserStr(originalBytes.get(12)).substring(7,8) +originalBytes.get(13);
			this.windowSize = NumberUtils.getDecimalByBigEndian(new String[]{originalBytes.get(14),originalBytes.get(15)});
			this.checkSum = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(16),originalBytes.get(17)});
			this.urgentPointer = NumberUtils.getHexByBigEndian(new String[]{originalBytes.get(18),originalBytes.get(19)});
			HashMap<String,String> tcpProtocolNumbersDecimal = ProtocolConstans.getTcpprotocolnumbersdecimal();
			Set<String> ports = tcpProtocolNumbersDecimal.keySet();
			if(ports.contains(this.sourcePort)){
				System.out.println("源端口协议:" +tcpProtocolNumbersDecimal.get(this.sourcePort));
				this.protocolName = tcpProtocolNumbersDecimal.get(this.sourcePort);
			}
			if(ports.contains(this.destPort)){
				System.out.println("目标端口协议:" +tcpProtocolNumbersDecimal.get(this.destPort));
				this.protocolName = tcpProtocolNumbersDecimal.get(this.sourcePort);
			}
			// 头部长度
			int headerLength10 = Integer.parseInt(headerLength);
			if(headerLength10>5){
				String tempOp = "";
				for (int i = 20; i < originalBytes.size()&&i<headerLength10*4; i++) {
					tempOp = tempOp+originalBytes.get(i);
				}
				this.option =tempOp;
			}
			System.out.println("tcp总字节数:"+originalBytes.size());
			System.out.println("tcp头部长度:"+headerLength10);
			if(this.sourcePort.equals("80")||this.destPort.equals("80")){
				this.payload = new HttpPacket(originalBytes.subList(headerLength10*4,originalBytes.size()));
			}
			//this.printNetBytes();
			System.out.println();
			System.out.println(this.toString());
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
	 * @param originalBytes comments
	 */
	public void setOriginalBytes(List<String> originalBytes) {
		this.originalBytes = originalBytes;
	}

	/**
	 * Gets sourcePort.
	 *
	 * @return sourcePort
	 */
	public String getSourcePort() {
		return sourcePort;
	}

	/**
	 * Sets sourcePort.
	 *
	 * @param sourcePort comments
	 */
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	/**
	 * Gets destPort.
	 *
	 * @return destPort
	 */
	public String getDestPort() {
		return destPort;
	}

	/**
	 * Sets destPort.
	 *
	 * @param destPort comments
	 */
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	/**
	 * Gets seqNumber.
	 *
	 * @return seqNumber
	 */
	public String getSeqNumber() {
		return seqNumber;
	}

	/**
	 * Sets seqNumber.
	 *
	 * @param seqNumber comments
	 */
	public void setSeqNumber(String seqNumber) {
		this.seqNumber = seqNumber;
	}

	/**
	 * Gets ackNumber.
	 *
	 * @return ackNumber
	 */
	public String getAckNumber() {
		return ackNumber;
	}

	/**
	 * Sets ackNumber.
	 *
	 * @param ackNumber comments
	 */
	public void setAckNumber(String ackNumber) {
		this.ackNumber = ackNumber;
	}


	/**
	 * Gets reserved.
	 *
	 * @return reserved
	 */
	public String getReserved() {
		return reserved;
	}

	/**
	 * Sets reserved.
	 *
	 * @param reserved comments
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	/**
	 * Gets flags.
	 *
	 * @return flags
	 */
	public String getFlags() {
		return flags;
	}

	/**
	 * Sets flags.
	 *
	 * @param flags comments
	 */
	public void setFlags(String flags) {
		this.flags = flags;
	}

	/**
	 * Gets windowSize.
	 *
	 * @return windowSize
	 */
	public String getWindowSize() {
		return windowSize;
	}

	/**
	 * Sets windowSize.
	 *
	 * @param windowSize comments
	 */
	public void setWindowSize(String windowSize) {
		this.windowSize = windowSize;
	}

	/**
	 * Gets checkSum.
	 *
	 * @return checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}

	/**
	 * Sets checkSum.
	 *
	 * @param checkSum comments
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * Gets urgentPointer.
	 *
	 * @return urgentPointer
	 */
	public String getUrgentPointer() {
		return urgentPointer;
	}

	/**
	 * Sets urgentPointer.
	 *
	 * @param urgentPointer comments
	 */
	public void setUrgentPointer(String urgentPointer) {
		this.urgentPointer = urgentPointer;
	}

	/**
	 * Gets payload.
	 *
	 * @return payload
	 */
	public PayLoad getPayload() {
		return payload;
	}

	/**
	 * Sets payload.
	 *
	 * @param payload comments
	 */
	public void setPayload(PayLoad payload) {
		this.payload = payload;
	}
	
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(String headerLength) {
		this.headerLength = headerLength;
	}

	
	@Override
	public String toString() {
		return "TcpPacket [sourcePort="
				+ sourcePort + ", destPort=" + destPort + ", seqNumber="
				+ seqNumber + ", ackNumber=" + ackNumber + ", headerLength="
				+ headerLength + ", reserved=" + reserved + ", flags=" + flags
				+ ", windowSize=" + windowSize + ", checkSum=" + checkSum
				+ ", urgentPointer=" + urgentPointer + ", option=" + option
				+ "]";
	}

	/**
	 * printNetBytes.
	 */
	public void printNetBytes() {
		int count = 0;
		int number = 0;
		System.out.println("tcppacket--------");
		for (String m : this.originalBytes) {
			if (count == 0) {
				System.out.println("tcp行" + number + ":");
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
