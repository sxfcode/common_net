package components.transportlayer;

import java.util.ArrayList;
import java.util.List;

import components.PayLoad;

/**
 * The Class UdpPacket.
 * 
 * @date 2014-3-1 12:20:34
 * @author 宿晓斐
 * @version 1.0
 * @since jdk 1.6,common_net_x64 1.0
 */
public class UdpPacket extends PayLoad {

	/** originalBytes. */
	private List<String> originalBytes = new ArrayList<String>();

	/**
	 * Instantiates a new UdpPacket.
	 * 
	 * @param bytes
	 *            comments
	 */
	public UdpPacket(List<String> bytes) {
		if (bytes != null && bytes.size() > 0) {
			originalBytes.addAll(bytes);
			//printNetBytes();
		}
	}

	public void printNetBytes() {
		int count = 0;
		int number = 0;
		for (String m : this.originalBytes) {
			if (count == 0) {
				System.out.println("upd行" + number + ":");
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
