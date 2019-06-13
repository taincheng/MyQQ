package QQServer;

import java.io.OutputStream;
import java.net.Socket;

public class UserInfo {
	private String uid;
	private String email;
	private String phone;
	private Socket socket;
	private String udpIp;
	private int udpport;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getUdpIp() {
		return udpIp;
	}
	public void setUdpIp(String udpIp) {
		this.udpIp = udpIp;
	}
	public int getUdpport() {
		return udpport;
	}
	public void setUdpport(int udpport) {
		this.udpport = udpport;
	}
	
}
