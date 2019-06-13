package SwingPage;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class chazao extends JFrame {

	private JTable table;
	private JTextField textField;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);


		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chazao frame = new chazao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Vector cols=new Vector();
	Vector rows=new Vector();
	/**
	 * Create the frame
	 */
	public chazao() {
		super();
		setTitle("查找好友");
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(100, 100, 469, 422);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JLabel label = new JLabel();
		label.setText("昵称:");
		label.setBounds(10, 22, 66, 18);
		getContentPane().add(label);

		textField = new JTextField();
		textField.setBounds(50, 14, 326, 35);
		getContentPane().add(textField);

		final JButton button = new JButton();
		button.setText("查找");
		button.setBounds(382, 17, 66, 28);
		getContentPane().add(button);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 438, 307);
		getContentPane().add(scrollPane);

		cols.add("昵称");
		cols.add("在线"); 
		
		table = new JTable(rows,cols);
		scrollPane.setViewportView(table);
		//
	}

}
