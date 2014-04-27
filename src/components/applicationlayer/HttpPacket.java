package components.applicationlayer;

import java.util.ArrayList;
import java.util.List;

import common.NumberUtils;
import components.PayLoad;

public class HttpPacket extends PayLoad {
	/** originalBytes. */
	private List<String> originalBytes = new ArrayList<String>();

	private Integer bytesLength = 0;

	private Integer lineNumber = 0;

	private List<String> httpPacketContent = new ArrayList<String>();

	public HttpPacket(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			this.originalBytes.addAll(bytes);
			this.bytesLength = this.originalBytes.size();
			System.out.println("Http报文长度:" + bytes.size());
			byte[] buffer = new byte[originalBytes.size()];
			for (int i = 0; i < originalBytes.size(); i++) {
				buffer[i] = NumberUtils.bitStringToByte(NumberUtils
						.reverserStr(originalBytes.get(i)));
			}
			httpPacketContent.addAll(NumberUtils.readBytes(buffer));
			this.lineNumber = httpPacketContent.size();
			// System.out.println(this.toString());
			this.print();
		}
	}

	public void print() {
		System.out.println("HttpPacket begin--");
		System.out.println("HttpPacket [bytesLength=" + bytesLength
				+ ", lineNumber=" + lineNumber + "]");
		for (int i = 0; i < httpPacketContent.size(); i++) {
			System.out.println(httpPacketContent.get(i));
		}
		System.out.println("HttpPacket end--");
	}

}
