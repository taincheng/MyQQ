package QQServer;

public class Start {

	public static void main(String[] args) {
		new Thread() {

			public void run() {
				try {
					System.out.println("登录服务器启动");
					LoginServerr.openServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {

			public void run() {
				try {
					System.out.println("注册服务器启动");
					Regserver.openServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {

			public void run() {
				try {
					System.out.println("信息中转服务器启动");
					UDPMessageServer.openServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
