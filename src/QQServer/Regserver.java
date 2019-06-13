package QQServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import DB.UserService;
import DB.UsernameException;
import QQservice.SendCode;
import net.sf.json.JSONObject;

/**
 * ע�������
 * 1.ע������
 * 2.��֤��
 * @author Old_man
 *
 */
public class Regserver implements Runnable{
	private InputStream input;
	private OutputStream output;
	
	private Socket socket = null;
	
	public Regserver(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try {
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
		
			byte[] bytes = new byte[1024];
			int len = input.read(bytes);
			String string = new String(bytes,0,len);
			JSONObject json = JSONObject.fromObject(string);
			String type = json.getString("type");
			if(type.equals("code")){//验证码
				
				String username = json.getString("username");
				
				Random random = new Random();
				StringBuffer code = new StringBuffer();
				for(int i = 0; i< 6; i++){
					code.append(random.nextInt(10));
				}
				
				if(username.trim().length() == 11){
//					try{//�ֻ�������֤
//						Long.parseLong(username);
//						hashMap_code.put(username, code.toString());
//						SendCode.send(username, code.toString());
//						output.write("{\"state\":1,\"msg\":\"��֤�뷢�ͳɹ���\"}".getBytes());
//						output.flush();
//					}catch (Exception e) {
//						output.write("{\"state\":1,\"msg\":\"��֤�뷢��ʧ�ܣ�\"}".getBytes());
//						output.flush();
//					}
					output.write("{\"state\":1,\"msg\":\"暂不正常手机注册，请使用email\"}".getBytes());
					output.flush();
				}else{//������֤�뷢��
					if(username.trim().indexOf("@") >= 0){
						hashMap_code.put(username, code.toString());
						SendCode.sendEmail(username, code.toString());
						output.write("{\"state\":0,\"msg\":\"验证码发送成功\"}".getBytes());
						output.flush();
					}else {
						output.write("{\"state\":1,\"msg\":\"验证码发送失败\"}".getBytes());
						output.flush();
					}
					
				}
				
			}else if(type.equals("reg")){//注册
				String username = json.getString("username");
				String password = json.getString("password");
				String code = json.getString("code");
				String code1 = hashMap_code.get(username);
				if(code1 != null){
					hashMap_code.remove(username);//将已经发送的code删除
				}
				if(code1.equals(code)){//询问验证码是否正确
					
					try {
						new UserService().regUser(username, password);
					} catch (UsernameException e) {
						output.write("{\"state\":3,\"msg\":\"用户名存在\"}".getBytes());
						output.flush();
						return;
					} catch (SQLException e) {
						output.write("{\"state\":1,\"msg\":\"sql错误\"}".getBytes());
						output.flush();
						return;
					}
					output.write("{\"state\":0,\"msg\":\"注册成功，可以去登录了\"}".getBytes());
					output.flush();
				}else {
					output.write("{\"state\":2,\"msg\":\"验证码错误\"}".getBytes());
					output.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				input.close();
				output.close();
			} catch (Exception e2) {
			}
		}
		
	}
	
	private static HashMap<String, String> hashMap_code = new HashMap<String, String>();
	
	public static void openServer() throws IOException{
		//开启线程池
		ExecutorService service = Executors.newFixedThreadPool(1000);
		//开启服务器端口
		ServerSocket server = new ServerSocket(4002);
		while(true){
			Socket socket = server.accept();
			service.execute(new Regserver(socket));
		}
	}
}
