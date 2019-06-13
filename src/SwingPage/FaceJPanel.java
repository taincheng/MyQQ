package SwingPage;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Config;
import util.Msg;


public class FaceJPanel extends JPanel implements 
Comparable<FaceJPanel>,MouseListener ,Runnable{

	private String image;
	private String netName;
	private String info;
	private String uid;
	private JLabel imageJLabel;
	private JLabel netNameJLabe;
	private JLabel infoJLabel;
	private boolean online = false;
	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;

	public FaceJPanel(String image, String netName, String info, String uid) {
		this.image = image;
		this.netName = netName;
		this.info = info;
		this.uid = uid;

		this.setLayout(null);
		imageJLabel = new JLabel();
		imageJLabel.setBounds(0, 0, 48, 48);
		add(imageJLabel);
		
		setImage(image);

		netNameJLabe = new JLabel(netName);
		netNameJLabe.setFont(new Font("楷体", Font.BOLD, 17));
		netNameJLabe.setBounds(57, 0, 351, 25);
		add(netNameJLabe);
		netNameJLabe.setText(netName);
		
		infoJLabel = new JLabel(info);
		infoJLabel.setBounds(57, 31, 351, 18);
		add(infoJLabel);
		infoJLabel.setText(info);
		
		this.addMouseListener(this);
		this.setVisible(true);
		
	}
	
	//所有的消息
	private Vector<Msg> msgs = new Vector<Msg>();
	private boolean is_run = true;
	@Override
	public void run() {
		is_run = true;
		int x = this.getX();
		int y = this.getY();
		
		while(is_run){
			this.setLocation(x-1, y-1);
			
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
			}
			this.setLocation(x+2, y+2);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
			}
		}
		this.setLocation(x, y);
	}
	private Thread thread = null;
	
	//寄存消息
	public void addMessage(Msg msg){
		msgs.add(msg);//添加消息进去
		
		if(thread == null){
			thread = new Thread();
			thread.start();
		}else if(thread.getState() == Thread.State.TERMINATED){
			thread = new Thread(this);
			thread.start();
		}else if(is_run == false){
			thread = new Thread(this);
			thread.start();
		}
	}

	public void setImage(String image) {
		if (image.equals("")) {
			int i = (int)(Math.random()*40);
			image = String.valueOf(i);
		}
		if (online) {
			imageJLabel.setIcon(new ImageIcon("face0/" + image + ".png"));
		} else {
			imageJLabel.setIcon(new ImageIcon("face1/" + image + ".png"));
		}
		this.image = image;
	}

	public void setnetName(String netName) {
		netNameJLabe.setText(netName);
		this.netName = netName;
	}

	public void setInfo(String info) {
		infoJLabel.setText(info);
		this.info = info;
	}

	public void setOnline(boolean online) {
		this.online = online;
		if (online) {
			imageJLabel.setIcon(new ImageIcon("face0/" + image + ".png"));
		} else {
			imageJLabel.setIcon(new ImageIcon("face1/" + image + ".png"));
		}
	}

	@Override
	public int compareTo(FaceJPanel o) {
		if (o.online) {
			return 1;
		} else if (this.online) {
			return -1;
		} else {
			return 0;
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(online){
				is_run = false;//终止闪动
				Config.showChatFrame(uid, netName, image, info,msgs);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x = this.getX();
		y = this.getY();
		height = this.getHeight();
		width = this.getWidth();
		this.setBounds(x,y+5,width+5,height+5);
		//this.setLocation(x - 5, y - 5);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBounds(x, y,width,height);
	}

	
}
