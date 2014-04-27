package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;


/**
 * The Class HellowWorld.
 *
 * @date 2014-1-26 11:27:13
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class HellowWorld {
	
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
			i++;
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
				System.out.printf("接收数据包 at %s  cap长度 %-4d 长度%-4d  %s  \n",sdf.format(new Date(header.timestampInMillis())),header.caplen(),header.wirelen(),user);
				System.out.println("===================================1");
				System.out.println(packet.toString());
				//packet.getByteArray(arg0, arg1)
				byte[] buffer = new byte[header.caplen()];
				packet.getByteArray(0, buffer);
				System.out.println("总字节数1:"+header.caplen());
				System.out.println("总字节数2:"+buffer.length);
				printBytes(buffer);
			}
		};
		// step 3:执行解析
		pcap.loop(1,packetHandler,"test");
		// step 4:关闭连接
		pcap.close();
	}
	public static void  printBytes(byte[] buffer ){
		Map<Integer,String> bytesStr = new HashMap<Integer,String>();
		int number = 0;
		int count = 0;
		String temp = "";
		for (byte b : buffer) {
			temp = temp +" " + NumberUtils.leftAddZero(Integer.toBinaryString(b), 8);
			count++;
			if(count ==4){
				bytesStr.put(number, temp);
				temp = "";
				number++;
				count =0;
			}
		}
		if(!temp.equals("")&&temp.length()>0){
			bytesStr.put(number, temp);
		}
		Iterator<Map.Entry<Integer,String>> it = bytesStr.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer,String> en = it.next();
			System.out.println("line "+en.getKey()+" :"+en.getValue());
		}
	}
	
	

}