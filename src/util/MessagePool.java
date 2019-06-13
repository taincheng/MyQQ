package util;

import java.util.HashMap;
import java.util.LinkedList;

import com.sun.org.apache.bcel.internal.generic.NEW;

import SwingPage.Chat;
import SwingPage.FaceJPanel;
import net.sf.json.JSONObject;

/**
 * 消息池 把所有的消息接收到池进行存储
 * @author Old_man
 *
 */
public class MessagePool {
	private MessagePool(){
		
	}
	private static MessagePool messagePool = new MessagePool();
	public static MessagePool getMessagePool(){
		return messagePool;
	}
	
	
	public static HashMap<String, LinkedList<Msg>> hashMap = new HashMap();
	
	//不管谁的消息都应该存储起来
	public void addMessage(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		String toUID = jsonObject.getString("toUID");
		String myUID = jsonObject.getString("myUID");
		String msg = jsonObject.getString("msg");
		String type = jsonObject.getString("type");
		String code = jsonObject.getString("code");
		
		//把接收到的消息包装到Msg对象内
		Msg msgObj = new Msg();
		msgObj.setCode(code);
		msgObj.setMsg(msg);
		msgObj.setMyUID(myUID);
		msgObj.setToUID(toUID);
		msgObj.setType(type);
		
		try {
			Chat chat = Config.chatTable.get(myUID);
			
			if(chat.isVisible()){
				chat.addMessage(msgObj);
			}else{
				throw new Exception();
			}
		} catch (Exception e) {
			
			FaceJPanel faceJPanel = Config.list.get(myUID);
			faceJPanel.addMessage(msgObj);
//			//链表集合存储Msg对象以便今后读取里面的消息
//			LinkedList<Msg> linkedList = hashMap.get(myUID);
//			
//			if(linkedList == null){
//				linkedList = new LinkedList<Msg>();
//			}
//			linkedList.add(msgObj);
//			hashMap.put(myUID,linkedList);
		}
		
	}
}
