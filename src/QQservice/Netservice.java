package QQservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;
import net.sf.json.JSONObject;
import util.Config;

/**
 * ͨѶ���� ���������ʱ�䱣����ϵ 1.���º�������״̬��Ϣ��5�������� 2.��¼��֤ 3.�˳��˻�
 * 
 * @author Old_man
 *
 */
public class Netservice implements Runnable {
	

	private Netservice() {

	}

	private static Netservice netservice = new Netservice();

	public static Netservice getNetservice() {
		return netservice;
	}

	// ׼���ͷ��������ֳ�ʱ����ϵ
	
	public void run() {
		try {
			byte[] bytes = new byte[1024*10];
			int len = 0;
			//好友在线实时更新
			while (run) {
				out.write("U0002".getBytes());
				out.flush();
				input.read();
				
				out.write(Config.haoyou_liebiaoString.getBytes());
				out.flush();
				
				len = input.read(bytes);
				String online = new String(bytes,0,len);
				
				//System.out.println("在线账户:" + online);
				try {
					if(!Config.haoyou_online.equals(online)){
						Config.haoyou_online = online;
						Config.haoyoulistJpanel.haoyouOnline();
					}
				} catch (Exception e) {
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		} catch (Exception e) {
			run = false;
		}

	}

	private Socket socket = null;
	private InputStream input = null;
	private OutputStream out = null;
	private Thread thread = null;
	private boolean run = false;

	public JSONObject login() throws UnknownHostException, IOException {
		
		socket = new Socket(Config.IP, Config.LOGIN_PORT);
		//System.out.println("2222");
		input = socket.getInputStream();
		out = socket.getOutputStream();
		String json_str = "{\"username\":\"" + Config.username + "\",\"password\":\"" + Config.password + "\"}";

		// ��ʼ�������������Ϣ
		out.write(json_str.getBytes());
		out.flush();

		// �ȴ���������ִ��Ϣ
		byte[] bytes = new byte[1024];
		int len = input.read(bytes);

		json_str = new String(bytes, 0, len);
		JSONObject json = JSONObject.fromObject(json_str);

		// �����0���ǵ�¼�ɹ�
		if (json.getInt("state") == 0) {
			// ��ʼ�������������ӷ���
			if (thread != null) {
				// ѯ���߳��Ƿ����
				if (thread.getState() == Thread.State.RUNNABLE) {
					run = false;
					try {
						thread.stop();

					} catch (Exception e) {
					}
				}
			}
			//好友信息获得
			out.write("U0001".getBytes());
			out.flush();
			bytes = new byte[1024*10];
			len = input.read(bytes);
			String jsonstr = new String(bytes,0,len);
			Config.haoyou_json_data = jsonstr;
			//System.out.println("好友信息" + Config.haoyou_json_data);
						
			//好友列表解析
			Config.jiexi_haoyou_json_data(Config.haoyou_json_data);
			
			//个人资料的获得
			out.write("U0003".getBytes());
			out.flush();
			len = input.read(bytes);
			Config.geren_json_data = new String(bytes,0,len);
			//System.out.println("个人资料" + Config.geren_json_data);
			
			//启动UDP服务器
			Config.datagramSocket_client = new DatagramSocket();
			//启动心跳包服务
			new MessageRegService(Config.datagramSocket_client);
			//启动消息服务
			new MessageService(Config.datagramSocket_client);
			
			
			// 重新开启线程与服务器连接
			run = true;
			thread = new Thread(this);
			thread.start();

		}
		return json;
	}
}
