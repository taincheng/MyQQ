package util;

import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Vector;

import SwingPage.Chat;
import SwingPage.FaceJPanel;
import SwingPage.HaoyoulistJpanel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Config {
	
	//��������ַ
	public static final String IP = "127.0.0.1";
	//��¼�˿�
	public static final int LOGIN_PORT = 4001;
	
	//ע��˿�
	public static final int REG_PORT = 4002;
	
	//UDP发送和接收以及心跳端
	public static DatagramSocket datagramSocket_client = null;
	//�û�������Ĵ�
	public static String username;
	public static String password;
	public static String haoyou_json_data = "";
	public static String geren_json_data = "";
	public static String haoyou_online = "";
	public static String haoyou_liebiaoString = "";
	
	public static HaoyoulistJpanel haoyoulistJpanel;
	//好友列表对象
	public static Hashtable<String, FaceJPanel> list = new Hashtable<String, FaceJPanel>();
	
	public static void jiexi_haoyou_json_data(String haoyou_json_data){
		JSONArray jsonArray = JSONArray.fromObject(haoyou_json_data);
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject json = (JSONObject) jsonArray.get(i);
			stringBuffer.append(json.getString("uid"));
			stringBuffer.append(",");
		}
		haoyou_liebiaoString = stringBuffer.toString();
	}
	
	//聊天窗口登记
	public static Hashtable<String, Chat> chatTable = new Hashtable<String, Chat>();
	
	//显示聊天窗口
	public static void showChatFrame(String uid,String netName,String img,
			String info,Vector<Msg> msgs){
		if(chatTable.get(uid) == null){
			Chat chat = new Chat(uid, netName, img, info,msgs);
			chatTable.put(uid, chat);
		}else{
			chatTable.get(uid).setVisible(true);
			chatTable.get(uid).setAlwaysOnTop(true);
		}
	}
	
	public static void CloseChatFrame(String uid){
		chatTable.remove(uid);
	}
}
