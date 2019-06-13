package QQServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DB.PasswordException;
import DB.StateException;
import DB.UserGereninfo;
import DB.UserService;
import DB.UsernameNotFoundException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 登录服务
 * 
 * @author ����
 *
 */
public class LoginServerr implements Runnable {

	private Socket socket = null;

	public LoginServerr(Socket socket) {
		this.socket = socket;
	}

	// 让连接一直保持
	public void run() {
		String uid = null;
		// ��¼����
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();

			// �ȴ��ͻ�����Ϣ
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len);
			//////////////////////////////////////////// {��username��:��13800012461��,��password��:��123456��}
			JSONObject json = JSONObject.fromObject(json_str);// ����
			String username = json.getString("username");
			String password = json.getString("password");

			boolean type = false;
			// 判断是否为手机号码
			if (username.trim().length() == 11) {
				try {
					// 判断是否为数字，不是抛出异常
					Long.parseLong(username);
					type = true;
				} catch (NumberFormatException e) {
					out.write("{\"state\":4,\"msg\":\"账号输入异常!\"}".getBytes());
					out.flush();
					return;
				}
			} else {// email
				type = false;
			}

			try {

				if (type == true) {// 手机号登录
					uid = new UserService().LoginForPhone(username, password);
					// 登录
					UserOnline.getUserOnline().regOnline(uid, socket, null, username);
				} else {// email
					uid = new UserService().LoginForEmail(username, password);
					// 登录
					UserOnline.getUserOnline().regOnline(uid, socket, username, null);
				}

				out.write("{\"state\":0,\"msg\":\"登录成功!\"}".getBytes());
				out.flush();
				// 
				while (true) {
					bytes = new byte[2048];
					len = in.read(bytes);
					String command = new String(bytes, 0, len);
					if (command.equals("U0001")) { // 更新好友列表(昵称、UID、头像、个人说明) 

						Vector<UserHaoyouinfo> userinfos = new UserService().getHaoyoulist(uid);
						out.write(JSONArray.fromObject(userinfos).toString().getBytes());
						out.flush();

					} else if (command.equals("U0002")) {// 更新好友在线状态
						out.write(1);
						out.flush();
						
						len = in.read(bytes);// 1324564,12346546,123456456,2346546,2456456,1237489,137687
						String str = new String(bytes, 0, len);
						String[] ids = str.split(",");

						StringBuffer stringBuffer = new StringBuffer();
						for (String string : ids) {
							if (UserOnline.getUserOnline().isUserOnline(string)) {
								stringBuffer.append(string);
								stringBuffer.append(",");
							}
						}
						if (stringBuffer.length() == 0) {
							// 没有好友
							out.write("notFound".getBytes());
							out.flush();
						} else {
							// 信息传出
							out.write(stringBuffer.toString().getBytes());
							out.flush();
						}

					} else if (command.equals("U0003")) { //个人资料更新

						UserGereninfo userinfo2 = new UserService().getUserinfo(uid);
						out.write(JSONObject.fromObject(userinfo2).toString().getBytes());
						out.flush();
						
					} else if (command.equals("E0001")) {//   E0001
						

					} else if (command.equals("EXIT")) {// 下线
						UserOnline.getUserOnline().logout(uid);
						return;
					}

				}

			} catch (UsernameNotFoundException e) {
				out.write("{\"state\":2,\"msg\":\"用户名不存在\"}".getBytes());
				out.flush();
				return;
			} catch (PasswordException e) {
				out.write("{\"state\":1,\"msg\":\"密码错误!\"}".getBytes());
				out.flush();
				return;
			} catch (StateException e) {
				out.write("{\"state\":3,\"msg\":\"账号被锁定!\"}".getBytes());
				out.flush();
				return;
			} catch (SQLException e) {
				out.write("{\"state\":4,\"msg\":\"SQL出错!\"}".getBytes());
				out.flush();
				return;
			}

		} catch (Exception e) {

		} finally {
			// 账号下线
			try {
				// 账号突然下线
				UserOnline.getUserOnline().logout(uid);
				in.close();
				out.close();
				socket.close();
			} catch (Exception e2) {
			}
		}

	}

	public static void openServer() throws Exception {
		// 线程池
		ExecutorService execute = Executors.newFixedThreadPool(2000);
		// TCP端口号
		ServerSocket server = new ServerSocket(4001);
		//保持连接
		while (true) {
			Socket socket = server.accept();
			socket.setSoTimeout(10000);
			execute.execute(new LoginServerr(socket));
		}

	}

}
