package QQServer;

import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
/**
 * 在线用户列表
 * @author Old_man
 *
 */
public class UserOnline {
	private UserOnline() {
	}

	private static UserOnline userOnline = new UserOnline();

	public static UserOnline getUserOnline() {
		return userOnline;
	}

	// 我们把所有的在线账户 全部登记到集合中
	/*
	 * String 是用户的编号
	 */
	private HashMap<String, UserInfo> hashMap = new HashMap<String, UserInfo>();

	
	/**
	 *上线登录用户登记
	 *
	 * @param uid
	 * @param socket
	 * @param email
	 * @param phone
	 */
	public void regOnline(String uid, Socket socket, String email, String phone) {
		UserInfo userInfo = hashMap.get(uid);
		//如果该账号在线，强行挤下去
		if (userInfo != null) {
			try {
				try {
					userInfo.getSocket().getOutputStream().write(4);
				} catch (Exception e) {
				}
				userInfo.getSocket().close();
			} catch (Exception e) {
			}
		}
		
		userInfo = new UserInfo();
		userInfo.setUid(uid);
		userInfo.setEmail(email);
		userInfo.setPhone(phone);
		userInfo.setSocket(socket);
		hashMap.put(uid,userInfo);//将信息放入集合
		
	}
	/**
	 * 更新客户端的UDP信息
	 * @param uid 用户编号
	 * @param ip UDP ip地址
	 * @param port UDP端口
	 * @throws NullPointerException 空指针异常
	 */
	public void updateOnlineUDP(String uid, String ip,int port) throws NullPointerException{
		UserInfo userInfo = hashMap.get(uid);
		userInfo.setUdpIp(ip);
		userInfo.setUdpport(port);
	
	}
	
	//判断用户是否在线
	public boolean isUserOnline(String uid){
		return hashMap.containsKey(uid);
	}

	/**
	 * 得到在线用户信息
	 * @param uid
	 * @return
	 */
	public UserInfo getOnlineUserInfo(String uid){
		return hashMap.get(uid);
	}
	/**
	 * 退出登录
	 * @param uid
	 */
	public void logout(String uid){
		hashMap.remove(uid);
	}
	/**
	 * 得到用户信息
	 * @return
	 */
	public Set<String> getUserInfos(){
		return hashMap.keySet();
	}
}
