package ydbs;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class A02YdbsMainLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A02YdbsMainLogin frame = new A02YdbsMainLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public A02YdbsMainLogin() {
		setBounds(500, 300, 250, 200);
		setTitle("로그인");
		getContentPane().setLayout(null);
		
		JTextField textName = new JTextField();
		textName.setBounds(59, 39, 116, 21);
		getContentPane().add(textName);
		
		JTextField textPassWord = new JTextField();
		textPassWord.setBounds(59, 70, 116, 21);
		getContentPane().add(textPassWord);
		
		JButton buttonLogin = new JButton("로그인");
		buttonLogin.setBounds(59, 101, 116, 21);
		getContentPane().add(buttonLogin);		
	}
	
}
