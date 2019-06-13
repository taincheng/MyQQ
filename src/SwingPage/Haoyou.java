package SwingPage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import util.Config;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import net.sf.json.JSONObject;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Haoyou extends JFrame{

 	JLabel myImage = new JLabel();
	JLabel myNetname = new JLabel();
	JLabel myInfo = new JLabel();
 	public void gengxin(){
 		//{"back":"","dd":0,"email":"12321@qw.com","img":"5","info":"不知妻美","mm":0,"name":"","netname":"刘强东","phonenumber":"11111111112","sex":"","uid":"1231233","yy":0}
 		JSONObject jsonObject = JSONObject.fromObject(Config.geren_json_data);
 		this.setTitle(jsonObject.getString("netname") + " 即时通讯软件");
 		if(jsonObject.getString("img").equals("")){
 			myImage.setIcon(new ImageIcon("face0/" + (int)(Math.random()*40) + ".png"));
 		}else {
 			myImage.setIcon(new ImageIcon("face0/" + jsonObject.getString("img") + ".png"));
		}
 		myNetname.setText(jsonObject.getString("netname"));
 		myInfo.setText(jsonObject.getString("info"));
 	}
	public Haoyou() {
		super();
		setBounds(100, 100, 340, 743);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		getContentPane().add(panel, BorderLayout.NORTH);

		myImage.setPreferredSize(new Dimension(55, 55));

		panel.add(myImage, BorderLayout.WEST);

		final JPanel panel_1 = new JPanel();
		final BorderLayout borderLayout = new BorderLayout(5, 5);
		panel_1.setLayout(borderLayout);
		panel.add(panel_1, BorderLayout.CENTER);

		myNetname.setFont(new Font("", Font.BOLD, 16));
		myNetname.setText("小唐唐想静静");
		panel_1.add(myNetname, BorderLayout.CENTER);

		myInfo.setFont(new Font("宋体", Font.PLAIN, 12));
		myInfo.setText("知识的价值不在于占有，而在于使用。");
		panel_1.add(myInfo, BorderLayout.SOUTH);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		getContentPane().add(panel_2, BorderLayout.SOUTH);

		final JPanel panel_3 = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_3.setLayout(flowLayout_1);
		panel_2.add(panel_3);

		final JButton button = new JButton();
		button.setText("设置");
		panel_3.add(button);

		final JButton button_2 = new JButton();
		button_2.setText("查找");
		panel_3.add(button_2);

		final JPanel panel_4 = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		panel_4.setLayout(flowLayout);
		panel_2.add(panel_4, BorderLayout.EAST);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				System.exit(0);
			}
		});
		button_1.setText("退出");
		panel_4.add(button_1);

		final JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		tabbedPane.addTab("我的好友", null, panel_5, null);

		final JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(new HaoyoulistJpanel());
		gengxin();
		
	}
}
