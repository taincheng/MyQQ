package QQservice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;
import util.Config;
import util.MessagePool;
/**
 * 接受服务器的中转消息
 * @author Old_man
 *
 */
public class MessageService extends Thread {

	private DatagramSocket client = null;
	public void run() {
		while (true) {
			try {
				byte[] bytes = new byte[1024 * 32];
				DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
				client.receive(datagramPacket);
				
				//将消息存储到池
				MessagePool.getMessagePool().addMessage(new String(
						datagramPacket.getData(),
						0,datagramPacket.getData().length));
				
				
				
			} catch (Exception e) {
			}
		}
	}
	
	public MessageService(DatagramSocket client){
		this.client = client;
		this.start();
	}
}
