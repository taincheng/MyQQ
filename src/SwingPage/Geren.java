package SwingPage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class Geren extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPanel panel;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Geren frame = new Geren();
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
	public Geren() {
		setResizable(false);
		setTitle("\u4E2A\u4EBA\u8D44\u6599");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(14, 13, 63, 63);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(163, 13, 179, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(163, 50, 345, 24);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_1 = new JLabel(" \u6635   \u79F0:");
		label_1.setBounds(91, 16, 72, 18);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u4E2A\u6027\u7B7E\u540D:");
		label_2.setBounds(91, 53, 72, 18);
		contentPane.add(label_2);
		
		panel = new JPanel();
		panel.setName("");
		panel.setBackground(new Color(240, 240, 240));
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("");
		panel.setBounds(14, 109, 494, 284);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_3 = new JLabel("\u771F\u5B9E\u59D3\u540D:");
		label_3.setFont(new Font("楷体", Font.BOLD, 18));
		label_3.setBounds(14, 13, 93, 27);
		panel.add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(121, 13, 77, 27);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_4 = new JLabel("\u6027\u522B:");
		label_4.setFont(new Font("楷体", Font.BOLD, 18));
		label_4.setBounds(243, 13, 55, 27);
		panel.add(label_4);
		
		JRadioButton radioButton = new JRadioButton("\u7537");
		radioButton.setBounds(316, 15, 49, 27);
		panel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.setBounds(383, 15, 49, 27);
		panel.add(radioButton_1);
		
		JLabel label_5 = new JLabel("\u51FA\u751F\u5E74\u6708:");
		label_5.setFont(new Font("楷体", Font.BOLD, 18));
		label_5.setBounds(14, 67, 93, 27);
		panel.add(label_5);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(121, 70, 77, 24);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(231, 70, 77, 24);
		panel.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(344, 70, 77, 24);
		panel.add(comboBox_2);
		
		JLabel label_6 = new JLabel("\u5E74");
		label_6.setFont(new Font("楷体", Font.BOLD, 18));
		label_6.setBounds(202, 67, 30, 27);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("\u6708");
		label_7.setFont(new Font("楷体", Font.BOLD, 18));
		label_7.setBounds(316, 67, 30, 27);
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("\u65E5");
		label_8.setFont(new Font("楷体", Font.BOLD, 18));
		label_8.setBounds(429, 67, 30, 27);
		panel.add(label_8);
		
		JLabel label_9 = new JLabel("\u5907\u6CE8:");
		label_9.setFont(new Font("楷体", Font.BOLD, 18));
		label_9.setBounds(14, 113, 93, 27);
		panel.add(label_9);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(74, 138, 406, 133);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}
}
