package QQServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;
/**
 * UDP信息中转服务器
 * @author Old_man
 *
 */
public class UDPMessageServer implements Runnable{
	private DatagramPacket packet = null;
	public UDPMessageServer(DatagramPacket packet){
		this.packet = packet;
	}
	
	//业务处理
	@Override
	public void run() {
		try {
			
			String jsonStr = new String(packet.getData(),0,packet.getLength());
			JSONObject json = JSONObject.fromObject(jsonStr);
			
			
			if(json.getString("type").equals("reg")){
				//处理心跳包
				String MyUID = json.getString("myUID");
				//更新最新的IP和端口号
				UserOnline.getUserOnline().updateOnlineUDP(MyUID,
						packet.getAddress().getHostAddress(), packet.getPort());
				
				//System.out.println("有注册消息发来" + jsonStr);
				
			}else if (json.getString("type").equals("msg") || json.getString("type").equals("qr")) {
				//处理信息转发
				String MyUID = json.getString("myUID");
				String toUID = json.getString("toUID");
				//System.out.println(toUID);
				//更新最新的IP和端口号
				UserOnline.getUserOnline().updateOnlineUDP(MyUID,
						packet.getAddress().getHostAddress(), packet.getPort());
				
				//获得要接受你信息的人
				UserInfo toUserInfo = UserOnline.getUserOnline().getOnlineUserInfo(toUID);
				
				//准备转发到客户端的数据包
				DatagramPacket datagramPacket = new DatagramPacket(
						packet.getData(), packet.getLength(),
						InetAddress.getByName(toUserInfo.getUdpIp()),
						toUserInfo.getUdpport());
				//发出数据
				datagramSocket.send(datagramPacket);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static DatagramSocket datagramSocket = null;
	
	//启动服务器
	public static void openServer() throws Exception{
		datagramSocket = new DatagramSocket(4003);
		//制作线程池
		ExecutorService execute = Executors.newFixedThreadPool(1000);
		
		while(true){
			try {
				//等待客户端的数据
				byte[] buf = new byte[1024 * 10];
				DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
				datagramSocket.receive(datagramPacket);
				//数据一旦到手，马上抓出一个线程处理
				execute.execute(new UDPMessageServer(datagramPacket));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
