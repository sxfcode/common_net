package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import components.internetlayer.ip.v4.Ipv4Packet;
import components.linklayer.LanFramePacket;


/**
 * 
 * 有意思的是，IEEE所制定的以太网标准使得DIXv2帧和IEEE帧在一个局域网中不能共存。
 * 因此，IEEE允许从现存的DIXv2网卡和网络设备到IEEE的标准设备的迁移。
 * 为了让设备可以识别使用的是哪种类型的帧，IEEE没有分配1536以下（十六进制数为600）的数为协议类型代码。
 * 数据字段的最大值为1500字节。所以一台设备可以很容易从源地址之后的2个字节来判断是哪种类型的帧，如果值为1536（十进制）或更高则为类型字段，意味着是dixv2帧。
 * 如果从源地址之后的2个字节小于1536，则可确定是长度字段，为IEEE802.3帧。
 * ======================
 *  Ethernet II类型以太网帧的最小长度为64字节（6＋6＋2＋46＋4），最大长度为1518字节（6＋6＋2＋1500＋4）。其中前12字节分别标识出发送数据帧的源节点MAC地址和接收数据帧的目标节点MAC地址。（注：ISL封装后可达1548字节，802.1Q封装后可达1522字节）
        接下来的2个字节标识出以太网帧所携带的上层数据类型，如16进制数0x0800代表IP协议数据，16进制数0x809B代表AppleTalk协议数据，16进制数0x8138代表Novell类型协议数据等。
        在不定长的数据字段后是4个字节的帧校验序列（Frame. Check Sequence，FCS），采用32位CRC循环冗余校验对从"目标MAC地址"字段到"数据"字段的数据进行校验。
 * =======================

所以，以太网帧的长度范围是64-1518。
 * 
 * 
 * The Class HellowWorld.
 * 链路层在传输数据时低位在前，高位在后，低字节在前，高字节在后。
 * @date 2014-1-26 11:27:13
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class HellowWorld {
	
	/** 这是最原始的一种格式，是由Xerox PARC提出的3MbpsCSMA/CD以太网标准的封装格式，后来在1980年由DEC，Intel和Xerox标准化形成Ethernet V1标准.. */
	public static final String FRAME_HEAD_V1 = "Ethernet V1"; 
	
	/** 由DEC，Intel和Xerox在1982年公布其标准，主要更改了Ethernet V1的电气特性和物理接口，在帧格式上并无变化*/
	public static final String FRAME_HEAD_V2 = "Ethernet V2(ARPA)"; // 源mac后2字节为类型,  总长度(最小6+6+2+46+4 = 64，最大6+6+2+1500+4 = 1518)
	// 常见协议类型
	// 0800       IP
	// 0806       ARP
	// 8137       Novell IPX
	// 809b       Apple Talk
	// 如果协议类型字段取值为0000-05dc(十进制的0-1500)，则该帧就不是Ethernet V2(ARPA)类型
	
	
	/** 1983年Novell发布其划时代的Netware/86网络套件时采用的私有以太网帧格式,Novell的RAW 802.3格式跟正式的IEEE 802.3标准互不兼容.. */
	public static final String FRAME_HEAD_V3 = "RAW 802.3";//源mac后2字节为总长度（46-1500），小于等于1500,总长度(最小6+6+2+2+44+4 = 64，最大6+6+2+2+1498+4 = 1518)
	// Length字段(2bytes,取值为0000-05dc，即十进制的0-1500)，因为RAW 802.3帧只支持IPX/SPX一种协议；
	
	/** 这是IEEE 正式的802.3标准，它由Ethernet V2发展而来。它将Ethernet V2帧头的协议类型字段替换为帧长度字段(取值为0000-05dc;十进制的1500)；
	 * 并加入802.2 LLC头用以标志上层协议，LLC头中包含DSAP，SSAP以及Crontrol字段.. */
	public static final String FRAME_HEAD_V4 = "LLC";// 源mac后2字节为总长度
	// 常见sap值
	//     0         Null LSAP         [IEEE]
	//		4         SNA Path Control         [IEEE]
	//		6         DOD IP         [79,JBP]
	//		AA         SNAP         [IEEE]
	//		FE         Global DSAP         [IEEE]
	// SAP值用以标志上层应用，但是每个SAP字段只有8bits长，而且其中仅保留了6比特用于标识上层协议，因此所能标识的协议数有限(不超过32种);
	// IEEE拒绝为某些重要的协议比如ARP协议定义SAP值(奇怪的是同时他们却定义了IP的SAP值)；因此802.3/802.2 LLC的使用有很大局限性；
	
	/** 这是IEEE为保证在802.2 LLC上支持更多的上层协议同时更好的支持IP协议而发布的标准，
	 * 与802.3/802.2 LLC一样802.3/802.2 SNAP也带有LLC头，但是扩展了LLC属性，新添加了一个2Bytes的协议类型域（同时将SAP的值置为AA），
	 * 从而使其可以标识更多的上层协议类型；另外添加了一个3Bytes的OUI字段用于代表不同的组织，RFC 1042定义了IP报文在802.2网络中的封装方法和ARP协议在802.2 SANP中的实现.. */
	public static final String FRAME_HEAD_V5 = "SNAP";
	
	//===============
	//今天的实际环境中大多数TCP/IP设备都使用Ethernet V2格式的帧。这是因为第一种大规模使用的TCP/IP系统(4.2/3 BSD UNIX)的出现时间介于RFC 894和RFC 1042之间，
	//它为了避免不能和别的主机互操作的风险而采用了RFC 894的实现；也由于大家都抱着这种想法，所以802.3标准并没有如预期那样得到普及；
	//===================
	
	private int flagNumber = 1600;
	
	private static String input = "";
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// step 1:获取网络适配器
       List<PcapIf> devlist = new ArrayList<PcapIf>();
       StringBuilder sb = new StringBuilder();
       int result = Pcap.findAllDevs(devlist, sb);
		if(result==Pcap.NOT_OK||devlist.isEmpty()){
			System.out.println("获取网络适配器失败:"+sb.toString());
			return;
		}
		System.out.println(sb.toString());
		System.out.println("获取网络适配器成功");
		int i =0;
		for (PcapIf pcapIf : devlist) {
			System.out.println("编号"+i+" ,网络适配器名称<"+pcapIf.getName()+">,网路适配器描述:"+pcapIf.getDescription());
		}
		PcapIf defaultDev = devlist.get(0);
		System.out.println("选择适配器0,网络适配器名称<"+defaultDev.getName()+">,网络适配器描述:"+defaultDev.getDescription());
		// step 2:创建解析器
		// 适配器工作模式
		int devMode = Pcap.MODE_PROMISCUOUS;
		int timeout = 10*1000;
		sb.setLength(0);
	    Pcap pcap = Pcap.openLive(defaultDev.getName(), 0, devMode, timeout, sb);
	    if(pcap==null){
	    	System.out.println(sb.toString());
	    	return;
	    }
	    PcapPacketHandler<String> packetHandler = new PcapPacketHandler<String>() {
			public void nextPacket(PcapPacket packet, String user) {
				PcapHeader header = packet.getCaptureHeader();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//				System.out.printf("接收数据包 at %s  cap长度 %-4d 长度%-4d  %s  \n",sdf.format(new Date(header.timestampInMillis())),header.caplen(),header.wirelen(),user);
				byte[] buffer = new byte[header.caplen()];
				packet.getByteArray(0, buffer);
				System.out.println("start ===================================");
				try {
					LanFramePacket fp = new LanFramePacket(buffer);
					if(fp.getHeader().isIpv4()){
						Ipv4Packet ip4 = (Ipv4Packet)fp.getPayLoad();
						if(ip4.isTcp()){
							System.out.println("");
						}
						//fp.printNetBytes();
					}
				} catch (Exception e) {
					System.out.println("发现异常数据包");
					e.printStackTrace();
				}
				
//				System.out.println(fp.toString());
				System.out.println("end ===================================");
			}
		};
		
		new Thread(){
			private Scanner san = new Scanner(System.in);
			public void run(){
				System.out.println("开始扫描输入");
				input = san.next();
				System.out.println("input :"+input);
			}
		}.start();
		// step 3:执行解析
		while (!input.equals("quit")) {
			System.out.println("开始循环!");
			pcap.loop(2,packetHandler,"test");
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			
			System.out.println("结束循环!");
		}
		// step 4:关闭连接
		pcap.close();
	}
	
	/**
	 * Checks if is ip4.
	 *
	 * @param head comments
	 * @return true, if is ip4
	 */
	public static boolean isIp4(String head){
		if(head.startsWith("0100")){
			return true;
		}else{
			return false;
		}
	}
}