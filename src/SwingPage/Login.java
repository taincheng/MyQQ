package SwingPage;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.LineBorder;

import org.apache.commons.collections.map.StaticBucketMap;

import QQservice.Netservice;
import net.sf.json.JSONObject;
import util.Config;
import util.WindowXY;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private JTextField username_zc;
	private JTextField code;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// 设置皮肤
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) { 
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("\u5373\u65F6\u804A\u5929\u5DE5\u5177");
		setResizable(false);
		setBackground(Color.ORANGE);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 300);// 300,660

		setLocation(WindowXY.getXY(Login.this.getSize()));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		label.setBounds(46, 51, 129, 46);
		label.setFont(new Font("楷体", Font.BOLD, 27));
		label.setToolTipText("");
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u5BC6  \u7801\uFF1A");
		label_1.setBounds(46, 110, 129, 46);
		label_1.setToolTipText("");
		label_1.setFont(new Font("楷体", Font.BOLD, 27));
		contentPane.add(label_1);

		username = new JTextField();
		username.setBounds(162, 51, 168, 39);
		contentPane.add(username);
		username.setColumns(10);

		password = new JPasswordField();
		password.setBounds(162, 110, 168, 40);
		contentPane.add(password);

		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Login.this.getHeight() <= 660 && Login.this.getHeight() > 300) {
					Login.this.setSize(449, 300);
				} else {
					Login.this.setSize(449, 660);
				}
				setLocation(WindowXY.getXY(Login.this.getSize()));
			}
		});
		button.setBounds(46, 192, 113, 45);
		button.setFont(new Font("微软雅黑 Light", Font.BOLD, 21));
		contentPane.add(button);

		JButton loginbutton = new JButton("\u767B\u5F55");
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username_str = username.getText().trim();
				String password_str = password.getText().trim();
				if(username_str.trim().equals("") || password_str.trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "用户名和密码都必须填");
				}else {
					Config.username = username_str;
					Config.password = password_str;
					try {
						JSONObject json = Netservice.getNetservice().login();
						
						if(json.getInt("state") == 0){
							//登录成功后直接显示好友列表
							new Haoyou().setVisible(true);
							Login.this.setVisible(false);
							Login.this.dispose();
							
						}else{
							JOptionPane.showMessageDialog(Login.this, json.getString("msg"));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(Login.this, "网络连接失败");
					}
				}
			}
		});
		loginbutton.setBounds(267, 192, 113, 45);
		loginbutton.setFont(new Font("微软雅黑 Light", Font.BOLD, 21));
		contentPane.add(loginbutton);

		JPanel panel = new JPanel();
		panel.setToolTipText("\u7528\u6237\u6CE8\u518C");
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(14, 285, 403, 310);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label_2 = new JLabel("\u624B\u673A\u53F7\uFF1A");
		label_2.setFont(new Font("楷体", Font.PLAIN, 18));
		label_2.setBounds(39, 20, 72, 18);
		panel.add(label_2);

		JLabel label_3 = new JLabel("\u90AE  \u7BB1\uFF1A");
		label_3.setFont(new Font("楷体", Font.PLAIN, 18));
		label_3.setBounds(39, 40, 72, 18);
		panel.add(label_3);

		username_zc = new JTextField();
		username_zc.setBounds(125, 20, 161, 38);
		panel.add(username_zc);
		username_zc.setColumns(10);

		JButton button_2 = new JButton("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(username_zc.getText().trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "注册用户名不能为空");
					return;
				}
				try {
					Socket socket = new Socket(Config.IP,Config.REG_PORT);
					
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream();
					
					output.write(("{\"type\":\"code\",\"username\":\""+username_zc.getText() +"\"}").getBytes());
					output.flush();
					
					byte[] bytes = new byte[1024];
					int len = input.read(bytes);
					String string = new String(bytes,0,len);
					System.out.println(string);
					JOptionPane.showMessageDialog(Login.this, JSONObject.fromObject(string).getString("msg"));
					input.close();
					output.close();
					socket.close();
		
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button_2.setFont(new Font("楷体", Font.PLAIN, 10));
		button_2.setBounds(294, 18, 95, 40);
		panel.add(button_2);

		JLabel label_4 = new JLabel("\u9A8C\u8BC1\u7801\uFF1A");
		label_4.setFont(new Font("楷体", Font.PLAIN, 21));
		label_4.setBounds(39, 97, 84, 31);
		panel.add(label_4);

		code = new JTextField();
		code.setBounds(125, 89, 161, 38);
		panel.add(code);
		code.setColumns(10);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Leelawadee UI", Font.BOLD, 16));
		lblPassword.setBounds(39, 166, 72, 31);
		panel.add(lblPassword);

		JLabel lblAgain = new JLabel("   Again");
		lblAgain.setFont(new Font("Leelawadee UI", Font.BOLD, 16));
		lblAgain.setBounds(39, 210, 72, 31);
		panel.add(lblAgain);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(125, 159, 161, 38);
		panel.add(passwordField_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(125, 203, 161, 38);
		panel.add(passwordField_2);

		JButton button_3 = new JButton("\u53D6\u6D88");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username_zc.setText("");
				code.setText("");
				passwordField_1.setText("");
				passwordField_2.setText("");
				Login.this.setSize(449, 300);
			}
		});
		button_3.setFont(new Font("楷体", Font.BOLD, 17));
		button_3.setBounds(28, 254, 95, 43);
		panel.add(button_3);

		JButton button_4 = new JButton("\u63D0\u4EA4");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(username_zc.getText().trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "注册用户名不能为空");
					return;
				}
				if(code.getText().trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "验证码不能为空");
					return;
				}
				if(passwordField_1.getText().trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "密码不能为空不能为空");
					return;
				}
				if(passwordField_2.getText().trim().equals("")){
					JOptionPane.showMessageDialog(Login.this, "请重复输入一遍密码");
					return;
				}
				if(!passwordField_1.getText().trim().equals(passwordField_2.getText().trim())){
					JOptionPane.showMessageDialog(Login.this, "两次密码输入不一致");
					return;
				}
				try {
					Socket socket = new Socket(Config.IP,Config.REG_PORT);
					
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream();
					
					output.write(("{\"type\":\"reg\",\"username\":\""+username_zc.getText() +"\""
							+ ",\"password\":\""+passwordField_1.getText() +"\""
									+ ",\"code\":\""+code.getText() +"\"}").getBytes());
					output.flush();
					
					byte[] bytes = new byte[1024];
					int len = input.read(bytes);
					String string = new String(bytes,0,len);
					System.out.println(string);
					JOptionPane.showMessageDialog(Login.this, JSONObject.fromObject(string).getString("msg"));
					input.close();
					output.close();
					socket.close();
					username_zc.setText("");
					code.setText("");
					passwordField_1.setText("");
					passwordField_2.setText("");
					Login.this.setSize(449, 300);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_4.setFont(new Font("楷体", Font.BOLD, 17));
		button_4.setBounds(247, 254, 95, 43);
		panel.add(button_4);

		JLabel label_5 = new JLabel("\u7528\u6237\u6CE8\u518C");
		label_5.setBackground(Color.GREEN);
		label_5.setBounds(14, 264, 72, 18);
		contentPane.add(label_5);
	}
}
